/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.roomlist

import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiRoomList
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiRoomListService
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import kotlin.coroutines.EmptyCoroutineContext

class RoomListFactoryTest {
    @Test
    fun `createRoomList should work`() = runTest {
        val sut = RoomListFactory(
            innerRoomListService = FakeFfiRoomListService(),
            analyticsService = FakeAnalyticsService(),
        )
        sut.createRoomList(
            pageSize = 10,
            coroutineContext = EmptyCoroutineContext,
            coroutineScope = backgroundScope,
        ) {
            FakeFfiRoomList()
        }
    }
}
