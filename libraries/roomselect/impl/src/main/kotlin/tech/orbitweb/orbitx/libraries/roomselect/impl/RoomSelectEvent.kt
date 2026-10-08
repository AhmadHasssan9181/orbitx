/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.roomselect.impl

import tech.orbitweb.orbitx.libraries.matrix.ui.model.SelectRoomInfo

sealed interface RoomSelectEvent {
    data class ToggleSelectedRoom(val room: SelectRoomInfo) : RoomSelectEvent
    data object ToggleSearchActive : RoomSelectEvent
    data class UpdateVisibleRange(val range: IntRange) : RoomSelectEvent
}
