/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.room

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.CurrentUserMembership
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMembershipDetails
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomType
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.preview.RoomPreviewInfo
import tech.orbitweb.orbitx.libraries.matrix.test.AN_AVATAR_URL
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_TOPIC
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

fun aRoomPreview(
    localRoom: FakeBaseRoom? = null,
    info: RoomPreviewInfo = aRoomPreviewInfo(),
    roomMembershipDetails: () -> Result<RoomMembershipDetails?> = { lambdaError() },
) = FakeNotJoinedRoom(
    localRoom = localRoom,
    previewInfo = info,
    roomMembershipDetails = roomMembershipDetails,
)

fun aRoomPreviewInfo(
    roomId: RoomId = A_ROOM_ID,
    name: String? = A_ROOM_NAME,
    topic: String? = A_ROOM_TOPIC,
    avatarUrl: String? = AN_AVATAR_URL,
    joinRule: JoinRule = JoinRule.Public,
    isSpace: Boolean = false,
    canonicalAlias: RoomAlias? = null,
    currentUserMembership: CurrentUserMembership? = null,
    numberOfJoinedMembers: Long = 1,
    isHistoryWorldReadable: Boolean = true,
) = RoomPreviewInfo(
    roomId = roomId,
    name = name,
    topic = topic,
    avatarUrl = avatarUrl,
    joinRule = joinRule,
    canonicalAlias = canonicalAlias,
    numberOfJoinedMembers = numberOfJoinedMembers,
    roomType = if (isSpace) RoomType.Space else RoomType.Room,
    isHistoryWorldReadable = isHistoryWorldReadable,
    membership = currentUserMembership,
)
