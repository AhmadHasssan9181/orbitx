/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.withContext
import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.resolvedImageMimeType
import tech.orbitweb.orbitx.libraries.androidutils.bitmap.calculateInSampleSize
import tech.orbitweb.orbitx.libraries.androidutils.bitmap.rotateToExifMetadataOrientation
import tech.orbitweb.orbitx.libraries.androidutils.bitmap.writeBitmap
import tech.orbitweb.orbitx.libraries.androidutils.file.createTmpFile
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeAnimatedImage
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeImage
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia
import java.io.File
import kotlin.math.roundToInt

private const val EDITED_MEDIA_DIR_NAME = "edited-media"

interface AttachmentImageEditor {
    suspend fun canEdit(localMedia: LocalMedia): Boolean

    suspend fun exportEdits(
        localMedia: LocalMedia,
        edits: AttachmentImageEdits,
    ): Result<EditedLocalMedia>
}

data class EditedLocalMedia(
    val localMedia: LocalMedia,
    val file: File,
)

@ContributesBinding(AppScope::class)
class DefaultAttachmentImageEditor(
    @ApplicationContext private val context: Context,
    private val dispatchers: CoroutineDispatchers,
) : AttachmentImageEditor {
    override suspend fun canEdit(localMedia: LocalMedia): Boolean = withContext(dispatchers.io) {
        localMedia.info.resolvedImageMimeType()
            ?.takeIf { it.isEditableStillImageMimeType() }
            ?.let { return@withContext true }

        val decodedMimeType = tech.orbitweb.orbitx.libraries.core.data.tryOrNull {
            context.contentResolver.openInputStream(localMedia.uri)?.use { stream ->
                val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeStream(stream, null, options)
                options.outMimeType
            }
        }

        decodedMimeType.isEditableStillImageMimeType()
    }

    override suspend fun exportEdits(
        localMedia: LocalMedia,
        edits: AttachmentImageEdits,
    ): Result<EditedLocalMedia> = withContext(dispatchers.io) {
        runCatchingExceptions {
            android.util.Log.i("OrbitImageEditor", "exportEdits: starting for uri=${localMedia.uri}")
            val sourceMimeType = localMedia.info.resolvedImageMimeType() ?: localMedia.info.mimeType
            val exportedMimeType = exportedMimeTypeFor(sourceMimeType)

            // Copy URI stream safely to a temporary disk file to avoid OOM and allow multi-pass decoding
            val tempSourceFile = context.copyUriToTempFile(localMedia.uri)
                ?: error("Unable to access or copy image from ${localMedia.uri}")

            val (decodedBitmap, exifOrientation) = try {
                val exif = tech.orbitweb.orbitx.libraries.core.data.tryOrNull {
                    ExifInterface(tempSourceFile.absolutePath).getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED,
                    )
                } ?: ExifInterface.ORIENTATION_UNDEFINED

                // 1. Determine bounds & inSampleSize targeting max 2048px to prevent OOM
                val boundsOptions = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeFile(tempSourceFile.absolutePath, boundsOptions)

                val sampleSize = boundsOptions.calculateInSampleSize(
                    desiredWidth = 2048,
                    desiredHeight = 2048,
                )

                // 2. Decode downsampled bitmap
                val decodeOptions = BitmapFactory.Options().apply {
                    inSampleSize = sampleSize
                }
                var bmp = BitmapFactory.decodeFile(tempSourceFile.absolutePath, decodeOptions)
                if (bmp == null) {
                    bmp = context.contentResolver.openInputStream(localMedia.uri)?.use { stream ->
                        BitmapFactory.decodeStream(stream, null, decodeOptions)
                    }
                }
                if (bmp == null) {
                    bmp = BitmapFactory.decodeFile(tempSourceFile.absolutePath)
                }
                val validBmp = bmp ?: error("Unable to decode bitmap from ${localMedia.uri}")
                Pair(validBmp, exif)
            } finally {
                tempSourceFile.delete()
            }

            android.util.Log.i(
                "OrbitImageEditor",
                "exportEdits: decoded bitmap ${decodedBitmap.width}x${decodedBitmap.height}, orientation=$exifOrientation"
            )

            val normalizedBitmap = decodedBitmap.rotateToExifMetadataOrientation(exifOrientation)
            if (normalizedBitmap !== decodedBitmap) {
                decodedBitmap.recycle()
            }

            val transformedBitmap = normalizedBitmap.applyEdits(edits)
            if (transformedBitmap !== normalizedBitmap) {
                normalizedBitmap.recycle()
            }

            val cropRect = edits.cropRect.toPixelRect(
                imageWidth = transformedBitmap.width,
                imageHeight = transformedBitmap.height,
            )
            val isCropUnchanged = cropRect.left == 0 && cropRect.top == 0 &&
                cropRect.width() == transformedBitmap.width && cropRect.height() == transformedBitmap.height
            val croppedBitmap = if (isCropUnchanged) {
                transformedBitmap
            } else {
                val safeWidth = cropRect.width().coerceAtLeast(1).coerceAtMost(transformedBitmap.width - cropRect.left)
                val safeHeight = cropRect.height().coerceAtLeast(1).coerceAtMost(transformedBitmap.height - cropRect.top)
                Bitmap.createBitmap(
                    transformedBitmap,
                    cropRect.left.coerceIn(0, transformedBitmap.width - 1),
                    cropRect.top.coerceIn(0, transformedBitmap.height - 1),
                    safeWidth,
                    safeHeight,
                )
            }
            if (croppedBitmap !== transformedBitmap) {
                transformedBitmap.recycle()
            }

            val editedMediaDir = File(context.cacheDir, EDITED_MEDIA_DIR_NAME).apply { mkdirs() }
            val outputFile = context.createTmpFile(baseDir = editedMediaDir, extension = compressFileExtension(exportedMimeType))
            outputFile.outputStream().use { out ->
                croppedBitmap.compress(compressFormat(exportedMimeType), 90, out)
                out.flush()
            }
            croppedBitmap.recycle()

            android.util.Log.i("OrbitImageEditor", "exportEdits: successfully exported to ${outputFile.absolutePath}")

            EditedLocalMedia(
                localMedia = localMedia.copy(
                    uri = Uri.fromFile(outputFile),
                    info = localMedia.info.copy(mimeType = exportedMimeType),
                ),
                file = outputFile,
            )
        }.onFailure {
            android.util.Log.e("OrbitImageEditor", "exportEdits FATAL ERROR for uri=${localMedia.uri}", it)
            timber.log.Timber.e(it, "exportEdits failed for uri=${localMedia.uri}")
        }
    }
}

private fun Context.copyUriToTempFile(uri: Uri): File? {
    val tempFile = File.createTempFile("orbit_edit_", ".tmp", cacheDir)
    return try {
        var success = false
        if (uri.scheme == android.content.ContentResolver.SCHEME_FILE) {
            val file = File(uri.path ?: "")
            if (file.exists() && file.canRead()) {
                file.copyTo(tempFile, overwrite = true)
                success = true
            }
        }
        if (!success) {
            contentResolver.openInputStream(uri)?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
                success = true
            }
        }
        if (!success || tempFile.length() == 0L) {
            val path = uri.path
            if (path != null) {
                val direct = File(path)
                if (direct.exists() && direct.canRead()) {
                    direct.copyTo(tempFile, overwrite = true)
                    success = true
                }
            }
        }
        if (success && tempFile.length() > 0L) {
            tempFile
        } else {
            tempFile.delete()
            null
        }
    } catch (e: Throwable) {
        android.util.Log.w("OrbitImageEditor", "copyUriToTempFile failed for $uri", e)
        tempFile.delete()
        null
    }
}

internal fun exportedMimeTypeFor(sourceMimeType: String?): String {
    return if (sourceMimeType == MimeTypes.Png) {
        MimeTypes.Png
    } else {
        MimeTypes.Jpeg
    }
}

private fun Bitmap.applyEdits(edits: AttachmentImageEdits): Bitmap {
    val normalizedTurns = (edits.rotationQuarterTurns % 4 + 4) % 4
    val hasTransform = normalizedTurns != 0 || edits.isFlippedHorizontally || edits.isFlippedVertically
    val transformed = if (hasTransform) {
        val centerX = width / 2f
        val centerY = height / 2f
        val matrix = Matrix().apply {
            val scaleX = if (edits.isFlippedHorizontally) -1f else 1f
            val scaleY = if (edits.isFlippedVertically) -1f else 1f
            if (scaleX < 0f || scaleY < 0f) {
                postScale(scaleX, scaleY, centerX, centerY)
            }
            if (normalizedTurns != 0) {
                postRotate(normalizedTurns * 90f, centerX, centerY)
            }
        }
        Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    } else {
        this
    }
    val filtered = transformed.applyFilter(edits.filter)
    if (filtered !== transformed && transformed !== this) {
        transformed.recycle()
    }

    if (edits.drawStrokes.isEmpty() && edits.stickers.isEmpty()) {
        return filtered
    }

    val mutableBitmap = if (filtered.isMutable) {
        filtered
    } else {
        try {
            filtered.copy(Bitmap.Config.ARGB_8888, true)
        } catch (_: Throwable) {
            null
        } ?: run {
            val fallback = Bitmap.createBitmap(filtered.width, filtered.height, Bitmap.Config.ARGB_8888)
            val c = android.graphics.Canvas(fallback)
            c.drawBitmap(filtered, 0f, 0f, null)
            fallback
        }
    }

    if (mutableBitmap !== filtered && filtered !== this && filtered !== transformed) {
        filtered.recycle()
    }

    val canvas = android.graphics.Canvas(mutableBitmap)
    val bmpWidth = mutableBitmap.width.toFloat()
    val bmpHeight = mutableBitmap.height.toFloat()

    // 1. Draw strokes
    val strokePaint = android.graphics.Paint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        strokeCap = android.graphics.Paint.Cap.ROUND
        strokeJoin = android.graphics.Paint.Join.ROUND
    }

    for (stroke in edits.drawStrokes) {
        if (stroke.points.size < 2) continue
        strokePaint.color = stroke.colorHex.toInt()
        val strokeWidthPx = stroke.strokeWidth / 360f * bmpWidth
        strokePaint.strokeWidth = strokeWidthPx.coerceAtLeast(3f)

        val path = android.graphics.Path()
        val first = stroke.points.first()
        path.moveTo(first.x * bmpWidth, first.y * bmpHeight)
        for (i in 1 until stroke.points.size) {
            val pt = stroke.points[i]
            path.lineTo(pt.x * bmpWidth, pt.y * bmpHeight)
        }
        canvas.drawPath(path, strokePaint)
    }

    // 2. Draw stickers
    val textPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        textAlign = android.graphics.Paint.Align.CENTER
    }

    for (sticker in edits.stickers) {
        val baseSize = bmpWidth * 0.16f * sticker.scale
        textPaint.textSize = baseSize
        val xPx = sticker.x * bmpWidth
        val yPx = sticker.y * bmpHeight

        canvas.save()
        canvas.rotate(sticker.rotationDegrees, xPx, yPx)
        val fontMetrics = textPaint.fontMetrics
        val baseline = yPx - (fontMetrics.ascent + fontMetrics.descent) / 2f
        canvas.drawText(sticker.text, xPx, baseline, textPaint)
        canvas.restore()
    }

    return mutableBitmap
}

private fun Bitmap.applyFilter(filter: ImageFilter): Bitmap {
    if (filter == ImageFilter.None) return this
    val cm = android.graphics.ColorMatrix()
    when (filter) {
        ImageFilter.None -> return this
        ImageFilter.Mono -> cm.setSaturation(0f)
        ImageFilter.Vivid -> cm.setSaturation(1.6f)
        ImageFilter.Warm -> cm.set(floatArrayOf(
            1.15f,
            0f,
            0f,
            0f,
            15f,
            0f,
            1.05f,
            0f,
            0f,
            5f,
            0f,
            0f,
            0.85f,
            0f,
            -10f,
            0f,
            0f,
            0f,
            1f,
            0f
        ))
        ImageFilter.Cool -> cm.set(floatArrayOf(
            0.85f,
            0f,
            0f,
            0f,
            -10f,
            0f,
            1.0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            1.2f,
            0f,
            15f,
            0f,
            0f,
            0f,
            1f,
            0f
        ))
        ImageFilter.Vintage -> cm.set(floatArrayOf(
            0.9f,
            0.1f,
            0.1f,
            0f,
            25f,
            0.1f,
            0.8f,
            0.1f,
            0f,
            15f,
            0.1f,
            0.1f,
            0.6f,
            0f,
            -10f,
            0f,
            0f,
            0f,
            1f,
            0f
        ))
    }
    val filtered = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(filtered)
    val paint = android.graphics.Paint().apply {
        colorFilter = android.graphics.ColorMatrixColorFilter(cm)
    }
    canvas.drawBitmap(this, 0f, 0f, paint)
    return filtered
}

private data class PixelCropRect(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int,
) {
    fun width() = right - left
    fun height() = bottom - top
}

private fun NormalizedCropRect.toPixelRect(imageWidth: Int, imageHeight: Int): PixelCropRect {
    val leftPx = (left * imageWidth).roundToInt().coerceIn(0, imageWidth - 1)
    val topPx = (top * imageHeight).roundToInt().coerceIn(0, imageHeight - 1)
    val rightPx = (right * imageWidth).roundToInt().coerceIn(leftPx + 1, imageWidth)
    val bottomPx = (bottom * imageHeight).roundToInt().coerceIn(topPx + 1, imageHeight)
    return PixelCropRect(
        left = leftPx,
        top = topPx,
        right = rightPx,
        bottom = bottomPx,
    )
}

private fun compressFormat(mimeType: String) = when (mimeType) {
    MimeTypes.Png -> Bitmap.CompressFormat.PNG
    else -> Bitmap.CompressFormat.JPEG
}

private fun compressFileExtension(mimeType: String) = when (mimeType) {
    MimeTypes.Png -> "png"
    else -> "jpeg"
}

private fun String?.isEditableStillImageMimeType(): Boolean {
    return this != null &&
        this.isMimeTypeImage() &&
        !this.isMimeTypeAnimatedImage() &&
        this != MimeTypes.Svg
}
