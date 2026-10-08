/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.fake

import android.service.notification.StatusBarNotification
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.push.impl.notifications.ActiveNotificationsProvider

class FakeActiveNotificationsProvider(
    private val getMessageNotificationsForRoomResult: (SessionId, RoomId, ThreadId?) -> List<StatusBarNotification> = { _, _, _ -> emptyList() },
    private val getAllMessageNotificationsForRoomResult: (SessionId, RoomId) -> List<StatusBarNotification> = { _, _ -> emptyList() },
    private val getNotificationsForSessionResult: (SessionId) -> List<StatusBarNotification> = { emptyList() },
    private val getMembershipNotificationForSessionResult: (SessionId) -> List<StatusBarNotification> = { emptyList() },
    private val getMembershipNotificationForRoomResult: (SessionId, RoomId) -> List<StatusBarNotification> = { _, _ -> emptyList() },
    private val getSummaryNotificationResult: (SessionId) -> StatusBarNotification? = { null },
    private val getFallbackNotificationResult: (SessionId) -> StatusBarNotification? = { null },
    private val countResult: (SessionId) -> Int = { 0 },
) : ActiveNotificationsProvider {
    override fun getMessageNotificationsForRoom(sessionId: SessionId, roomId: RoomId, threadId: ThreadId?): List<StatusBarNotification> {
        return getMessageNotificationsForRoomResult(sessionId, roomId, threadId)
    }

    override fun getAllMessageNotificationsForRoom(sessionId: SessionId, roomId: RoomId): List<StatusBarNotification> {
        return getAllMessageNotificationsForRoomResult(sessionId, roomId)
    }

    override fun getNotificationsForSession(sessionId: SessionId): List<StatusBarNotification> {
        return getNotificationsForSessionResult(sessionId)
    }

    override fun getMembershipNotificationForSession(sessionId: SessionId): List<StatusBarNotification> {
        return getMembershipNotificationForSessionResult(sessionId)
    }

    override fun getMembershipNotificationForRoom(sessionId: SessionId, roomId: RoomId): List<StatusBarNotification> {
        return getMembershipNotificationForRoomResult(sessionId, roomId)
    }

    override fun getSummaryNotification(sessionId: SessionId): StatusBarNotification? {
        return getSummaryNotificationResult(sessionId)
    }

    override fun getFallbackNotification(sessionId: SessionId): StatusBarNotification? {
        return getFallbackNotificationResult(sessionId)
    }

    override fun count(sessionId: SessionId): Int {
        return countResult(sessionId)
    }
}
