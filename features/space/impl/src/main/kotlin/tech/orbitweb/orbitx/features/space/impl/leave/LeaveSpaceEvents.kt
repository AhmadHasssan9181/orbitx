/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl.leave

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

sealed interface LeaveSpaceEvents {
    data object Retry : LeaveSpaceEvents
    data object SelectAllRooms : LeaveSpaceEvents
    data object DeselectAllRooms : LeaveSpaceEvents
    data class ToggleRoomSelection(val roomId: RoomId) : LeaveSpaceEvents
    data object LeaveSpace : LeaveSpaceEvents
    data object CloseError : LeaveSpaceEvents
}
