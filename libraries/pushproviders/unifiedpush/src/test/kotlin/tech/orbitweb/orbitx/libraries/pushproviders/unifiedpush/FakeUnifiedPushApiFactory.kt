/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush.network.DiscoveryResponse
import tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush.network.UnifiedPushApi

class FakeUnifiedPushApiFactory(
    private val discoveryResponse: () -> DiscoveryResponse
) : UnifiedPushApiFactory {
    var baseUrlParameter: String? = null
        private set

    override fun create(baseUrl: String): UnifiedPushApi {
        baseUrlParameter = baseUrl
        return FakeUnifiedPushApi(discoveryResponse)
    }
}

class FakeUnifiedPushApi(
    private val discoveryResponse: () -> DiscoveryResponse
) : UnifiedPushApi {
    override suspend fun discover(): DiscoveryResponse {
        return discoveryResponse()
    }
}
