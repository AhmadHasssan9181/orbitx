/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.details

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo

sealed interface MediaBottomSheetState {
    data object Hidden : MediaBottomSheetState

    data class Details(
        val fromGallery: Boolean,
        val eventId: EventId?,
        val canDelete: Boolean,
        val mediaInfo: MediaInfo,
        val thumbnailSource: MediaSource?,
    ) : MediaBottomSheetState

    data class DeleteConfirmation(
        val eventId: EventId,
        val mediaInfo: MediaInfo,
        val thumbnailSource: MediaSource?,
    ) : MediaBottomSheetState
}
