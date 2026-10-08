/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import kotlinx.coroutines.coroutineScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.preferences.api.store.SessionPreferencesStoreFactory
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore

/**
 * This migration sets the skip session verification preference to true for all existing sessions.
 * This way we don't force existing users to verify their session again.
 */
@ContributesIntoSet(AppScope::class)
class AppMigration02(
    private val sessionStore: SessionStore,
    private val sessionPreferenceStoreFactory: SessionPreferencesStoreFactory,
) : AppMigration {
    override val order: Int = 2

    override suspend fun migrate(isFreshInstall: Boolean) {
        coroutineScope {
            for (session in sessionStore.getAllSessions()) {
                val sessionId = SessionId(session.userId)
                val preferences = sessionPreferenceStoreFactory.get(sessionId, this)
                preferences.setSkipSessionVerification(true)
                // This session preference store must be ephemeral since it's not created with the right coroutine scope
                sessionPreferenceStoreFactory.remove(sessionId)
            }
        }
    }
}
