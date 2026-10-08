/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.pushgateway

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.network.RetrofitFactory

interface PushGatewayApiFactory {
    fun create(baseUrl: String): PushGatewayAPI
}

@ContributesBinding(AppScope::class)
class DefaultPushGatewayApiFactory(
    private val retrofitFactory: RetrofitFactory,
) : PushGatewayApiFactory {
    override fun create(baseUrl: String): PushGatewayAPI {
        return retrofitFactory.create(baseUrl)
            .create(PushGatewayAPI::class.java)
    }
}
