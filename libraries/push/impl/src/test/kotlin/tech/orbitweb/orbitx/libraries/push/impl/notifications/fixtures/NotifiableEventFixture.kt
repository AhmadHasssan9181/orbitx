/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.api.notification.RtcNotificationType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventType
import tech.orbitweb.orbitx.libraries.matrix.test.AN_AVATAR_URL
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_MESSAGE
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_TIMESTAMP
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME_2
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.FallbackNotifiableEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.InviteNotifiableEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableMessageEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableRingingCallEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.SimpleNotifiableEvent
import tech.orbitweb.orbitx.services.toolbox.test.systemclock.A_FAKE_TIMESTAMP

fun aSimpleNotifiableEvent(
    sessionId: SessionId = A_SESSION_ID,
    roomId: RoomId = A_ROOM_ID,
    eventId: EventId = AN_EVENT_ID,
    type: String? = null,
    isRedacted: Boolean = false,
    canBeReplaced: Boolean = false,
    editedEventId: EventId? = null
) = SimpleNotifiableEvent(
    sessionId = sessionId,
    roomId = roomId,
    eventId = eventId,
    editedEventId = editedEventId,
    noisy = false,
    title = "title",
    description = "description",
    type = type,
    timestamp = 0,
    soundName = null,
    canBeReplaced = canBeReplaced,
    isRedacted = isRedacted
)

fun anInviteNotifiableEvent(
    sessionId: SessionId = A_SESSION_ID,
    roomId: RoomId = A_ROOM_ID,
    eventId: EventId = AN_EVENT_ID,
    isRedacted: Boolean = false
) = InviteNotifiableEvent(
    sessionId = sessionId,
    eventId = eventId,
    roomId = roomId,
    roomName = A_ROOM_NAME,
    editedEventId = null,
    noisy = false,
    title = "title",
    description = "description",
    type = null,
    timestamp = 0,
    soundName = null,
    canBeReplaced = false,
    isRedacted = isRedacted
)

fun aNotifiableMessageEvent(
    body: String = A_MESSAGE,
    sessionId: SessionId = A_SESSION_ID,
    roomId: RoomId = A_ROOM_ID,
    eventId: EventId = AN_EVENT_ID,
    threadId: ThreadId? = null,
    isRedacted: Boolean = false,
    hasMentionOrReply: Boolean = false,
    timestamp: Long = A_TIMESTAMP,
    type: String = EventType.MESSAGE,
    senderId: UserId = A_USER_ID_2,
    senderDisambiguatedDisplayName: String = A_USER_NAME_2,
    roomName: String? = A_ROOM_NAME,
) = NotifiableMessageEvent(
    sessionId = sessionId,
    eventId = eventId,
    editedEventId = null,
    noisy = false,
    timestamp = timestamp,
    senderDisambiguatedDisplayName = senderDisambiguatedDisplayName,
    senderId = senderId,
    body = body,
    roomId = roomId,
    threadId = threadId,
    roomName = roomName,
    canBeReplaced = false,
    isRedacted = isRedacted,
    imageUriString = null,
    imageMimeType = null,
    roomAvatarPath = null,
    senderAvatarPath = null,
    soundName = null,
    outGoingMessage = false,
    outGoingMessageFailed = false,
    isUpdated = false,
    hasMentionOrReply = hasMentionOrReply,
    type = type,
)

fun aNotifiableCallEvent(
    sessionId: SessionId = A_SESSION_ID,
    roomId: RoomId = A_ROOM_ID,
    eventId: EventId = AN_EVENT_ID,
    senderId: UserId = A_USER_ID_2,
    senderName: String? = null,
    roomAvatarUrl: String? = AN_AVATAR_URL,
    senderAvatarUrl: String? = AN_AVATAR_URL,
    rtcNotificationType: RtcNotificationType = RtcNotificationType.NOTIFY,
    timestamp: Long = 0L,
    expirationTimestamp: Long = 0L,
    callIntent: CallIntent = CallIntent.VIDEO,
) = NotifiableRingingCallEvent(
    sessionId = sessionId,
    eventId = eventId,
    roomId = roomId,
    roomName = A_ROOM_NAME,
    editedEventId = null,
    description = "description",
    timestamp = timestamp,
    expirationTimestamp = expirationTimestamp,
    canBeReplaced = false,
    isRedacted = false,
    isUpdated = false,
    senderDisambiguatedDisplayName = senderName,
    senderId = senderId,
    roomAvatarUrl = roomAvatarUrl,
    senderAvatarUrl = senderAvatarUrl,
    rtcNotificationType = rtcNotificationType,
    callIntent = callIntent,
)

fun aFallbackNotifiableEvent(
    sessionId: SessionId = A_SESSION_ID,
) = FallbackNotifiableEvent(
    sessionId = sessionId,
    roomId = A_ROOM_ID,
    eventId = AN_EVENT_ID,
    editedEventId = null,
    description = "A fallback notification",
    canBeReplaced = false,
    isRedacted = false,
    isUpdated = false,
    timestamp = A_FAKE_TIMESTAMP,
    cause = "Unable to decrypt event",
)
