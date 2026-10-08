/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineRoomInfo
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.virtual.TimelineItemDaySeparatorView
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.virtual.TimelineItemReadMarkerView
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.virtual.TimelineItemRoomBeginningView
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.virtual.TimelineLoadingMoreIndicator
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual.TimelineItemDaySeparatorModel
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual.TimelineItemLastForwardIndicatorModel
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual.TimelineItemLoadingIndicatorModel
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual.TimelineItemReadMarkerModel
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual.TimelineItemRoomBeginningModel
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.virtual.TimelineItemTypingNotificationModel
import tech.orbitweb.orbitx.features.messages.impl.typing.TypingNotificationView
import timber.log.Timber

@Composable
fun TimelineItemVirtualRow(
    virtual: TimelineItem.Virtual,
    timelineRoomInfo: TimelineRoomInfo,
    eventSink: (TimelineEvent.TimelineItemEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (virtual.model) {
            is TimelineItemDaySeparatorModel -> TimelineItemDaySeparatorView(virtual.model)
            TimelineItemReadMarkerModel -> TimelineItemReadMarkerView()
            TimelineItemRoomBeginningModel -> {
                TimelineItemRoomBeginningView(
                    predecessorRoom = timelineRoomInfo.predecessorRoom,
                    roomName = timelineRoomInfo.name,
                    isDm = timelineRoomInfo.isDm,
                    onPredecessorRoomClick = { roomId ->
                        eventSink(TimelineEvent.NavigateToPredecessorOrSuccessorRoom(roomId))
                    },
                )
            }
            is TimelineItemLoadingIndicatorModel -> {
                TimelineLoadingMoreIndicator(virtual.model.direction)
                val latestEventSink by rememberUpdatedState(eventSink)
                LaunchedEffect(virtual.model.timestamp) {
                    Timber.d("Pagination triggered by load more indicator")
                    latestEventSink(TimelineEvent.LoadMore(virtual.model.direction))
                }
            }
            // Empty model trick to avoid timeline jumping during forward pagination.
            is TimelineItemLastForwardIndicatorModel -> {
                Spacer(modifier = Modifier)
            }
            is TimelineItemTypingNotificationModel -> {
                TypingNotificationView(
                    state = timelineRoomInfo.typingNotificationState,
                )
            }
        }
    }
}
