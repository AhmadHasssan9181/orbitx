/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.reportroom.impl

sealed interface ReportRoomEvents {
    data class UpdateReason(val reason: String) : ReportRoomEvents
    data object ToggleLeaveRoom : ReportRoomEvents
    data object Report : ReportRoomEvents
    data object ClearReportAction : ReportRoomEvents
}
