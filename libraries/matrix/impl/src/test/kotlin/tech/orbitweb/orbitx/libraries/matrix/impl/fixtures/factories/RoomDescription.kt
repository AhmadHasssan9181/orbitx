/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories

import org.matrix.rustcomponents.sdk.PublicRoomJoinRule
import org.matrix.rustcomponents.sdk.RoomDescription
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID

internal fun aRustRoomDescription(
    roomId: String = A_ROOM_ID.value,
    name: String? = "name",
    topic: String? = "topic",
    alias: String? = A_ROOM_ALIAS.value,
    avatarUrl: String? = "avatarUrl",
    joinRule: PublicRoomJoinRule = PublicRoomJoinRule.PUBLIC,
    isWorldReadable: Boolean = true,
    joinedMembers: ULong = 2u,
) = RoomDescription(
    roomId = roomId,
    name = name,
    topic = topic,
    alias = alias,
    avatarUrl = avatarUrl,
    joinRule = joinRule,
    isWorldReadable = isWorldReadable,
    joinedMembers = joinedMembers,
)
