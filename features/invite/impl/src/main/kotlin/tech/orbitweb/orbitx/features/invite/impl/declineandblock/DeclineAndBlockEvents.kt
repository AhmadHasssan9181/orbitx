/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl.declineandblock

sealed interface DeclineAndBlockEvents {
    data class UpdateReportReason(val reason: String) : DeclineAndBlockEvents
    data object ToggleReportRoom : DeclineAndBlockEvents
    data object ToggleBlockUser : DeclineAndBlockEvents
    data object Decline : DeclineAndBlockEvents
    data object ClearDeclineAction : DeclineAndBlockEvents
}
