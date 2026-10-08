/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.preferences.api.store.PreferenceDataStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val applicationMigrationVersion = intPreferencesKey("applicationMigrationVersion")

@ContributesBinding(AppScope::class)
class DefaultMigrationStore(
    preferenceDataStoreFactory: PreferenceDataStoreFactory,
) : MigrationStore {
    private val store = preferenceDataStoreFactory.create("elementx_migration")

    override suspend fun setApplicationMigrationVersion(version: Int) {
        store.edit { prefs ->
            prefs[applicationMigrationVersion] = version
        }
    }

    override fun applicationMigrationVersion(): Flow<Int> {
        return store.data.map { prefs ->
            prefs[applicationMigrationVersion] ?: -1
        }
    }
}
