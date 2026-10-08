/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.logout.test

import tech.orbitweb.orbitx.features.logout.api.LogoutUseCase
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeLogoutUseCase(
    var logoutLambda: (Boolean) -> Unit = { lambdaError() }
) : LogoutUseCase {
    override suspend fun logoutAll(ignoreSdkError: Boolean) = simulateLongTask {
        logoutLambda(ignoreSdkError)
    }
}
