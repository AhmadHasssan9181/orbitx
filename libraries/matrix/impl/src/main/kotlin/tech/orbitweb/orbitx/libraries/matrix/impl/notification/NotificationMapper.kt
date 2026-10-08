/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.notification

import org.matrix.rustcomponents.sdk.NotificationEvent
import org.matrix.rustcomponents.sdk.NotificationItem
import org.matrix.rustcomponents.sdk.use
import tech.orbitweb.orbitx.libraries.core.bool.orFalse
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.notification.NotificationContent
import tech.orbitweb.orbitx.libraries.matrix.api.notification.NotificationData
import tech.orbitweb.orbitx.libraries.matrix.impl.room.join.map
import tech.orbitweb.orbitx.services.toolbox.api.systemclock.SystemClock

class NotificationMapper(
    private val clock: SystemClock,
) {
    private val notificationContentMapper = NotificationContentMapper()

    fun map(
        sessionId: SessionId,
        eventId: EventId,
        roomId: RoomId,
        notificationItem: NotificationItem
    ): Result<NotificationData> {
        return runCatchingExceptions {
            notificationItem.use { item ->
                val timestamp = item.timestamp() ?: clock.epochMillis()
                NotificationData(
                    sessionId = sessionId,
                    eventId = eventId,
                    threadId = item.threadId?.let(::ThreadId),
                    roomId = roomId,
                    senderAvatarUrl = item.senderInfo.avatarUrl,
                    senderDisplayName = item.senderInfo.displayName,
                    senderIsNameAmbiguous = item.senderInfo.isNameAmbiguous,
                    roomAvatarUrl = item.roomInfo.avatarUrl ?: item.senderInfo.avatarUrl.takeIf { item.roomInfo.isDm },
                    roomDisplayName = item.roomInfo.displayName,
                    isDirect = item.roomInfo.isDirect,
                    isDm = item.roomInfo.isDm,
                    isSpace = item.roomInfo.isSpace,
                    isEncrypted = item.roomInfo.isEncrypted.orFalse(),
                    isNoisy = item.isNoisy.orFalse(),
                    timestamp = timestamp,
                    content = notificationContentMapper.map(item.event).getOrThrow(),
                    hasMention = item.hasMention.orFalse(),
                    roomJoinRule = item.roomInfo.joinRule?.map(),
                )
            }
        }
    }
}

class NotificationContentMapper {
    private val timelineEventToNotificationContentMapper = TimelineEventToNotificationContentMapper()

    fun map(notificationEvent: NotificationEvent): Result<NotificationContent> =
        when (notificationEvent) {
            is NotificationEvent.Timeline -> timelineEventToNotificationContentMapper.map(notificationEvent.event)
            is NotificationEvent.Invite -> Result.success(
                NotificationContent.Invite(
                    senderId = UserId(notificationEvent.sender),
                )
            )
        }
}

private fun NotificationItem.timestamp(): Long? {
    return (this.event as? NotificationEvent.Timeline)?.event?.timestamp()?.toLong()
}
