/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource

@Parcelize
data class GalleryItemData(
    val filename: String,
    val mimeType: String,
    val mediaSource: MediaSource,
    val thumbnailSource: MediaSource?,
    val type: Type,
) : Parcelable {
    enum class Type {
        Image,
        Video,
        Audio,
        File,
    }
}
