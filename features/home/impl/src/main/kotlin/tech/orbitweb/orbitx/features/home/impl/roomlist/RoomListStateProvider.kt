/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.roomlist

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.home.impl.filters.RoomListFiltersState
import tech.orbitweb.orbitx.features.home.impl.filters.aRoomListFiltersState
import tech.orbitweb.orbitx.features.home.impl.model.LatestEvent
import tech.orbitweb.orbitx.features.home.impl.model.RoomListRoomSummary
import tech.orbitweb.orbitx.features.home.impl.model.RoomSummaryDisplayType
import tech.orbitweb.orbitx.features.home.impl.model.aRoomListRoomSummary
import tech.orbitweb.orbitx.features.home.impl.model.anInviteSender
import tech.orbitweb.orbitx.features.home.impl.search.RoomListSearchState
import tech.orbitweb.orbitx.features.home.impl.search.aRoomListSearchState
import tech.orbitweb.orbitx.features.home.impl.spacefilters.SpaceFiltersState
import tech.orbitweb.orbitx.features.home.impl.spacefilters.anUnselectedSpaceFiltersState
import tech.orbitweb.orbitx.features.invite.api.acceptdecline.AcceptDeclineInviteState
import tech.orbitweb.orbitx.features.invite.api.acceptdecline.anAcceptDeclineInviteState
import tech.orbitweb.orbitx.features.leaveroom.api.LeaveRoomEvent
import tech.orbitweb.orbitx.features.leaveroom.api.LeaveRoomState
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.push.api.battery.aBatteryOptimizationState

open class RoomListStateProvider : PreviewParameterProvider<RoomListState> {
    override val values: Sequence<RoomListState>
        get() = sequenceOf(
            aRoomListState(),
            aRoomListState(contextMenu = aContextMenuShown(roomName = null)),
            aRoomListState(contextMenu = aContextMenuShown(roomName = "A nice room name")),
            aRoomListState(contextMenu = aContextMenuShown(isFavorite = true)),
            aRoomListState(contentState = aRoomsContentState(securityBannerState = SecurityBannerState.RecoveryKeyConfirmation)),
            aRoomListState(contentState = anEmptyContentState()),
            aRoomListState(contentState = aSkeletonContentState()),
            aRoomListState(searchState = aRoomListSearchState(isSearchActive = true, query = "Test")),
            aRoomListState(contentState = aRoomsContentState(securityBannerState = SecurityBannerState.SetUpRecovery)),
            aRoomListState(contentState = aRoomsContentState(batteryOptimizationState = aBatteryOptimizationState(shouldDisplayBanner = true))),
            aRoomListState(contentState = anEmptyContentState(securityBannerState = SecurityBannerState.RecoveryKeyConfirmation)),
        )
}

internal fun aRoomListState(
    contextMenu: RoomListState.ContextMenu = RoomListState.ContextMenu.Hidden,
    declineInviteMenu: RoomListState.DeclineInviteMenu = RoomListState.DeclineInviteMenu.Hidden,
    leaveRoomState: LeaveRoomState = aLeaveRoomState(),
    searchState: RoomListSearchState = aRoomListSearchState(),
    filtersState: RoomListFiltersState = aRoomListFiltersState(),
    spaceFiltersState: SpaceFiltersState = anUnselectedSpaceFiltersState(),
    contentState: RoomListContentState = aRoomsContentState(),
    acceptDeclineInviteState: AcceptDeclineInviteState = anAcceptDeclineInviteState(),
    hideInvitesAvatars: Boolean = false,
    canReportRoom: Boolean = true,
    eventSink: (RoomListEvent) -> Unit = {}
) = RoomListState(
    contextMenu = contextMenu,
    declineInviteMenu = declineInviteMenu,
    leaveRoomState = leaveRoomState,
    filtersState = filtersState,
    searchState = searchState,
    spaceFiltersState = spaceFiltersState,
    contentState = contentState,
    acceptDeclineInviteState = acceptDeclineInviteState,
    hideInvitesAvatars = hideInvitesAvatars,
    canReportRoom = canReportRoom,
    eventSink = eventSink,
)

internal fun aLeaveRoomState(
    eventSink: (LeaveRoomEvent) -> Unit = {}
) = object : LeaveRoomState {
    override val eventSink: (LeaveRoomEvent) -> Unit = eventSink
}

internal fun aRoomListRoomSummaryList(): ImmutableList<RoomListRoomSummary> {
    return persistentListOf(
        aRoomListRoomSummary(
            name = "Room Invited",
            avatarData = AvatarData("!roomId", "Room with Alice and Bob", size = AvatarSize.RoomListItem),
            id = "!roomId:domain",
            inviteSender = anInviteSender(),
            displayType = RoomSummaryDisplayType.INVITE,
        ),
        aRoomListRoomSummary(
            name = "Room",
            numberOfUnreadMessages = 1,
            timestamp = "14:18",
            latestEvent = LatestEvent.Synced("A very very very very long message which suites on two lines"),
            avatarData = AvatarData("!id", "R", size = AvatarSize.RoomListItem),
            id = "!roomId5:domain",
        ),
        aRoomListRoomSummary(
            name = "Room#2",
            numberOfUnreadMessages = 0,
            timestamp = "14:16",
            latestEvent = LatestEvent.Synced("A short message"),
            avatarData = AvatarData("!id", "Z", size = AvatarSize.RoomListItem),
            id = "!roomId2:domain",
        ),
        aRoomListRoomSummary(
            id = "!roomId3:domain",
            displayType = RoomSummaryDisplayType.PLACEHOLDER,
        ),
        aRoomListRoomSummary(
            id = "!roomId4:domain",
            displayType = RoomSummaryDisplayType.PLACEHOLDER,
        ),
    )
}

internal fun generateRoomListRoomSummaryList(
    numberOfRooms: Int = 10,
): ImmutableList<RoomListRoomSummary> {
    return List(numberOfRooms) { index ->
        aRoomListRoomSummary(
            name = "Room#$index",
            numberOfUnreadMessages = 0,
            timestamp = "14:16",
            latestEvent = LatestEvent.Synced("A message"),
            avatarData = AvatarData("!id$index", "${(65 + index % 26).toChar()}", size = AvatarSize.RoomListItem),
            id = "!roomId$index:domain",
        )
    }.toImmutableList()
}
