/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.auth

import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthRedirectUrlProvider

const val FAKE_REDIRECT_URL = "tech.orbitweb.orbitx:/"

class FakeOAuthRedirectUrlProvider(
    private val provideResult: String = FAKE_REDIRECT_URL,
) : OAuthRedirectUrlProvider {
    override fun provide() = provideResult
}
