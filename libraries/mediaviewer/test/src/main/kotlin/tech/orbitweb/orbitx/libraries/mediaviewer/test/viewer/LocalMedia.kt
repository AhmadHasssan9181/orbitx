/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.test.viewer

import android.net.Uri
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.anImageMediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia

fun aLocalMedia(
    uri: Uri,
    mediaInfo: MediaInfo = anImageMediaInfo(),
) = LocalMedia(
    uri = uri,
    info = mediaInfo
)
