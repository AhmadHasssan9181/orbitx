/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.tasks

import android.content.Context
import coil3.SingletonImageLoader
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import tech.orbitweb.orbitx.features.invite.api.SeenInvitesStore
import tech.orbitweb.orbitx.features.preferences.impl.DefaultCacheService
import tech.orbitweb.orbitx.libraries.cachestore.api.CacheStore
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.push.api.PushService
import tech.orbitweb.orbitx.services.appnavstate.api.ActiveRoomsHolder

interface ClearCacheUseCase {
    suspend operator fun invoke()
}

@ContributesBinding(SessionScope::class)
class DefaultClearCacheUseCase(
    @ApplicationContext private val context: Context,
    private val matrixClient: MatrixClient,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val defaultCacheService: DefaultCacheService,
    private val okHttpClient: () -> OkHttpClient,
    private val pushService: PushService,
    private val seenInvitesStore: SeenInvitesStore,
    private val activeRoomsHolder: ActiveRoomsHolder,
    private val cacheStore: CacheStore,
) : ClearCacheUseCase {
    override suspend fun invoke() = withContext(coroutineDispatchers.io) {
        // Clear cache store
        cacheStore.deleteAll()
        // Active rooms should be disposed of before clearing the cache
        activeRoomsHolder.clear(matrixClient.sessionId)
        // Clear Matrix cache
        matrixClient.clearCache()
        // Clear Coil cache
        SingletonImageLoader.get(context).let {
            it.diskCache?.clear()
            it.memoryCache?.clear()
        }
        // Clear OkHttp cache
        okHttpClient().cache?.delete()
        // Clear app cache
        context.cacheDir?.listFiles {
            // But keep the logs
            it.name != "logs"
        }?.onEach {
            it.deleteRecursively()
        }
        // Clear some settings
        seenInvitesStore.clear()
        // Ensure any error will be displayed again
        pushService.setIgnoreRegistrationError(matrixClient.sessionId, false)
        pushService.resetBatteryOptimizationState()
        // Ensure the app is restarted
        defaultCacheService.onClearedCache(matrixClient.sessionId)
    }
}
