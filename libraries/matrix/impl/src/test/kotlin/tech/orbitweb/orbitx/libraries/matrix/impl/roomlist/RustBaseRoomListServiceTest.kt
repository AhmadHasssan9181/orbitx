/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */
package tech.orbitweb.orbitx.libraries.matrix.impl.roomlist

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomListService
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiRoomListService
import tech.orbitweb.orbitx.libraries.matrix.impl.room.RoomSyncSubscriber
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.matrix.rustcomponents.sdk.RoomListServiceSyncIndicator
import org.matrix.rustcomponents.sdk.RoomListService as RustRoomListService

@OptIn(ExperimentalCoroutinesApi::class)
class RustBaseRoomListServiceTest {
    @Test
    fun `syncIndicator should emit the expected values`() = runTest {
        val roomListService = FakeFfiRoomListService()
        val sut = createRustRoomListService(
            roomListService = roomListService,
        )
        // Give time for mxCallback to setup
        runCurrent()
        sut.syncIndicator.test {
            assertThat(awaitItem()).isEqualTo(RoomListService.SyncIndicator.Hide)
            roomListService.emitRoomListServiceSyncIndicator(RoomListServiceSyncIndicator.SHOW)
            assertThat(awaitItem()).isEqualTo(RoomListService.SyncIndicator.Show)
            roomListService.emitRoomListServiceSyncIndicator(RoomListServiceSyncIndicator.HIDE)
            assertThat(awaitItem()).isEqualTo(RoomListService.SyncIndicator.Hide)
        }
    }
}

private fun TestScope.createRustRoomListService(
    roomListService: RustRoomListService = FakeFfiRoomListService(),
) = RustRoomListService(
    innerRoomListService = roomListService,
    sessionDispatcher = StandardTestDispatcher(testScheduler),
    roomListFactory = RoomListFactory(
        innerRoomListService = roomListService,
        analyticsService = FakeAnalyticsService(),
    ),
    roomSyncSubscriber = RoomSyncSubscriber(
        roomListService = roomListService,
        dispatchers = testCoroutineDispatchers(),
    ),
    sessionCoroutineScope = backgroundScope,
)
