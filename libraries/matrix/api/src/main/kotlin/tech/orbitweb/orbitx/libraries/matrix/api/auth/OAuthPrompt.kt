/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.auth

sealed interface OAuthPrompt {
    /**
     * The Authorization Server should prompt the End-User for
     * reauthentication.
     */
    data object Login : OAuthPrompt

    /**
     * The Authorization Server should prompt the End-User to create a user
     * account.
     *
     * Defined in [Initiating User Registration via OpenID Connect](https://openid.net/specs/openid-connect-prompt-create-1_0.html).
     */
    data object Create : OAuthPrompt

    /**
     * An unknown value.
     */
    data class Unknown(val value: String) : OAuthPrompt
}
