/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.oidc

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.R
import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthRedirectUrlProvider
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

@ContributesBinding(AppScope::class)
class DefaultOAuthRedirectUrlProvider(
    private val stringProvider: StringProvider,
) : OAuthRedirectUrlProvider {
    override fun provide() = buildString {
        append(stringProvider.getString(R.string.login_redirect_scheme))
        append(":/")
    }
}
