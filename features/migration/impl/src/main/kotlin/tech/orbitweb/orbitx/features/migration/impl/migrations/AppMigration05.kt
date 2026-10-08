/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import tech.orbitweb.orbitx.libraries.di.BaseDirectory
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore
import java.io.File

@ContributesIntoSet(AppScope::class)
class AppMigration05(
    private val sessionStore: SessionStore,
    @BaseDirectory private val baseDirectory: File,
) : AppMigration {
    override val order: Int = 5

    override suspend fun migrate(isFreshInstall: Boolean) {
        val allSessions = sessionStore.getAllSessions()
        for (session in allSessions) {
            if (session.sessionPath.isEmpty()) {
                val sessionPath = File(baseDirectory, session.userId.replace(':', '_')).absolutePath
                sessionStore.updateData(session.copy(sessionPath = sessionPath))
            }
        }
    }
}
