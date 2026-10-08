/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.impl.watchers

import tech.orbitweb.orbitx.libraries.matrix.api.room.SendQueueUpdate
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_TRANSACTION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.services.analytics.api.NoopAnalyticsTransaction
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultAnalyticsSendMessageWatcherTest {
    @Test
    fun `test start listens to send queue updates`() = runTest {
        val mockedTransaction = mockk<NoopAnalyticsTransaction>(relaxed = true)
        val startTransactionRecorder = lambdaRecorder { _: String, _: String?, _: String? -> mockedTransaction }
        val room = FakeJoinedRoom()
        val analyticsService = FakeAnalyticsService(startTransactionLambda = startTransactionRecorder)

        val watcher = createDefaultAnalyticsSendMessageWatcher(room = room, analyticsService = analyticsService)

        // When we start listening, we don't trigger any analyticsService.startTransaction calls
        watcher.start()
        runCurrent()

        startTransactionRecorder.assertions().isNeverCalled()

        // When we receive a new local event, we start a new transaction for it
        room.givenSendQueueUpdate(SendQueueUpdate.NewLocalEvent(A_TRANSACTION_ID))
        runCurrent()

        startTransactionRecorder.assertions().isCalledOnce()

        // And we receive an 'event sent' update with the event's id, we finish the transaction
        room.givenSendQueueUpdate(SendQueueUpdate.SentEvent(A_TRANSACTION_ID, AN_EVENT_ID))
        runCurrent()

        verify { mockedTransaction.finish() }

        // We also stop the watcher for cleanup
        watcher.stop()
    }

    private fun TestScope.createDefaultAnalyticsSendMessageWatcher(
        room: FakeJoinedRoom = FakeJoinedRoom(),
        analyticsService: FakeAnalyticsService = FakeAnalyticsService(),
    ) = DefaultAnalyticsSendMessageWatcher(
        room = room,
        analyticsService = analyticsService,
        coroutineScope = backgroundScope,
    )
}
