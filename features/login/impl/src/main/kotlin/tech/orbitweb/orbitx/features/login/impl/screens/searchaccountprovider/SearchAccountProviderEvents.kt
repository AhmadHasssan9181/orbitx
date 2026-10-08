/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.searchaccountprovider

sealed interface SearchAccountProviderEvents {
    /**
     * The user has typed something, expect to get a list of matching account provider results
     * in the state.
     */
    data class UserInput(val input: String) : SearchAccountProviderEvents
}
