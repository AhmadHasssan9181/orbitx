/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.featureflag.impl

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlags

class DefaultFeaturesProviderTest {
    @Test
    fun `provide should return all features`() {
        val provider = DefaultFeaturesProvider()
        val features = provider.provide()
        assertThat(features.size).isEqualTo(FeatureFlags.entries.size)
        FeatureFlags.entries.forEach {
            assertThat(features.contains(it)).isTrue()
        }
    }
}
