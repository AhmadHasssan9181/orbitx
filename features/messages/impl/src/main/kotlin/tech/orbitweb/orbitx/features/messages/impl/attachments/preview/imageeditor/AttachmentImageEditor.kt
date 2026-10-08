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
import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.resolvedImageMimeType
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
import kotlinx.coroutines.withContext
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

        val decodedMimeType = context.openInputStreamSafe(localMedia.uri)?.use { input ->
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeStream(input, null, options)
            options.outMimeType
        }

        decodedMimeType.isEditableStillImageMimeType()
    }

    override suspend fun exportEdits(
        localMedia: LocalMedia,
        edits: AttachmentImageEdits,
    ): Result<EditedLocalMedia> = withContext(dispatchers.io) {
        runCatchingExceptions {
            val sourceMimeType = localMedia.info.resolvedImageMimeType() ?: localMedia.info.mimeType
            val exportedMimeType = exportedMimeTypeFor(sourceMimeType)
            val exifOrientation = context.openInputStreamSafe(localMedia.uri)?.let { input ->
                input.use {
                    ExifInterface(it).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
                }
            } ?: ExifInterface.ORIENTATION_UNDEFINED

            val decodedBitmap = context.openInputStreamSafe(localMedia.uri)?.use { input ->
                BitmapFactory.decodeStream(input)
            } ?: error("Unable to decode image from ${localMedia.uri}")

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
                Bitmap.createBitmap(
                    transformedBitmap,
                    cropRect.left,
                    cropRect.top,
                    cropRect.width(),
                    cropRect.height(),
                )
            }
            if (croppedBitmap !== transformedBitmap) {
                transformedBitmap.recycle()
            }

            val editedMediaDir = File(context.cacheDir, EDITED_MEDIA_DIR_NAME).apply { mkdirs() }
            val outputFile = context.createTmpFile(baseDir = editedMediaDir, extension = compressFileExtension(exportedMimeType))
            outputFile.writeBitmap(
                bitmap = croppedBitmap,
                format = compressFormat(exportedMimeType),
                quality = 90,
            )
            croppedBitmap.recycle()

            EditedLocalMedia(
                localMedia = localMedia.copy(
                    uri = Uri.fromFile(outputFile),
                    info = localMedia.info.copy(mimeType = exportedMimeType),
                ),
                file = outputFile,
            )
        }
    }
}

private fun Context.openInputStreamSafe(uri: Uri): java.io.InputStream? {
    if (uri.scheme == android.content.ContentResolver.SCHEME_FILE) {
        val path = uri.path
        if (path != null) {
            val file = File(path)
            if (file.exists() && file.canRead()) {
                return tech.orbitweb.orbitx.libraries.core.data.tryOrNull { java.io.FileInputStream(file) }
            }
        }
    }
    val streamFromResolver = tech.orbitweb.orbitx.libraries.core.data.tryOrNull { contentResolver.openInputStream(uri) }
    if (streamFromResolver != null) return streamFromResolver

    val path = uri.path
    if (path != null) {
        val directFile = File(path)
        if (directFile.exists() && directFile.canRead()) {
            return tech.orbitweb.orbitx.libraries.core.data.tryOrNull { java.io.FileInputStream(directFile) }
        }
        if (path.startsWith("/cache/")) {
            val cacheFile = File(cacheDir, path.removePrefix("/cache/"))
            if (cacheFile.exists() && cacheFile.canRead()) {
                return tech.orbitweb.orbitx.libraries.core.data.tryOrNull { java.io.FileInputStream(cacheFile) }
            }
        } else if (path.startsWith("/files/")) {
            val appFile = File(filesDir, path.removePrefix("/files/"))
            if (appFile.exists() && appFile.canRead()) {
                return tech.orbitweb.orbitx.libraries.core.data.tryOrNull { java.io.FileInputStream(appFile) }
            }
        }
    }
    return null
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

    val resultBitmap = filtered.copy(Bitmap.Config.ARGB_8888, true)
    if (filtered !== this && filtered !== transformed) {
        filtered.recycle()
    }

    val canvas = android.graphics.Canvas(resultBitmap)
    val bmpWidth = resultBitmap.width.toFloat()
    val bmpHeight = resultBitmap.height.toFloat()

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
        val strokeWidthPx = (stroke.strokeWidth / 360f) * bmpWidth
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

    return resultBitmap
}

private fun Bitmap.applyFilter(filter: ImageFilter): Bitmap {
    if (filter == ImageFilter.None) return this
    val cm = android.graphics.ColorMatrix()
    when (filter) {
        ImageFilter.None -> return this
        ImageFilter.Mono -> cm.setSaturation(0f)
        ImageFilter.Vivid -> cm.setSaturation(1.6f)
        ImageFilter.Warm -> cm.set(floatArrayOf(
            1.15f, 0f, 0f, 0f, 15f,
            0f, 1.05f, 0f, 0f, 5f,
            0f, 0f, 0.85f, 0f, -10f,
            0f, 0f, 0f, 1f, 0f
        ))
        ImageFilter.Cool -> cm.set(floatArrayOf(
            0.85f, 0f, 0f, 0f, -10f,
            0f, 1.0f, 0f, 0f, 0f,
            0f, 0f, 1.2f, 0f, 15f,
            0f, 0f, 0f, 1f, 0f
        ))
        ImageFilter.Vintage -> cm.set(floatArrayOf(
            0.9f, 0.1f, 0.1f, 0f, 25f,
            0.1f, 0.8f, 0.1f, 0f, 15f,
            0.1f, 0.1f, 0.6f, 0f, -10f,
            0f, 0f, 0f, 1f, 0f
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
