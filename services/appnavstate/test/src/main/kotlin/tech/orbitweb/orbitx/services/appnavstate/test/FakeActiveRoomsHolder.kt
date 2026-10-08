/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.appnavstate.test

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.services.appnavstate.api.ActiveRoomsHolder

class FakeActiveRoomsHolder : ActiveRoomsHolder {
    private var room: JoinedRoom? = null

    override fun addRoom(room: JoinedRoom) {
        this.room = room
    }

    override fun getActiveRoom(sessionId: SessionId): JoinedRoom? {
        return room
    }

    override fun getActiveRoomMatching(sessionId: SessionId, roomId: RoomId): JoinedRoom? {
        return null
    }

    override fun removeRoom(sessionId: SessionId, roomId: RoomId) {
        room = null
    }

    override fun clear(sessionId: SessionId) {
    }
}
