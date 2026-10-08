/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.spaces

import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.core.bool.orFalse
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import tech.orbitweb.orbitx.libraries.matrix.impl.room.join.map
import tech.orbitweb.orbitx.libraries.matrix.impl.room.map
import org.matrix.rustcomponents.sdk.SpaceRoom as RustSpaceRoom

class SpaceRoomMapper {
    fun map(spaceRoom: RustSpaceRoom): SpaceRoom {
        return SpaceRoom(
            avatarUrl = spaceRoom.avatarUrl,
            canonicalAlias = spaceRoom.canonicalAlias?.let(::RoomAlias),
            childrenCount = spaceRoom.childrenCount.toInt(),
            guestCanJoin = spaceRoom.guestCanJoin,
            heroes = spaceRoom.heroes.orEmpty().map { it.map() }.toImmutableList(),
            joinRule = spaceRoom.joinRule?.map(),
            rawName = spaceRoom.rawName,
            displayName = spaceRoom.displayName,
            numJoinedMembers = spaceRoom.numJoinedMembers.toInt(),
            roomId = RoomId(spaceRoom.roomId),
            roomType = spaceRoom.roomType.map(),
            state = spaceRoom.state?.map(),
            topic = spaceRoom.topic,
            worldReadable = spaceRoom.worldReadable.orFalse(),
            via = spaceRoom.via.toImmutableList(),
            isDirect = spaceRoom.isDirect,
            isDm = spaceRoom.isDm,
        )
    }
}
