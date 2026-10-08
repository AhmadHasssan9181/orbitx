/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.matrix.rustcomponents.sdk.RoomListService
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import timber.log.Timber

class RoomSyncSubscriber(
    private val roomListService: RoomListService,
    private val dispatchers: CoroutineDispatchers,
) {
    private val mutex = Mutex()

    suspend fun subscribe(roomId: RoomId) {
        mutex.withLock {
            withContext(dispatchers.io) {
                try {
                    Timber.d("Subscribing to room $roomId}")
                    roomListService.subscribeToRooms(listOf(roomId.value))
                } catch (exception: Exception) {
                    Timber.e(exception, "Failed to subscribe to room $roomId")
                }
            }
        }
    }

    suspend fun batchSubscribe(roomIds: List<RoomId>) = mutex.withLock {
        withContext(dispatchers.io) {
            try {
                Timber.d("Subscribing to rooms: $roomIds")
                roomListService.subscribeToRooms(roomIds.map { it.value })
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                Timber.e(exception, "Failed to subscribe to rooms: $roomIds")
            }
        }
    }
}
