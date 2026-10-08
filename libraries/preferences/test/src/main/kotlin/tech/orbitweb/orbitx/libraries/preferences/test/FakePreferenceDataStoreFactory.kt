/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.preferences.test

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import tech.orbitweb.orbitx.libraries.preferences.api.store.PreferenceDataStoreFactory
import java.io.File
import androidx.datastore.preferences.core.PreferenceDataStoreFactory as AndroidPreferenceDataStoreFactory

class FakePreferenceDataStoreFactory : PreferenceDataStoreFactory {
    override fun create(name: String): DataStore<Preferences> {
        return AndroidPreferenceDataStoreFactory.create { File.createTempFile("test", ".preferences_pb") }
    }
}
