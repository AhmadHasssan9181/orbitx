/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.loginpassword

sealed interface LoginPasswordEvents {
    data class SetLogin(val login: String) : LoginPasswordEvents
    data class SetPassword(val password: String) : LoginPasswordEvents
    data object Submit : LoginPasswordEvents
    data object ClearError : LoginPasswordEvents
}
