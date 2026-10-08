/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClientProvider
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore

/**
 * Ensure we clear the well-known cached config, since it could be invalid due to an SDK issue.
 */
@ContributesIntoSet(AppScope::class)
class AppMigration09(
    private val sessionStore: SessionStore,
    private val matrixClientProvider: MatrixClientProvider,
) : AppMigration {
    override val order: Int = 9

    override suspend fun migrate(isFreshInstall: Boolean) {
        if (isFreshInstall) return

        val sessions = sessionStore.getAllSessions()

        for (session in sessions) {
            val client = matrixClientProvider.getOrRestore(SessionId(session.userId)).getOrNull() ?: continue
            client.resetWellKnownConfig()
        }
    }
}
