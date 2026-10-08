/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.test

import tech.orbitweb.orbitx.libraries.push.impl.pushgateway.PushGatewayNotifyRequest
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakePushGatewayNotifyRequest(
    private val executeResult: (PushGatewayNotifyRequest.Params) -> Unit = { lambdaError() }
) : PushGatewayNotifyRequest {
    override suspend fun execute(params: PushGatewayNotifyRequest.Params) {
        executeResult(params)
    }
}
