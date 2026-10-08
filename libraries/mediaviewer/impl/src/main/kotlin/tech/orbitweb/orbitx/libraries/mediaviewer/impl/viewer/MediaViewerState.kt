/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.viewer

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.details.MediaBottomSheetState

data class MediaViewerState(
    val initiallySelectedEventId: EventId?,
    val listData: ImmutableList<MediaViewerPageData>,
    val currentIndex: Int,
    val snackbarMessage: SnackbarMessage?,
    val canShowInfo: Boolean,
    val mediaBottomSheetState: MediaBottomSheetState,
    val eventSink: (MediaViewerEvent) -> Unit,
)

@Immutable
sealed interface MediaViewerPageData {
    val pagerKey: Long

    data class Failure(
        val throwable: Throwable,
        override val pagerKey: Long = 0,
    ) : MediaViewerPageData

    data class Loading(
        val direction: Timeline.PaginationDirection,
        val timestamp: Long,
        override val pagerKey: Long,
    ) : MediaViewerPageData

    data class MediaViewerData(
        val eventId: EventId?,
        val mediaInfo: MediaInfo,
        val mediaSource: MediaSource,
        val thumbnailSource: MediaSource?,
        val downloadedMedia: State<AsyncData<LocalMedia>>,
        override val pagerKey: Long,
    ) : MediaViewerPageData
}
