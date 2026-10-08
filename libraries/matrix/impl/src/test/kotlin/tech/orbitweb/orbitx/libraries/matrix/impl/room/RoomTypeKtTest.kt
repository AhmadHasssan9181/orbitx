/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */
package tech.orbitweb.orbitx.libraries.matrix.impl.room

import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomType
import org.matrix.rustcomponents.sdk.RoomType as RustRoomType

class RoomTypeKtTest {
    @Test
    fun toRoomType() {
        assert(RustRoomType.Room.map() == RoomType.Room)
        assert(RustRoomType.Space.map() == RoomType.Space)
        assert(RustRoomType.Custom("m.other").map() == RoomType.Other("m.other"))
    }
}
