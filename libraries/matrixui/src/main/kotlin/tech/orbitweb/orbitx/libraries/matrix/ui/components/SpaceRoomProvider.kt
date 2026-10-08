/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.components

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.designsystem.preview.SPACE_NAME
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.CurrentUserMembership
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomType
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import tech.orbitweb.orbitx.libraries.previewutils.room.aSpaceRoom

class SpaceRoomProvider : PreviewParameterProvider<SpaceRoom> {
    override val values: Sequence<SpaceRoom> = sequenceOf(
        aSpaceRoom(
            roomType = RoomType.Room,
            displayName = "Room name with topic",
            topic = "Room topic that is quite long and might be truncated"
        ),
        aSpaceRoom(
            roomType = RoomType.Room,
            displayName = "Room name no topic",
            state = CurrentUserMembership.LEFT,
        ),
        aSpaceRoom(
            displayName = SPACE_NAME,
            roomType = RoomType.Room,
            isDirect = true,
            heroes = listOf(aMatrixUser(displayName = USER_NAME_ALICE)),
            state = CurrentUserMembership.JOINED,
            numJoinedMembers = 2,
        ),
        aSpaceRoom(
            roomType = RoomType.Room,
            displayName = "Room name with topic",
            topic = "Room topic that is quite long and might be truncated",
            state = CurrentUserMembership.INVITED,
        ),
        aSpaceRoom(
            roomType = RoomType.Room,
            displayName = "Room name no topic",
            state = CurrentUserMembership.INVITED,
        ),
        aSpaceRoom(
            numJoinedMembers = 5,
            childrenCount = 10,
            worldReadable = true,
            roomId = RoomId("!spaceId0:example.com"),
        ),
        aSpaceRoom(
            numJoinedMembers = 5,
            childrenCount = 10,
            worldReadable = true,
            avatarUrl = "anUrl",
            roomId = RoomId("!spaceId1:example.com"),
            state = CurrentUserMembership.LEFT,
        ),
        aSpaceRoom(
            numJoinedMembers = 5,
            childrenCount = 10,
            worldReadable = true,
            avatarUrl = "anUrl",
            roomId = RoomId("!spaceId2:example.com"),
            state = CurrentUserMembership.INVITED,
        ),
        aSpaceRoom(
            displayName = SPACE_NAME,
            roomType = RoomType.Space,
            heroes = listOf(aMatrixUser(displayName = USER_NAME_ALICE)),
            state = CurrentUserMembership.JOINED,
            numJoinedMembers = 2,
        ),
    )
}
