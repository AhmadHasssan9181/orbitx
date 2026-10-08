/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.root

import tech.orbitweb.orbitx.libraries.sessionstorage.api.LoggedInState

/**
 * [RootNavState] produced by [RootNavStateFlowFactory].
 */
data class RootNavState(
    /**
     * This value is incremented when a clear cache is done.
     * Can be useful to track to force ui state to re-render
     */
    val cacheIndex: Int,
    /**
     * LoggedInState.
     */
    val loggedInState: LoggedInState,
)
