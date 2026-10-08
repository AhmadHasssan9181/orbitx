/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeUnifiedPushGatewayResolver(
    private val getGatewayResult: (String) -> UnifiedPushGatewayResolverResult = { lambdaError() },
) : UnifiedPushGatewayResolver {
    override suspend fun getGateway(endpoint: String): UnifiedPushGatewayResolverResult {
        return getGatewayResult(endpoint)
    }
}
