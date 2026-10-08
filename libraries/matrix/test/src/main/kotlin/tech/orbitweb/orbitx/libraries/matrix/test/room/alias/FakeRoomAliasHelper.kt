/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.room.alias

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.room.alias.RoomAliasHelper

class FakeRoomAliasHelper(
    private val roomAliasNameFromRoomDisplayNameLambda: (String) -> String = { name ->
        name.trimStart().trimEnd().replace(" ", "_")
    },
    private val isRoomAliasValidLambda: (RoomAlias) -> Boolean = { true }
) : RoomAliasHelper {
    override fun roomAliasNameFromRoomDisplayName(name: String): String {
        return roomAliasNameFromRoomDisplayNameLambda(name)
    }

    override fun isRoomAliasValid(roomAlias: RoomAlias): Boolean {
        return isRoomAliasValidLambda(roomAlias)
    }
}
