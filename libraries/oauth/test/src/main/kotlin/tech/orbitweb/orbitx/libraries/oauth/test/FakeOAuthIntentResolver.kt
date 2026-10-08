/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.oauth.test

import android.content.Intent
import tech.orbitweb.orbitx.libraries.oauth.api.OAuthAction
import tech.orbitweb.orbitx.libraries.oauth.api.OAuthIntentResolver
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeOAuthIntentResolver(
    private val resolveResult: (Intent) -> OAuthAction? = { lambdaError() }
) : OAuthIntentResolver {
    override fun resolve(intent: Intent): OAuthAction? {
        return resolveResult(intent)
    }
}
