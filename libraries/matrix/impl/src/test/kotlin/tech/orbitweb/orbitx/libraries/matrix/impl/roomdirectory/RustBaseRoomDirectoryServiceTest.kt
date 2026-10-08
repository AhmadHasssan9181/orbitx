/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.roomdirectory

import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiClient
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RustBaseRoomDirectoryServiceTest {
    @Test
    fun test() = runTest {
        val client = FakeFfiClient()
        val sut = RustRoomDirectoryService(
            client = client,
            sessionDispatcher = StandardTestDispatcher(testScheduler),
        )
        sut.createRoomDirectoryList(backgroundScope)
    }
}
