/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import tech.orbitweb.orbitx.features.rageshake.api.logs.LogFilesRemover

/**
 * Remove existing logs from the device to remove any leaks of sensitive data.
 */
@ContributesIntoSet(AppScope::class)
class AppMigration01(
    private val logFilesRemover: LogFilesRemover,
) : AppMigration {
    override val order: Int = 1

    override suspend fun migrate(isFreshInstall: Boolean) {
        logFilesRemover.perform()
    }
}
