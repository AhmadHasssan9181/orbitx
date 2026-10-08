/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.file

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.aZipMediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.anApkMediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.anAudioMediaInfo

open class MediaInfoFileProvider : PreviewParameterProvider<MediaInfo> {
    override val values: Sequence<MediaInfo>
        get() = sequenceOf(
            aZipMediaInfo(),
            anAudioMediaInfo(),
            anApkMediaInfo(),
        )
}
