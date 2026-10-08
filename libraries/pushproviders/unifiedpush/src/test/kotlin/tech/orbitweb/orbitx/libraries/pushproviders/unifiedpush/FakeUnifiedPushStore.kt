/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeUnifiedPushStore(
    private val getEndpointResult: (String) -> String? = { lambdaError() },
    private val storeUpEndpointResult: (String, String?) -> Unit = { _, _ -> lambdaError() },
    private val getPushGatewayResult: (String) -> String? = { lambdaError() },
    private val storePushGatewayResult: (String, String?) -> Unit = { _, _ -> lambdaError() },
    private val getDistributorValueResult: (UserId) -> String? = { lambdaError() },
    private val setDistributorValueResult: (UserId, String) -> Unit = { _, _ -> lambdaError() },
) : UnifiedPushStore {
    override fun getEndpoint(clientSecret: String): String? {
        return getEndpointResult(clientSecret)
    }

    override fun storeUpEndpoint(clientSecret: String, endpoint: String?) {
        storeUpEndpointResult(clientSecret, endpoint)
    }

    override fun getPushGateway(clientSecret: String): String? {
        return getPushGatewayResult(clientSecret)
    }

    override fun storePushGateway(clientSecret: String, gateway: String?) {
        storePushGatewayResult(clientSecret, gateway)
    }

    override fun getDistributorValue(userId: UserId): String? {
        return getDistributorValueResult(userId)
    }

    override fun setDistributorValue(userId: UserId, value: String) {
        setDistributorValueResult(userId, value)
    }
}
