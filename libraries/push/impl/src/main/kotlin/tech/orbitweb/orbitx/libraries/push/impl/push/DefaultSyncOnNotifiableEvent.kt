/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.push

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlagService
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlags
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClientProvider
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.push.impl.db.PushRequest
import tech.orbitweb.orbitx.services.appnavstate.api.AppForegroundStateService
import timber.log.Timber
import kotlin.time.Duration.Companion.seconds

@ContributesBinding(AppScope::class)
class DefaultSyncOnNotifiableEvent(
    private val matrixClientProvider: MatrixClientProvider,
    private val featureFlagService: FeatureFlagService,
    private val appForegroundStateService: AppForegroundStateService,
    private val dispatchers: CoroutineDispatchers,
) : SyncOnNotifiableEvent {
    override suspend operator fun invoke(requests: List<PushRequest>) = withContext(dispatchers.io) {
        if (!featureFlagService.isFeatureEnabled(FeatureFlags.SyncOnPush)) {
            return@withContext
        }

        try {
            val eventsBySession = requests.groupBy { it.sessionId }

            appForegroundStateService.updateIsSyncingNotificationEvent(true)
            Timber.d("Starting opportunistic room list sync | In foreground: ${appForegroundStateService.isInForeground.value}")

            for ((sessionId, events) in eventsBySession) {
                val client = matrixClientProvider.getOrRestore(SessionId(sessionId)).getOrNull() ?: continue
                val roomIds = events.map { RoomId(it.roomId) }.distinct()

                client.roomListService.subscribeToVisibleRooms(roomIds)

                if (!appForegroundStateService.isInForeground.value) {
                    // Give the sync some time to complete in background
                    delay(10.seconds)
                }
            }
        } finally {
            Timber.d("Finished opportunistic room list sync")
            appForegroundStateService.updateIsSyncingNotificationEvent(false)
        }
    }
}
