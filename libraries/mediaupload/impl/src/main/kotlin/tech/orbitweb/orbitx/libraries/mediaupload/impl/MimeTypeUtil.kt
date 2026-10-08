/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaupload.impl

import android.graphics.Bitmap
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes

fun mimeTypeToCompressFormat(mimeType: String) = when (mimeType) {
    MimeTypes.Png -> Bitmap.CompressFormat.PNG
    else -> Bitmap.CompressFormat.JPEG
}

fun mimeTypeToCompressFileExtension(mimeType: String) = when (mimeType) {
    MimeTypes.Png -> "png"
    else -> "jpeg"
}

fun mimeTypeToThumbnailMimeType(mimeType: String) = when (mimeType) {
    MimeTypes.Png -> MimeTypes.Png
    else -> MimeTypes.Jpeg
}
