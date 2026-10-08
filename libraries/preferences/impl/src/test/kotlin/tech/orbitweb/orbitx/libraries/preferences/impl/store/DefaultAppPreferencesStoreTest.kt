/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.preferences.impl.store

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.core.meta.BuildType
import tech.orbitweb.orbitx.libraries.preferences.test.FakePreferenceDataStoreFactory

class DefaultAppPreferencesStoreTest {
    private val buildMeta = BuildMeta(
        buildType = BuildType.DEBUG,
        isDebuggable = true,
        applicationName = "ORBIT Chat",
        productionApplicationName = "Element",
        desktopApplicationName = "Element Desktop",
        applicationId = "tech.orbitweb.orbitx",
        isEnterpriseBuild = false,
        lowPrivacyLoggingEnabled = false,
        versionName = "1.0.0",
        versionCode = 1,
        gitRevision = "test",
        gitBranchName = "test",
        flavorDescription = "test",
        flavorShortDescription = "test",
    )

    @Test
    fun `live location minimum distance defaults to 10`() = runTest {
        val store = DefaultAppPreferencesStore(
            buildMeta = buildMeta,
            preferenceDataStoreFactory = FakePreferenceDataStoreFactory(),
        )

        assertThat(store.getLiveLocationMinimumDistanceInMetersUpdateFlow().first()).isEqualTo(10)
    }

    @Test
    fun `live location minimum distance persists updates`() = runTest {
        val store = DefaultAppPreferencesStore(
            buildMeta = buildMeta,
            preferenceDataStoreFactory = FakePreferenceDataStoreFactory(),
        )

        store.setLiveLocationMinimumDistanceInMetersUpdate(25)

        assertThat(store.getLiveLocationMinimumDistanceInMetersUpdateFlow().first()).isEqualTo(25)
    }
}
