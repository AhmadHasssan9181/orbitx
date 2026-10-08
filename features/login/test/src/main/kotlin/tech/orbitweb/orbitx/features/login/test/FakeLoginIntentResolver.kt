/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.test

import tech.orbitweb.orbitx.features.login.api.LoginIntentResolver
import tech.orbitweb.orbitx.features.login.api.LoginParams
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeLoginIntentResolver(
    private val parseResult: (String) -> LoginParams? = { lambdaError() }
) : LoginIntentResolver {
    override fun parse(uriString: String): LoginParams? {
        return parseResult(uriString)
    }
}
