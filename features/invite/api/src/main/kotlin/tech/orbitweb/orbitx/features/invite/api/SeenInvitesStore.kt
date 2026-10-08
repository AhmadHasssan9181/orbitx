/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.api

import kotlinx.coroutines.flow.Flow
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

interface SeenInvitesStore {
    /**
     * Returns a flow of seen room IDs of invitation.
     */
    fun seenRoomIds(): Flow<Set<RoomId>>

    /**
     * Mark the invitation as seen.
     * Call this when the invitation details are shown to the user.
     * @param roomId the room ID of the invitation to mark as seen.
     */
    suspend fun markAsSeen(roomId: RoomId)

    /**
     * Mark the invitation as unseen.
     * Call this when the invitation has been accepted or declined.
     * @param roomId the room ID of the invitation to mark as unseen.
     */
    suspend fun markAsUnSeen(roomId: RoomId)

    /**
     * Delete the store.
     */
    suspend fun clear()
}
