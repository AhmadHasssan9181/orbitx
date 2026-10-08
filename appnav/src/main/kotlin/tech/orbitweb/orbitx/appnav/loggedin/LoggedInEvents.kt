/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.loggedin

sealed interface LoggedInEvents {
    data class CloseErrorDialog(val doNotShowAgain: Boolean) : LoggedInEvents
    data object CheckSlidingSyncProxyAvailability : LoggedInEvents
    data object LogoutAndMigrateToNativeSlidingSync : LoggedInEvents
}
