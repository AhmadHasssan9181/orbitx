/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview

import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeAnimatedImage
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeImage
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import java.util.Locale

internal fun MediaInfo.canEditImage(): Boolean {
    val resolvedMimeType = resolvedImageMimeType() ?: return false
    return resolvedMimeType.isMimeTypeImage() &&
        !resolvedMimeType.isMimeTypeAnimatedImage() &&
        resolvedMimeType != MimeTypes.Svg
}

internal fun MediaInfo.isImageAttachment(): Boolean {
    return resolvedImageMimeType().isMimeTypeImage()
}

internal fun MediaInfo.resolvedImageMimeType(): String? {
    return mimeType.takeIf { it.isMimeTypeImage() } ?: fileExtension.toImageMimeTypeOrNull()
}

private fun String.toImageMimeTypeOrNull(): String? {
    return when (lowercase(Locale.ROOT)) {
        "png" -> MimeTypes.Png
        "jpg", "jpeg" -> MimeTypes.Jpeg
        "gif" -> MimeTypes.Gif
        "webp" -> MimeTypes.WebP
        "svg" -> MimeTypes.Svg
        "bmp" -> "image/bmp"
        "heic" -> "image/heic"
        "heif" -> "image/heif"
        "avif" -> "image/avif"
        else -> null
    }
}
