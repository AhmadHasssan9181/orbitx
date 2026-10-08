/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.auth.MatrixHomeServerDetails
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiHomeserverLoginDetails

class HomeserverDetailsKtTest {
    @Test
    fun `map should be correct`() {
        // Given
        val homeserverLoginDetails = FakeFfiHomeserverLoginDetails(
            url = "https://example.org",
            supportsPasswordLogin = true,
            supportsOAuthLogin = false
        )

        // When
        val result = homeserverLoginDetails.map()

        // Then
        assertThat(result).isEqualTo(
            MatrixHomeServerDetails(
                url = "https://example.org",
                supportsPasswordLogin = true,
                supportsOAuthLogin = false
            )
        )
    }
}
