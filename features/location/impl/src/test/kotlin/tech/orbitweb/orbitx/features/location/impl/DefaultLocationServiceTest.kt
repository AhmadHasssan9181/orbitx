/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.location.api.BuildConfig
import org.junit.Test

class DefaultLocationServiceTest {
    @Test
    fun `isServiceAvailable should return value depending on BuildConfig MAPTILER_API_KEY`() {
        val locationService = DefaultLocationService()
        assertThat(locationService.isServiceAvailable()).isEqualTo(
            BuildConfig.MAPTILER_API_KEY.isNotEmpty()
        )
    }
}
