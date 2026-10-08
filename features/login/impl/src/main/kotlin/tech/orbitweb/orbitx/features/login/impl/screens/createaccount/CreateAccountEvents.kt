/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.createaccount

sealed interface CreateAccountEvents {
    data class SetPageProgress(val progress: Int) : CreateAccountEvents
    data class OnMessageReceived(val message: String) : CreateAccountEvents
}
