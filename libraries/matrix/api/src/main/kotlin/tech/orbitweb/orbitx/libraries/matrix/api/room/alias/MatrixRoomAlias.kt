/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room.alias

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.api.room.BaseRoom

/**
 * Return true if the given roomIdOrAlias is the same room as this room.
 */
fun BaseRoom.matches(roomIdOrAlias: RoomIdOrAlias): Boolean {
    return when (roomIdOrAlias) {
        is RoomIdOrAlias.Id -> {
            roomIdOrAlias.roomId == roomId
        }
        is RoomIdOrAlias.Alias -> {
            roomIdOrAlias.roomAlias == info().canonicalAlias || roomIdOrAlias.roomAlias in info().alternativeAliases
        }
    }
}
