/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext

/**
 * Remove notifications.bin file, used to store notification data locally.
 */
@ContributesIntoSet(AppScope::class)
class AppMigration04(
    @ApplicationContext private val context: Context,
) : AppMigration {
    companion object {
        internal const val NOTIFICATION_FILE_NAME = "notifications.bin"
    }

    override val order: Int = 4

    override suspend fun migrate(isFreshInstall: Boolean) {
        runCatchingExceptions { context.getDatabasePath(NOTIFICATION_FILE_NAME).delete() }
    }
}
