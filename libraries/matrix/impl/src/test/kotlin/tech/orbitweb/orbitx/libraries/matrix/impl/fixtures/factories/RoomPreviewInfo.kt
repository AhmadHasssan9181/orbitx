/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories

import org.matrix.rustcomponents.sdk.JoinRule
import org.matrix.rustcomponents.sdk.Membership
import org.matrix.rustcomponents.sdk.RoomPreviewInfo
import org.matrix.rustcomponents.sdk.RoomType
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID

internal fun aRustRoomPreviewInfo(
    canonicalAlias: String? = A_ROOM_ALIAS.value,
    membership: Membership? = Membership.JOINED,
    joinRule: JoinRule = JoinRule.Public,
) = RoomPreviewInfo(
    roomId = A_ROOM_ID.value,
    canonicalAlias = canonicalAlias,
    name = "name",
    topic = "topic",
    avatarUrl = "avatarUrl",
    numJoinedMembers = 1u,
    numActiveMembers = 1u,
    isDirect = false,
    roomType = RoomType.Room,
    isHistoryWorldReadable = true,
    membership = membership,
    joinRule = joinRule,
    heroes = null,
)
