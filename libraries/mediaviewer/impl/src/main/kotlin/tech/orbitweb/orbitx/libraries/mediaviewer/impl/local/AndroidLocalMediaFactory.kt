/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.local

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.androidutils.file.getFileName
import tech.orbitweb.orbitx.libraries.androidutils.file.getFileSize
import tech.orbitweb.orbitx.libraries.androidutils.file.getMimeType
import tech.orbitweb.orbitx.libraries.androidutils.filesize.FileSizeFormatter
import tech.orbitweb.orbitx.libraries.core.data.tryOrNull
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaFile
import tech.orbitweb.orbitx.libraries.matrix.api.media.toFile
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMediaFactory
import tech.orbitweb.orbitx.libraries.mediaviewer.api.util.FileExtensionExtractor

@ContributesBinding(AppScope::class)
class AndroidLocalMediaFactory(
    @ApplicationContext private val context: Context,
    private val fileSizeFormatter: FileSizeFormatter,
    private val fileExtensionExtractor: FileExtensionExtractor,
) : LocalMediaFactory {
    override fun createFromMediaFile(
        mediaFile: MediaFile,
        mediaInfo: MediaInfo,
    ): LocalMedia {
        val file = mediaFile.toFile()
        val targetFile = if (tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralCrypto.isEphemeralEncrypted(file)) {
            val extension = when {
                mediaInfo.mimeType.startsWith("video/mp4") -> ".mp4"
                mediaInfo.mimeType.startsWith("video/webm") -> ".webm"
                mediaInfo.mimeType.startsWith("video/") -> ".mp4"
                mediaInfo.mimeType.startsWith("audio/ogg") -> ".ogg"
                mediaInfo.mimeType.startsWith("audio/mp4") -> ".m4a"
                mediaInfo.mimeType.startsWith("audio/mpeg") -> ".mp3"
                mediaInfo.mimeType.startsWith("audio/") -> ".ogg"
                mediaInfo.mimeType.startsWith("image/jpeg") -> ".jpg"
                mediaInfo.mimeType.startsWith("image/png") -> ".png"
                mediaInfo.mimeType.startsWith("image/webp") -> ".webp"
                mediaInfo.mimeType.startsWith("image/gif") -> ".gif"
                else -> {
                    val extracted = fileExtensionExtractor.extractFromName(mediaInfo.filename)
                    if (extracted.isNotBlank() && !extracted.equals("ephemeral", ignoreCase = true) && !extracted.equals("tmp", ignoreCase = true)) {
                        ".$extracted"
                    } else {
                        ".tmp"
                    }
                }
            }
            val decryptedTemp = java.io.File.createTempFile("orbit_view_once_", extension, context.cacheDir)
            if (tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralCrypto.decryptFile(file, decryptedTemp)) {
                decryptedTemp
            } else {
                file
            }
        } else {
            file
        }
        return createFromUri(
            uri = targetFile.toUri(),
            mimeType = mediaInfo.mimeType,
            name = mediaInfo.filename,
            caption = mediaInfo.caption,
            formattedFileSize = mediaInfo.formattedFileSize,
            senderId = mediaInfo.senderId,
            senderName = mediaInfo.senderName,
            senderAvatar = mediaInfo.senderAvatar,
            dateSent = mediaInfo.dateSent,
            dateSentFull = mediaInfo.dateSentFull,
            waveform = mediaInfo.waveform,
            duration = mediaInfo.duration,
        )
    }

    override fun createFromUri(
        uri: Uri,
        mimeType: String?,
        name: String?,
        formattedFileSize: String?
    ): LocalMedia = createFromUri(
        uri = uri,
        mimeType = mimeType,
        name = name,
        caption = null,
        formattedFileSize = formattedFileSize,
        senderId = null,
        senderName = null,
        senderAvatar = null,
        dateSent = null,
        dateSentFull = null,
        waveform = null,
        duration = null,
    )

    private fun createFromUri(
        uri: Uri,
        mimeType: String?,
        name: String?,
        caption: String?,
        formattedFileSize: String?,
        senderId: UserId?,
        senderName: String?,
        senderAvatar: String?,
        dateSent: String?,
        dateSentFull: String?,
        waveform: List<Float>?,
        duration: String?,
    ): LocalMedia {
        val fileName = name ?: context.getFileName(uri) ?: ""
        val resolvedMimeType = resolveMimeType(
            uri = uri,
            mimeType = mimeType,
            fileName = fileName,
        )
        val fileSize = context.getFileSize(uri)
        val calculatedFormattedFileSize = formattedFileSize ?: fileSizeFormatter.format(fileSize)
        val fileExtension = fileExtensionExtractor.extractFromName(fileName)
        return LocalMedia(
            uri = uri,
            info = MediaInfo(
                mimeType = resolvedMimeType,
                filename = fileName,
                fileSize = fileSize,
                caption = caption,
                formattedCaption = null,
                formattedFileSize = calculatedFormattedFileSize,
                fileExtension = fileExtension,
                senderId = senderId,
                senderName = senderName,
                senderAvatar = senderAvatar,
                dateSent = dateSent,
                dateSentFull = dateSentFull,
                waveform = waveform,
                duration = duration,
            )
        )
    }

    private fun resolveMimeType(
        uri: Uri,
        mimeType: String?,
        fileName: String,
    ): String {
        val explicitMimeType = mimeType.takeUnless { it.isNullOrBlank() || it == MimeTypes.OctetStream }
        if (explicitMimeType != null) return explicitMimeType

        val resolverMimeType = context.getMimeType(uri).takeUnless { it.isNullOrBlank() || it == MimeTypes.OctetStream }
        if (resolverMimeType != null) return resolverMimeType

        val decodedImageMimeType = decodeImageMimeType(uri)
        if (decodedImageMimeType != null) return decodedImageMimeType

        val extensionMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtensionExtractor.extractFromName(fileName)
        )
        if (!extensionMimeType.isNullOrBlank()) return extensionMimeType

        return MimeTypes.OctetStream
    }

    private fun decodeImageMimeType(uri: Uri): String? {
        return tryOrNull {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeStream(inputStream, null, options)
                options.outMimeType
            }
        }
    }
}
