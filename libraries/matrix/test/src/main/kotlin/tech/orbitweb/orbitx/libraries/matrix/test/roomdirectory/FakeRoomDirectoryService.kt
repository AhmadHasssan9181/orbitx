/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.roomdirectory

import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomDirectoryList
import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomDirectoryService
import kotlinx.coroutines.CoroutineScope

class FakeRoomDirectoryService(
    private val createRoomDirectoryListFactory: (CoroutineScope) -> RoomDirectoryList = { throw AssertionError("Configure a proper factory.") }
) : RoomDirectoryService {
    override fun createRoomDirectoryList(scope: CoroutineScope) = createRoomDirectoryListFactory(scope)
}
