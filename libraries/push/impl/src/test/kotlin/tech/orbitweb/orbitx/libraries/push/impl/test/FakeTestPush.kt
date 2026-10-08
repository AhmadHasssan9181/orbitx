/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.test

import tech.orbitweb.orbitx.libraries.pushproviders.api.Config
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeTestPush(
    private val executeResult: (Config) -> Unit = { lambdaError() }
) : TestPush {
    override suspend fun execute(config: Config) {
        executeResult(config)
    }
}
