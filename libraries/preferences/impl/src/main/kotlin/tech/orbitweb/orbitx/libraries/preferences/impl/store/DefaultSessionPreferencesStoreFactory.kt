/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.preferences.impl.store

import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.preferences.api.store.SessionPreferencesStore
import tech.orbitweb.orbitx.libraries.preferences.api.store.SessionPreferencesStoreFactory
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionListener
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionObserver
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.ConcurrentHashMap

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultSessionPreferencesStoreFactory(
    @ApplicationContext private val context: Context,
    sessionObserver: SessionObserver,
) : SessionPreferencesStoreFactory {
    private val cache = ConcurrentHashMap<SessionId, DefaultSessionPreferencesStore>()

    init {
        sessionObserver.addListener(object : SessionListener {
            override suspend fun onSessionDeleted(userId: String, wasLastSession: Boolean) {
                val sessionPreferences = cache.remove(SessionId(userId))
                sessionPreferences?.clear()
            }
        })
    }

    override fun get(sessionId: SessionId, sessionCoroutineScope: CoroutineScope): SessionPreferencesStore = cache.getOrPut(sessionId) {
        DefaultSessionPreferencesStore(context, sessionId, sessionCoroutineScope)
    }

    override fun remove(sessionId: SessionId) {
        cache.remove(sessionId)
    }
}
