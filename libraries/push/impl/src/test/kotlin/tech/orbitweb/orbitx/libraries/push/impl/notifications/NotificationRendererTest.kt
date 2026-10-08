/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications

import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.features.enterprise.test.FakeEnterpriseService
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.ui.media.test.FakeImageLoader
import tech.orbitweb.orbitx.libraries.push.api.notifications.NotificationIdProvider
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fake.FakeActiveNotificationsProvider
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fake.FakeNotificationCreator
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fake.FakeNotificationDataFactory
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fake.FakeNotificationDisplayer
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fake.FakeRoomGroupMessageCreator
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fake.FakeSummaryGroupMessageCreator
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures.A_NOTIFICATION
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures.aNotifiableMessageEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures.aSimpleNotifiableEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures.anInviteNotifiableEvent
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableEvent
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.test.InMemorySessionStore
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

private const val MY_USER_DISPLAY_NAME = "display-name"
private const val MY_USER_AVATAR_URL = "avatar-url"
private const val USE_COMPLETE_NOTIFICATION_FORMAT = true

private val A_SUMMARY_NOTIFICATION = SummaryNotification.Update(A_NOTIFICATION)
private val ONE_SHOT_NOTIFICATION =
    OneShotNotification(notification = A_NOTIFICATION, tag = "ignored", isNoisy = false, timestamp = -1)

class NotificationRendererTest : RobolectricTest() {
    private val notificationDisplayer = FakeNotificationDisplayer()

    private val notificationCreator = FakeNotificationCreator()
    private val roomGroupMessageCreator = FakeRoomGroupMessageCreator()
    private val summaryGroupMessageCreator = FakeSummaryGroupMessageCreator()
    private val notificationDataFactory = DefaultNotificationDataFactory(
        notificationCreator = notificationCreator,
        roomGroupMessageCreator = roomGroupMessageCreator,
        summaryGroupMessageCreator = summaryGroupMessageCreator,
        activeNotificationsProvider = FakeActiveNotificationsProvider(),
    )
    private val notificationIdProvider = NotificationIdProvider

    private val notificationRenderer = createNotificationRenderer(
        notificationDisplayer = notificationDisplayer,
        notificationDataFactory = notificationDataFactory,
    )

    @Test
    fun `given no notifications when rendering then cancels summary notification`() = runTest {
        renderEventsAsNotifications(emptyList())

        notificationDisplayer.verifySummaryCancelled()
    }

    @Test
    fun `given a room message group notification is added when rendering then show the message notification and update summary`() = runTest {
        roomGroupMessageCreator.createRoomMessageResult = lambdaRecorder { _, _, _, _, _, _ -> A_NOTIFICATION }

        renderEventsAsNotifications(listOf(aNotifiableMessageEvent()))

        notificationDisplayer.showNotificationResult.assertions().isCalledExactly(2).withSequence(
            listOf(value(A_ROOM_ID.value), value(notificationIdProvider.getRoomMessagesNotificationId(A_SESSION_ID)), value(A_NOTIFICATION)),
            listOf(value(null), value(notificationIdProvider.getSummaryNotificationId(A_SESSION_ID)), value(A_SUMMARY_NOTIFICATION.notification))
        )
    }

    @Test
    fun `given a simple notification is added when rendering then show the simple notification and update summary`() = runTest {
        notificationCreator.createSimpleNotificationResult = lambdaRecorder { _, _ -> ONE_SHOT_NOTIFICATION.copy(tag = AN_EVENT_ID.value).notification }

        renderEventsAsNotifications(listOf(aSimpleNotifiableEvent(eventId = AN_EVENT_ID)))

        notificationDisplayer.showNotificationResult.assertions().isCalledExactly(2).withSequence(
            listOf(value(AN_EVENT_ID.value), value(notificationIdProvider.getRoomEventNotificationId(A_SESSION_ID)), value(A_NOTIFICATION)),
            listOf(value(null), value(notificationIdProvider.getSummaryNotificationId(A_SESSION_ID)), value(A_SUMMARY_NOTIFICATION.notification))
        )
    }

    @Test
    fun `given an invitation notification is added when rendering then show the invitation notification and update summary`() = runTest {
        notificationCreator.createRoomInvitationNotificationResult = lambdaRecorder { _, _ -> ONE_SHOT_NOTIFICATION.copy(tag = AN_EVENT_ID.value).notification }

        renderEventsAsNotifications(listOf(anInviteNotifiableEvent()))

        notificationDisplayer.showNotificationResult.assertions().isCalledExactly(2).withSequence(
            listOf(value(A_ROOM_ID.value), value(notificationIdProvider.getRoomInvitationNotificationId(A_SESSION_ID)), value(A_NOTIFICATION)),
            listOf(value(null), value(notificationIdProvider.getSummaryNotificationId(A_SESSION_ID)), value(A_SUMMARY_NOTIFICATION.notification))
        )
    }

    private suspend fun renderEventsAsNotifications(events: List<NotifiableEvent>) {
        notificationRenderer.render(
            MatrixUser(A_SESSION_ID, MY_USER_DISPLAY_NAME, MY_USER_AVATAR_URL),
            useCompleteNotificationFormat = USE_COMPLETE_NOTIFICATION_FORMAT,
            eventsToProcess = events,
            imageLoader = FakeImageLoader(),
        )
    }
}

fun createNotificationRenderer(
    notificationDisplayer: NotificationDisplayer = FakeNotificationDisplayer(),
    notificationDataFactory: NotificationDataFactory = FakeNotificationDataFactory(),
    enterpriseService: EnterpriseService = FakeEnterpriseService(),
    sessionStore: SessionStore = InMemorySessionStore(),
    analyticsService: FakeAnalyticsService = FakeAnalyticsService(),
) = NotificationRenderer(
    notificationDisplayer = notificationDisplayer,
    notificationDataFactory = notificationDataFactory,
    enterpriseService = enterpriseService,
    sessionStore = sessionStore,
    analyticsService = analyticsService,
)
