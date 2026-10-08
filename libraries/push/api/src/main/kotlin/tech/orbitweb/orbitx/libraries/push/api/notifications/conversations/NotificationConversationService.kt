/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.api.notifications.conversations

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

/**
 * Service to handle conversation-related notifications.
 */
interface NotificationConversationService {
    /**
     * Called when a new message is received in a room.
     * It should create a new conversation shortcut for this room.
     */
    suspend fun onSendMessage(
        sessionId: SessionId,
        roomId: RoomId,
        roomName: String?,
        roomIsDirect: Boolean,
        roomAvatarUrl: String?,
    )

    /**
     * Called when a room is left.
     * It should remove the conversation shortcut for this room.
     */
    suspend fun onLeftRoom(sessionId: SessionId, roomId: RoomId)

    /**
     * Called when the list of available rooms changes.
     * It should update the conversation shortcuts accordingly, removing shortcuts for no longer joined rooms.
     */
    suspend fun onAvailableRoomsChanged(sessionId: SessionId, roomIds: Set<RoomId>)
}
