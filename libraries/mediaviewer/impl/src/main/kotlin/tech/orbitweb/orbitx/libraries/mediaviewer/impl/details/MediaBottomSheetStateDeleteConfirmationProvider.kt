/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.details

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.anImageMediaInfo

open class MediaBottomSheetStateDeleteConfirmationProvider : PreviewParameterProvider<MediaBottomSheetState.DeleteConfirmation> {
    override val values: Sequence<MediaBottomSheetState.DeleteConfirmation>
        get() = sequenceOf(
            aMediaBottomSheetStateDeleteConfirmation(),
            aMediaBottomSheetStateDeleteConfirmation(
                thumbnailSource = MediaSource("url_thumbnail")
            ),
        )
}

fun aMediaBottomSheetStateDeleteConfirmation(
    mediaInfo: MediaInfo = anImageMediaInfo(
        senderName = USER_NAME_ALICE,
    ),
    thumbnailSource: MediaSource? = null,
) = MediaBottomSheetState.DeleteConfirmation(
    eventId = EventId("\$eventId"),
    mediaInfo = mediaInfo,
    thumbnailSource = thumbnailSource,
)
