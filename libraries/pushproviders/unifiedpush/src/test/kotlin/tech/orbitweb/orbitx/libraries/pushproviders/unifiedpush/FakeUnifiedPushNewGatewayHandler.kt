/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeUnifiedPushNewGatewayHandler(
    private val handleResult: (String, String, String) -> Result<Unit> = { _, _, _ -> lambdaError() },
) : UnifiedPushNewGatewayHandler {
    override suspend fun handle(endpoint: String, pushGateway: String, clientSecret: String): Result<Unit> {
        return handleResult(endpoint, pushGateway, clientSecret)
    }
}
