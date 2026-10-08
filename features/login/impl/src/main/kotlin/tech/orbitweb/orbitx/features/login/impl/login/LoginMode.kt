/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.login

import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthDetails

sealed interface LoginMode {
    data object PasswordLogin : LoginMode
    data class OAuth(val oAuthDetails: OAuthDetails) : LoginMode
    data class AccountCreation(val url: String) : LoginMode
}
