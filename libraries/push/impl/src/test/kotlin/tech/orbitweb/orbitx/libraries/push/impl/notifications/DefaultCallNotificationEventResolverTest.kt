/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.api.notification.NotificationContent
import tech.orbitweb.orbitx.libraries.matrix.api.notification.RtcNotificationType
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME_2
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClientProvider
import tech.orbitweb.orbitx.libraries.matrix.test.notification.aNotificationData
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeBaseRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomInfo
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableMessageEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableRingingCallEvent
import tech.orbitweb.orbitx.services.appnavstate.test.FakeAppForegroundStateService
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultCallNotificationEventResolverTest {
    @Test
    fun `resolve CallNotify - RING when call is still ongoing`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
                sessionId = A_SESSION_ID,
                roomId = A_ROOM_ID,
                // The call is still ongoing
                initialRoomInfo = aRoomInfo(hasRoomCall = true),
            )
        )
        val client = FakeMatrixClient().apply {
            givenGetRoomResult(A_ROOM_ID, room)
        }

        val resolver = createDefaultNotifiableEventResolver(
            clientProvider = FakeMatrixClientProvider(getClient = { Result.success(client) }),
        )
        val expectedResult = NotifiableRingingCallEvent(
            sessionId = A_SESSION_ID,
            roomId = A_ROOM_ID,
            eventId = AN_EVENT_ID,
            senderId = A_USER_ID_2,
            roomName = A_ROOM_NAME,
            editedEventId = null,
            description = "📹 Incoming call",
            timestamp = 567L,
            canBeReplaced = true,
            isRedacted = false,
            isUpdated = false,
            senderDisambiguatedDisplayName = A_USER_NAME_2,
            senderAvatarUrl = null,
            expirationTimestamp = 1567L,
            rtcNotificationType = RtcNotificationType.RING,
            callIntent = CallIntent.VIDEO
        )

        val notificationData = aNotificationData(
            content = NotificationContent.MessageLike.RtcNotification(A_USER_ID_2, RtcNotificationType.RING, CallIntent.VIDEO, 1567)
        )
        val result = resolver.resolveEvent(A_SESSION_ID, notificationData)
        assertThat(result.getOrNull()).isEqualTo(expectedResult)
    }

    @Test
    fun `resolve CallNotify - NOTIFY`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
                sessionId = A_SESSION_ID,
                roomId = A_ROOM_ID,
                // The call already ended
                initialRoomInfo = aRoomInfo(hasRoomCall = true),
            )
        )
        val client = FakeMatrixClient().apply {
            givenGetRoomResult(A_ROOM_ID, room)
        }

        val resolver = createDefaultNotifiableEventResolver(
            clientProvider = FakeMatrixClientProvider(getClient = { Result.success(client) }),
        )
        val expectedResult = NotifiableMessageEvent(
            sessionId = A_SESSION_ID,
            roomId = A_ROOM_ID,
            eventId = AN_EVENT_ID,
            senderId = A_USER_ID_2,
            roomName = A_ROOM_NAME,
            editedEventId = null,
            body = "📹 Incoming call",
            timestamp = 567L,
            canBeReplaced = false,
            isRedacted = false,
            isUpdated = false,
            senderDisambiguatedDisplayName = A_USER_NAME_2,
            noisy = true,
            imageUriString = null,
            imageMimeType = null,
            threadId = null,
            type = "org.matrix.msc4075.rtc.notification",
        )

        val notificationData = aNotificationData(
            content = NotificationContent.MessageLike.RtcNotification(A_USER_ID_2, RtcNotificationType.NOTIFY, CallIntent.AUDIO, 0)
        )
        val result = resolver.resolveEvent(A_SESSION_ID, notificationData)
        assertThat(result.getOrNull()).isEqualTo(expectedResult)
    }

    @Test
    fun `resolve CallNotify - RING but timed out displays the same as NOTIFY`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
                sessionId = A_SESSION_ID,
                roomId = A_ROOM_ID,
                // The call already ended
                initialRoomInfo = aRoomInfo(hasRoomCall = false),
            )
        )
        val client = FakeMatrixClient().apply {
            givenGetRoomResult(A_ROOM_ID, room)
        }

        val resolver = createDefaultNotifiableEventResolver(
            clientProvider = FakeMatrixClientProvider(getClient = { Result.success(client) }),
        )
        val expectedResult = NotifiableMessageEvent(
            sessionId = A_SESSION_ID,
            roomId = A_ROOM_ID,
            eventId = AN_EVENT_ID,
            senderId = A_USER_ID_2,
            roomName = A_ROOM_NAME,
            editedEventId = null,
            body = "📹 Incoming call",
            timestamp = 567L,
            canBeReplaced = false,
            isRedacted = false,
            isUpdated = false,
            senderDisambiguatedDisplayName = A_USER_NAME_2,
            noisy = true,
            imageUriString = null,
            imageMimeType = null,
            threadId = null,
            type = "org.matrix.msc4075.rtc.notification",
        )

        val notificationData = aNotificationData(
            content = NotificationContent.MessageLike.RtcNotification(A_USER_ID_2, RtcNotificationType.RING, CallIntent.VIDEO, 0)
        )
        val result = resolver.resolveEvent(A_SESSION_ID, notificationData)
        assertThat(result.getOrNull()).isEqualTo(expectedResult)
    }

    private fun createDefaultNotifiableEventResolver(
        stringProvider: FakeStringProvider = FakeStringProvider(defaultResult = "\uD83D\uDCF9 Incoming call"),
        appForegroundStateService: FakeAppForegroundStateService = FakeAppForegroundStateService(),
        clientProvider: FakeMatrixClientProvider = FakeMatrixClientProvider(),
    ) = DefaultCallNotificationEventResolver(
        stringProvider = stringProvider,
        appForegroundStateService = appForegroundStateService,
        clientProvider = clientProvider,
    )
}
