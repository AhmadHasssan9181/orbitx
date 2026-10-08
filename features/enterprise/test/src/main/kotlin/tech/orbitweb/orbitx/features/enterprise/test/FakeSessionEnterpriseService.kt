/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.enterprise.test

import tech.orbitweb.orbitx.features.enterprise.api.SessionEnterpriseService
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeSessionEnterpriseService(
    private val isElementCallAvailableResult: () -> Boolean = { lambdaError() },
    private val tweakMasUrlResult: (String) -> String = { lambdaError() },
) : SessionEnterpriseService {
    override suspend fun init() {
    }

    override suspend fun tweakMasUrl(url: String): String = simulateLongTask {
        tweakMasUrlResult(url)
    }

    override suspend fun isElementCallAvailable(): Boolean = simulateLongTask {
        isElementCallAvailableResult()
    }
}
