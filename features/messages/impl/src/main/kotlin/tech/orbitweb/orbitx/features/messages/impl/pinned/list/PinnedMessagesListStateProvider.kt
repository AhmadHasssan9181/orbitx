/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.pinned.list

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.messages.impl.UserEventPermissions
import tech.orbitweb.orbitx.features.messages.impl.actionlist.ActionListState
import tech.orbitweb.orbitx.features.messages.impl.actionlist.anActionListState
import tech.orbitweb.orbitx.features.messages.impl.link.LinkState
import tech.orbitweb.orbitx.features.messages.impl.link.aLinkState
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineRoomInfo
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemDaySeparator
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemReactions
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineRoomInfo
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemGroupPosition
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemAudioContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemFileContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemPollContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemTextContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.TimelineProtectionState
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.aTimelineProtectionState

open class PinnedMessagesListStateProvider : PreviewParameterProvider<PinnedMessagesListState> {
    override val values: Sequence<PinnedMessagesListState>
        get() = sequenceOf(
            aFailedPinnedMessagesListState(),
            aLoadingPinnedMessagesListState(),
            anEmptyPinnedMessagesListState(),
            aLoadedPinnedMessagesListState(
                timelineItems = persistentListOf(
                    aTimelineItemEvent(
                        isMine = false,
                        content = aTimelineItemTextContent("A pinned message"),
                        groupPosition = TimelineItemGroupPosition.Last,
                        timelineItemReactions = aTimelineItemReactions(0)
                    ),
                    aTimelineItemEvent(
                        isMine = false,
                        content = aTimelineItemAudioContent("A pinned file"),
                        groupPosition = TimelineItemGroupPosition.Middle,
                        timelineItemReactions = aTimelineItemReactions(0)
                    ),
                    aTimelineItemEvent(
                        isMine = false,
                        content = aTimelineItemPollContent("A pinned poll?"),
                        groupPosition = TimelineItemGroupPosition.First,
                        timelineItemReactions = aTimelineItemReactions(0)
                    ),
                    aTimelineItemDaySeparator(),
                    aTimelineItemEvent(
                        isMine = true,
                        content = aTimelineItemTextContent("A pinned message"),
                        groupPosition = TimelineItemGroupPosition.Last,
                        timelineItemReactions = aTimelineItemReactions(0)
                    ),
                    aTimelineItemEvent(
                        isMine = true,
                        content = aTimelineItemFileContent("A pinned file?"),
                        groupPosition = TimelineItemGroupPosition.Middle,
                        timelineItemReactions = aTimelineItemReactions(0)
                    ),
                    aTimelineItemEvent(
                        isMine = true,
                        content = aTimelineItemPollContent("A pinned poll?"),
                        groupPosition = TimelineItemGroupPosition.First,
                        timelineItemReactions = aTimelineItemReactions(0)
                    ),
                )
            )
        )
}

fun aFailedPinnedMessagesListState() = PinnedMessagesListState.Failed

fun aLoadingPinnedMessagesListState() = PinnedMessagesListState.Loading

fun anEmptyPinnedMessagesListState() = PinnedMessagesListState.Empty

fun aLoadedPinnedMessagesListState(
    timelineRoomInfo: TimelineRoomInfo = aTimelineRoomInfo(),
    timelineProtectionState: TimelineProtectionState = aTimelineProtectionState(),
    linkState: LinkState = aLinkState(),
    timelineItems: List<TimelineItem> = emptyList(),
    actionListState: ActionListState = anActionListState(),
    aUserEventPermissions: UserEventPermissions = UserEventPermissions.DEFAULT,
    displayThreadSummaries: Boolean = false,
    eventSink: (PinnedMessagesListEvent) -> Unit = {}
) = PinnedMessagesListState.Filled(
    timelineRoomInfo = timelineRoomInfo,
    timelineProtectionState = timelineProtectionState,
    linkState = linkState,
    timelineItems = timelineItems.toImmutableList(),
    actionListState = actionListState,
    userEventPermissions = aUserEventPermissions,
    displayThreadSummaries = displayThreadSummaries,
    eventSink = eventSink,
)
