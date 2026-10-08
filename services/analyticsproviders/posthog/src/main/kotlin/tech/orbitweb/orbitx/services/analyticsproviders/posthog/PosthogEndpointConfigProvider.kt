/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analyticsproviders.posthog

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService

@Inject
class PosthogEndpointConfigProvider(
    private val enterpriseService: EnterpriseService,
) {
    fun provide(): PosthogEndpointConfig? {
        return if (enterpriseService.isEnterpriseBuild) {
            PosthogEndpointConfig(
                host = BuildConfig.POSTHOG_HOST,
                apiKey = BuildConfig.POSTHOG_APIKEY,
            ).takeIf {
                it.isValid
            }
        } else {
            // Disabled external telemetry by default
            null
        }
    }
}
