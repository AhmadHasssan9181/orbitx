/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.impl.FakeClientBuilderProvider
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiClient
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiClientBuilder
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiHomeserverLoginDetails

class RustHomeserverLoginCompatibilityCheckerTest {
    @Test
    fun `check - is valid if it supports OAuth login`() = runTest {
        val sut = createChecker { FakeFfiHomeserverLoginDetails(supportsOAuthLogin = true) }
        assertThat(sut.check("https://matrix.host.org").getOrNull()).isTrue()
    }

    @Test
    fun `check - is valid if it supports password login`() = runTest {
        val sut = createChecker { FakeFfiHomeserverLoginDetails(supportsPasswordLogin = true) }
        assertThat(sut.check("https://matrix.host.org").getOrNull()).isTrue()
    }

    @Test
    fun `check - is not valid if it only supports SSO login`() = runTest {
        val sut = createChecker { FakeFfiHomeserverLoginDetails(supportsSsoLogin = true) }
        assertThat(sut.check("https://matrix.host.org").getOrNull()).isFalse()
    }

    @Test
    fun `check - is not valid if fetching the data fails`() = runTest {
        val sut = createChecker { error("Unexpected error!") }
        assertThat(sut.check("https://matrix.host.org").isFailure).isTrue()
    }

    private fun createChecker(
        result: () -> FakeFfiHomeserverLoginDetails,
    ) = RustHomeServerLoginCompatibilityChecker(
        clientBuilderProvider = FakeClientBuilderProvider {
            FakeFfiClientBuilder {
                FakeFfiClient(homeserverLoginDetailsResult = result)
            }
        },
    )
}
