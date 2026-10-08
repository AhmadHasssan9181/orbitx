/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.test

import tech.orbitweb.orbitx.features.invite.api.SeenInvitesStore
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemorySeenInvitesStore(
    initialRoomIds: Set<RoomId> = emptySet(),
) : SeenInvitesStore {
    private val roomIds = MutableStateFlow(initialRoomIds)

    override fun seenRoomIds(): Flow<Set<RoomId>> = roomIds

    override suspend fun markAsSeen(roomId: RoomId) {
        roomIds.value += roomId
    }

    override suspend fun markAsUnSeen(roomId: RoomId) {
        roomIds.value -= roomId
    }

    override suspend fun clear() {
        roomIds.value = emptySet()
    }
}
