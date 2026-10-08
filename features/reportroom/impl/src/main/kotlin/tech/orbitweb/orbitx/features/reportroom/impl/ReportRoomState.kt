/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.reportroom.impl

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction

data class ReportRoomState(
    val reason: String,
    val leaveRoom: Boolean,
    val reportAction: AsyncAction<Unit>,
    val eventSink: (ReportRoomEvents) -> Unit
) {
    val canReport: Boolean = reason.isNotBlank()
}
