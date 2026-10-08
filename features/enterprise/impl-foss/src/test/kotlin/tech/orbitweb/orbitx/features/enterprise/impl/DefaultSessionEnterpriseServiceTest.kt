/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.enterprise.impl

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultSessionEnterpriseServiceTest {
    @Test
    fun `isElementCallAvailable is always true`() = runTest {
        val service = DefaultSessionEnterpriseService()
        assertThat(service.isElementCallAvailable()).isTrue()
    }
}
