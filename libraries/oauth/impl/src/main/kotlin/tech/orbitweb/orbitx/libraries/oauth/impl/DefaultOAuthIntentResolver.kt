/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.oauth.impl

import android.content.Intent
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.oauth.api.OAuthAction
import tech.orbitweb.orbitx.libraries.oauth.api.OAuthIntentResolver

@ContributesBinding(AppScope::class)
class DefaultOAuthIntentResolver(
    private val oAuthUrlParser: OAuthUrlParser,
) : OAuthIntentResolver {
    override fun resolve(intent: Intent): OAuthAction? {
        return oAuthUrlParser.parse(intent.dataString.orEmpty())
    }
}
