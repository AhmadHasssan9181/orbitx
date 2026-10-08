/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.api.local

import android.net.Uri
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaFile
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo

interface LocalMediaFactory {
    /**
     * This method will create a [LocalMedia] with the given [MediaFile] and [MediaInfo].
     */
    fun createFromMediaFile(
        mediaFile: MediaFile,
        mediaInfo: MediaInfo,
    ): LocalMedia

    /**
     * This method will create a [LocalMedia] with the given mimeType, name and formattedFileSize
     * If any of those params are null, it'll try to read them from the content.
     */
    fun createFromUri(
        uri: Uri,
        mimeType: String?,
        name: String?,
        formattedFileSize: String?
    ): LocalMedia
}
