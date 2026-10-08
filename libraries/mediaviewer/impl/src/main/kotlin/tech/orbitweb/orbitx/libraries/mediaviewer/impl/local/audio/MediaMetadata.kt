/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.audio

import androidx.media3.common.MediaMetadata
import tech.orbitweb.orbitx.libraries.ui.strings.Strings

fun MediaMetadata?.hasArtwork(): Boolean {
    return this?.artworkData != null || this?.artworkUri != null
}

fun MediaMetadata?.buildInfo(): String {
    this ?: return ""
    return buildString {
        if (artist != null) {
            append(artist)
        }
        if (title != null) {
            if (isNotEmpty()) {
                append(Strings.NICE_SEPARATOR)
            }
            append(title)
        }
        if (recordingYear != null) {
            if (isNotEmpty()) {
                append(Strings.NICE_SEPARATOR)
            }
            append(recordingYear)
        }
    }
}
