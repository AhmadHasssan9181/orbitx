/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.tasks

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.push.test.notifications.FakeNotificationCleaner
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers

class MarkAllRoomsAsReadTest {
    @Test
    fun `invoke - delegates to client and clears all notifications`() = runTest {
        val markAllRoomsAsReadLambda = lambdaRecorder<Result<Unit>> { Result.success(Unit) }
        val clearAllMessagesEventsLambda = lambdaRecorder<SessionId, Unit> { }
        val markAllRoomsAsRead = DefaultMarkAllRoomsAsRead(
            client = FakeMatrixClient(
                markAllRoomsAsReadResult = markAllRoomsAsReadLambda,
            ),
            notificationCleaner = FakeNotificationCleaner(
                clearAllMessagesEventsLambda = clearAllMessagesEventsLambda,
            ),
            coroutineDispatchers = testCoroutineDispatchers(),
        )

        val result = markAllRoomsAsRead()

        assertThat(result.isSuccess).isTrue()
        markAllRoomsAsReadLambda.assertions().isCalledOnce()
        clearAllMessagesEventsLambda.assertions().isCalledOnce().with(value(A_SESSION_ID))
    }

    @Test
    fun `invoke - does not clear notifications when client fails`() = runTest {
        val clearAllMessagesEventsLambda = lambdaRecorder<SessionId, Unit> { }
        val markAllRoomsAsRead = DefaultMarkAllRoomsAsRead(
            client = FakeMatrixClient(
                markAllRoomsAsReadResult = { Result.failure(IllegalStateException("Failed")) },
            ),
            notificationCleaner = FakeNotificationCleaner(
                clearAllMessagesEventsLambda = clearAllMessagesEventsLambda,
            ),
            coroutineDispatchers = testCoroutineDispatchers(),
        )

        val result = markAllRoomsAsRead()

        assertThat(result.isFailure).isTrue()
        clearAllMessagesEventsLambda.assertions().isNeverCalled()
    }
}
