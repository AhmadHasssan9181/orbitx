/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.auth

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.test.auth.aMatrixHomeServerDetails

class MatrixHomeServerDetailsTest {
    @Test
    fun `if homeserver supports oidc, then it is supported`() {
        val sut = aMatrixHomeServerDetails(
            supportsOAuthLogin = true,
            supportsPasswordLogin = false,
        )
        assertThat(sut.isSupported).isTrue()
    }

    @Test
    fun `if homeserver supports password, then it is supported`() {
        val sut = aMatrixHomeServerDetails(
            supportsOAuthLogin = false,
            supportsPasswordLogin = true,
        )
        assertThat(sut.isSupported).isTrue()
    }

    @Test
    fun `if homeserver supports both, then it is supported`() {
        val sut = aMatrixHomeServerDetails(
            supportsOAuthLogin = true,
            supportsPasswordLogin = true,
        )
        assertThat(sut.isSupported).isTrue()
    }

    @Test
    fun `if homeserver supports none, then it is not supported`() {
        val sut = aMatrixHomeServerDetails(
            supportsOAuthLogin = false,
            supportsPasswordLogin = false,
        )
        assertThat(sut.isSupported).isFalse()
    }
}
