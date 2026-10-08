/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.api.notifications

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId

interface NotificationCleaner {
    fun clearAllMessagesEvents(sessionId: SessionId)
    fun clearMessagesForRoom(sessionId: SessionId, roomId: RoomId)
    fun clearMessagesForThread(sessionId: SessionId, roomId: RoomId, threadId: ThreadId)
    fun clearEvent(sessionId: SessionId, eventId: EventId)

    fun clearMembershipNotificationForSession(sessionId: SessionId)
    fun clearMembershipNotificationForRoom(sessionId: SessionId, roomId: RoomId)
}
