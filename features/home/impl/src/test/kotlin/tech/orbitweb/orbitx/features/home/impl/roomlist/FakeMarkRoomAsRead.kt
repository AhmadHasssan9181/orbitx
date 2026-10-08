/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.roomlist

import tech.orbitweb.orbitx.features.preferences.impl.tasks.MarkRoomAsRead
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

class FakeMarkRoomAsRead(
    private val invokeLambda: suspend (RoomId) -> Result<Unit> = { Result.success(Unit) },
) : MarkRoomAsRead {
    val invokedRoomIds = mutableListOf<RoomId>()

    override suspend fun invoke(roomId: RoomId): Result<Unit> {
        invokedRoomIds.add(roomId)
        return invokeLambda(roomId)
    }
}
