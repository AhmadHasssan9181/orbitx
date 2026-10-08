/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl.root

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import tech.orbitweb.orbitx.features.invite.api.acceptdecline.AcceptDeclineInviteState
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomInfo
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom

data class SpaceState(
    val spaceInfo: RoomInfo,
    val children: ImmutableList<SpaceRoom>,
    val seenSpaceInvites: ImmutableSet<RoomId>,
    val hideInvitesAvatar: Boolean,
    val hasMoreToLoad: Boolean,
    val joinActions: ImmutableMap<RoomId, AsyncAction<Unit>>,
    val acceptDeclineInviteState: AcceptDeclineInviteState,
    val topicViewerState: TopicViewerState,
    val canAccessSpaceSettings: Boolean,
    val isManageMode: Boolean,
    val selectedRoomIds: ImmutableSet<RoomId>,
    val canEditSpaceGraph: Boolean,
    val removeRoomsAction: AsyncAction<Unit>,
    val eventSink: (SpaceEvents) -> Unit
) {
    fun isJoining(spaceId: RoomId): Boolean = joinActions[spaceId] == AsyncAction.Loading
    fun isSelected(spaceId: RoomId): Boolean = selectedRoomIds.contains(spaceId)
    val hasAnyJoinFailures: Boolean = joinActions.values.any {
        it is AsyncAction.Failure
    }

    val showManageRoomsAction: Boolean = canEditSpaceGraph && children.any { spaceRoom -> !spaceRoom.isSpace }
    val selectedCount: Int = selectedRoomIds.size
    val isRemoveButtonEnabled: Boolean = selectedRoomIds.isNotEmpty()
}

@Immutable
sealed interface TopicViewerState {
    data object Hidden : TopicViewerState
    data class Shown(val topic: String) : TopicViewerState
}
