/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.notification

import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.notification.NotificationContent
import tech.orbitweb.orbitx.libraries.matrix.api.notification.NotificationData
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_TIMESTAMP
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME_2

fun aNotificationData(
    content: NotificationContent = NotificationContent.MessageLike.RoomEncrypted,
    isDirect: Boolean = false,
    isSpace: Boolean = false,
    hasMention: Boolean = false,
    threadId: ThreadId? = null,
    timestamp: Long = A_TIMESTAMP,
    senderDisplayName: String? = A_USER_NAME_2,
    senderIsNameAmbiguous: Boolean = false,
    roomDisplayName: String? = A_ROOM_NAME,
    roomJoinRule: JoinRule? = null,
): NotificationData {
    return NotificationData(
        sessionId = A_SESSION_ID,
        eventId = AN_EVENT_ID,
        threadId = threadId,
        roomId = A_ROOM_ID,
        senderAvatarUrl = null,
        senderDisplayName = senderDisplayName,
        senderIsNameAmbiguous = senderIsNameAmbiguous,
        roomAvatarUrl = null,
        roomDisplayName = roomDisplayName,
        isDirect = isDirect,
        isDm = false,
        isSpace = isSpace,
        isEncrypted = false,
        isNoisy = false,
        timestamp = timestamp,
        content = content,
        hasMention = hasMention,
        roomJoinRule = roomJoinRule,
    )
}
