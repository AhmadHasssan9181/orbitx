/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthConfig
import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthRedirectUrlProvider
import org.matrix.rustcomponents.sdk.OAuthConfiguration

@Inject
class OAuthConfigurationProvider(
    private val buildMeta: BuildMeta,
    private val oAuthRedirectUrlProvider: OAuthRedirectUrlProvider,
) {
    fun get(): OAuthConfiguration = OAuthConfiguration(
        clientName = buildMeta.applicationName,
        redirectUri = oAuthRedirectUrlProvider.provide(),
        clientUri = OAuthConfig.CLIENT_URI,
        logoUri = OAuthConfig.LOGO_URI,
        tosUri = OAuthConfig.TOS_URI,
        policyUri = OAuthConfig.POLICY_URI,
        staticRegistrations = OAuthConfig.STATIC_REGISTRATIONS,
    )
}
