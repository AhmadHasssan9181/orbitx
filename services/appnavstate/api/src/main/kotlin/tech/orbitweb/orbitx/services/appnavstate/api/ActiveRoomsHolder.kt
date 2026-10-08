/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.appnavstate.api

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom

/**
 * Holds the active rooms for a given session so they can be reused instead of instantiating new ones.
 */
interface ActiveRoomsHolder {
    /**
     * Adds a new held room for the given sessionId.
     */
    fun addRoom(room: JoinedRoom)

    /**
     * Returns the last room added for the given [sessionId] or null if no room was added.
     */
    fun getActiveRoom(sessionId: SessionId): JoinedRoom?

    /**
     * Returns an active room associated to the given [sessionId], with the given [roomId], or null if none match.
     */
    fun getActiveRoomMatching(sessionId: SessionId, roomId: RoomId): JoinedRoom?

    /**
     * Removes any room matching the provided [sessionId] and [roomId].
     */
    fun removeRoom(sessionId: SessionId, roomId: RoomId)

    /**
     * Clears all the rooms for the given sessionId.
     */
    fun clear(sessionId: SessionId)
}
