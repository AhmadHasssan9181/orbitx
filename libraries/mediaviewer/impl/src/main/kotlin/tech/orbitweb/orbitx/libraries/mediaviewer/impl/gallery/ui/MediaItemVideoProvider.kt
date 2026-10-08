/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.gallery.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.MediaItem
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.aMediaItemVideo

class MediaItemVideoProvider : PreviewParameterProvider<MediaItem.Video> {
    override val values: Sequence<MediaItem.Video>
        get() = sequenceOf(
            aMediaItemVideo(),
            aMediaItemVideo(
                duration = null,
            ),
        )
}
