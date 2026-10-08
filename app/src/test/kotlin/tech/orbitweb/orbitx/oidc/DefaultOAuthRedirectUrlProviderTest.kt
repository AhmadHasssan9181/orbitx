/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.oidc

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.R
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider

class DefaultOAuthRedirectUrlProviderTest {
    @Test
    fun `test provide`() {
        val stringProvider = FakeStringProvider(
            defaultResult = "str"
        )
        val sut = DefaultOAuthRedirectUrlProvider(
            stringProvider = stringProvider,
        )
        val result = sut.provide()
        assertThat(result).isEqualTo("str:/")
        assertThat(stringProvider.lastResIdParam).isEqualTo(R.string.login_redirect_scheme)
    }
}
