/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.room

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.BaseRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMembersState
import tech.orbitweb.orbitx.libraries.matrix.api.room.getDirectRoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.room.roomMembers

@Composable
fun BaseRoom.getRoomMemberAsState(userId: UserId): State<RoomMember?> {
    val roomMembersState by membersStateFlow.collectAsState()
    return getRoomMemberAsState(roomMembersState = roomMembersState, userId = userId)
}

@Composable
fun getRoomMemberAsState(roomMembersState: RoomMembersState, userId: UserId): State<RoomMember?> {
    val roomMembers = roomMembersState.roomMembers()
    return remember(roomMembers) {
        derivedStateOf {
            roomMembers?.find {
                it.userId == userId
            }
        }
    }
}

@Composable
fun BaseRoom.getDirectRoomMember(roomMembersState: RoomMembersState): State<RoomMember?> {
    val roomInfo by roomInfoFlow.collectAsState()
    return remember(roomInfo.isDm) {
        mutableStateOf(roomMembersState.getDirectRoomMember(roomInfo, sessionId))
    }
}

@Composable
fun BaseRoom.getCurrentRoomMember(roomMembersState: RoomMembersState): State<RoomMember?> {
    return getRoomMemberAsState(roomMembersState = roomMembersState, userId = sessionId)
}
