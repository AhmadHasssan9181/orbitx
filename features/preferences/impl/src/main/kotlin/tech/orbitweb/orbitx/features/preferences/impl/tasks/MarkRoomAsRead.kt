/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.tasks

import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.ReceiptType
import tech.orbitweb.orbitx.libraries.preferences.api.store.SessionPreferencesStore
import tech.orbitweb.orbitx.libraries.push.api.notifications.NotificationCleaner

interface MarkRoomAsRead {
    suspend operator fun invoke(roomId: RoomId): Result<Unit>
}

@ContributesBinding(SessionScope::class)
class DefaultMarkRoomAsRead(
    private val client: MatrixClient,
    private val notificationCleaner: NotificationCleaner,
    private val sessionPreferencesStore: SessionPreferencesStore,
    private val coroutineDispatchers: CoroutineDispatchers,
) : MarkRoomAsRead {
    override suspend fun invoke(roomId: RoomId): Result<Unit> = withContext(coroutineDispatchers.io) {
        notificationCleaner.clearMessagesForRoom(client.sessionId, roomId)
        val room = client.getRoom(roomId) ?: return@withContext Result.failure(IllegalStateException("Room not found"))
        room.use {
            it.setUnreadFlag(isUnread = false)
            val receiptType = if (sessionPreferencesStore.isSendPublicReadReceiptsEnabled().first()) {
                ReceiptType.READ
            } else {
                ReceiptType.READ_PRIVATE
            }
            it.markAsRead(receiptType)
        }
    }
}
