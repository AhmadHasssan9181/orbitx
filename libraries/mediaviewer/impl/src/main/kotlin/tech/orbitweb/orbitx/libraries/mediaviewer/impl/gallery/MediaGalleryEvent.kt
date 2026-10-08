/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.gallery

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.MediaItem

sealed interface MediaGalleryEvent {
    data class ChangeMode(val mode: MediaGalleryMode) : MediaGalleryEvent
    data class LoadMore(val direction: Timeline.PaginationDirection) : MediaGalleryEvent
    data class Share(val eventId: EventId) : MediaGalleryEvent
    data class Forward(val eventId: EventId) : MediaGalleryEvent
    data class SaveOnDisk(val eventId: EventId) : MediaGalleryEvent
    data class OpenWith(val eventId: EventId) : MediaGalleryEvent
    data class OpenInfo(val mediaItem: MediaItem.Event) : MediaGalleryEvent
    data class ViewInTimeline(val eventId: EventId) : MediaGalleryEvent

    data class ConfirmDelete(
        val eventId: EventId,
        val mediaInfo: MediaInfo,
        val thumbnailSource: MediaSource?,
    ) : MediaGalleryEvent

    data object CloseBottomSheet : MediaGalleryEvent
    data class Delete(val eventId: EventId) : MediaGalleryEvent
}
