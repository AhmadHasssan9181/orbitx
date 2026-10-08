/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.test.auth.FAKE_REDIRECT_URL
import tech.orbitweb.orbitx.libraries.matrix.test.auth.FakeOAuthRedirectUrlProvider
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import org.junit.Test

class OAuthConfigurationProviderTest {
    @Test
    fun get() {
        val result = OAuthConfigurationProvider(
            buildMeta = aBuildMeta(
                applicationName = "myName",
            ),
            oAuthRedirectUrlProvider = FakeOAuthRedirectUrlProvider(),
        ).get()
        assertThat(result.clientName).isEqualTo("myName")
        assertThat(result.redirectUri).isEqualTo(FAKE_REDIRECT_URL)
    }
}
