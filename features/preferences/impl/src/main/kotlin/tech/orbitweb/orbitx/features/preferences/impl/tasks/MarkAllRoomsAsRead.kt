/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.tasks

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.push.api.notifications.NotificationCleaner
import kotlinx.coroutines.withContext

interface MarkAllRoomsAsRead {
    suspend operator fun invoke(): Result<Unit>
}

@ContributesBinding(SessionScope::class)
class DefaultMarkAllRoomsAsRead(
    private val client: MatrixClient,
    private val notificationCleaner: NotificationCleaner,
    private val coroutineDispatchers: CoroutineDispatchers,
) : MarkAllRoomsAsRead {
    override suspend fun invoke(): Result<Unit> = withContext(coroutineDispatchers.io) {
        client.markAllRoomsAsRead()
            .onSuccess {
                notificationCleaner.clearAllMessagesEvents(client.sessionId)
            }
    }
}
