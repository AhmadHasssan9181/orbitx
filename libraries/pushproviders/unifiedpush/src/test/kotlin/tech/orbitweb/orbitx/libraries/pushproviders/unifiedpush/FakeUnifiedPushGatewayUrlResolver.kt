/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeUnifiedPushGatewayUrlResolver(
    private val resolveResult: (UnifiedPushGatewayResolverResult, String) -> String = { _, _ -> lambdaError() },
) : UnifiedPushGatewayUrlResolver {
    override fun resolve(gatewayResult: UnifiedPushGatewayResolverResult, instance: String): String {
        return resolveResult(gatewayResult, instance)
    }
}
