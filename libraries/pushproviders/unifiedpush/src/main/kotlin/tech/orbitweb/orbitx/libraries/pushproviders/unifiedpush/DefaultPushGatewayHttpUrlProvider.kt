/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService

interface DefaultPushGatewayHttpUrlProvider {
    fun provide(): String
}

@ContributesBinding(AppScope::class)
class DefaultDefaultPushGatewayHttpUrlProvider(
    private val enterpriseService: EnterpriseService,
) : DefaultPushGatewayHttpUrlProvider {
    override fun provide(): String {
        return enterpriseService.unifiedPushDefaultPushGateway() ?: UnifiedPushConfig.DEFAULT_PUSH_GATEWAY_HTTP_URL
    }
}
