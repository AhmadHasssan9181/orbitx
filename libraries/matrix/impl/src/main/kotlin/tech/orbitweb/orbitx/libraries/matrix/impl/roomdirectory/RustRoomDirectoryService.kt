/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.roomdirectory

import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomDirectoryList
import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomDirectoryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.matrix.rustcomponents.sdk.Client

class RustRoomDirectoryService(
    private val client: Client,
    private val sessionDispatcher: CoroutineDispatcher,
) : RoomDirectoryService {
    override fun createRoomDirectoryList(scope: CoroutineScope): RoomDirectoryList {
        return RustRoomDirectoryList(client.roomDirectorySearch(), scope, sessionDispatcher)
    }
}
