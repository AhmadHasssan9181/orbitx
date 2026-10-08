/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import kotlinx.coroutines.withContext
import timber.log.Timber

interface MarkAsFullyRead {
    suspend operator fun invoke(roomId: RoomId, eventId: EventId): Result<Unit>
}

@ContributesBinding(SessionScope::class)
class DefaultMarkAsFullyRead(
    private val matrixClient: MatrixClient,
    private val coroutineDispatchers: CoroutineDispatchers,
) : MarkAsFullyRead {
    override suspend fun invoke(roomId: RoomId, eventId: EventId): Result<Unit> = withContext(coroutineDispatchers.io) {
        matrixClient.markRoomAsFullyRead(roomId, eventId).onFailure {
            Timber.e(it, "Failed to mark room $roomId as fully read for event $eventId")
        }
    }
}
