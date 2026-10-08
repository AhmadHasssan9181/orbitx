/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClientProvider
import tech.orbitweb.orbitx.libraries.matrix.test.notification.FakeNotificationService
import tech.orbitweb.orbitx.libraries.matrix.test.notification.aNotificationData
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fake.FakeNotificationDataFactory
import tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures.aNotifiableMessageEvent
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder

class DefaultOnMissedCallNotificationHandlerTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `addMissedCallNotification - should add missed call notification`() = runTest {
        val dataFactory = FakeNotificationDataFactory(
            messageEventToNotificationsResult = lambdaRecorder { _, _, _ -> emptyList() }
        )
        // Create a fake matrix client provider that returns a fake matrix client with a fake notification service that returns a valid notification data
        val matrixClientProvider = FakeMatrixClientProvider(getClient = {
            val notificationService = FakeNotificationService().apply {
                givenGetNotificationsResult(
                    Result.success(mapOf(AN_EVENT_ID to Result.success(aNotificationData(senderDisplayName = A_USER_NAME, senderIsNameAmbiguous = false))))
                )
            }
            Result.success(FakeMatrixClient(notificationService = notificationService))
        })
        val defaultOnMissedCallNotificationHandler = DefaultOnMissedCallNotificationHandler(
            matrixClientProvider = matrixClientProvider,
            defaultNotificationDrawerManager = createDefaultNotificationDrawerManager(
                notificationRenderer = createNotificationRenderer(
                    notificationDataFactory = dataFactory,
                ),
            ),
            callNotificationEventResolver = FakeCallNotificationEventResolver(resolveEventLambda = { _, _, _ ->
                Result.success(aNotifiableMessageEvent())
            }),
        )

        defaultOnMissedCallNotificationHandler.addMissedCallNotification(
            sessionId = A_SESSION_ID,
            roomId = A_ROOM_ID,
            eventId = AN_EVENT_ID,
        )

        runCurrent()

        dataFactory.messageEventToNotificationsResult.assertions().isCalledOnce()
    }
}
