/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.androidutils.media

import android.media.MediaMetadataRetriever

/** [MediaMetadataRetriever] only implements `AutoClosable` since API 29, so we need to execute this to have the same in older APIs. */
inline fun <T> MediaMetadataRetriever.runAndRelease(block: MediaMetadataRetriever.() -> T): T {
    return try {
        block()
    } finally {
        release()
    }
}
