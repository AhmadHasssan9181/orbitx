/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.roomselect.impl

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.SearchBarResultState
import tech.orbitweb.orbitx.libraries.matrix.ui.model.SelectRoomInfo
import tech.orbitweb.orbitx.libraries.roomselect.api.RoomSelectMode

data class RoomSelectState(
    val mode: RoomSelectMode,
    val maxNumberOfRooms: Int,
    val resultState: SearchBarResultState<ImmutableList<SelectRoomInfo>>,
    val searchQuery: TextFieldState,
    val isSearchActive: Boolean,
    val selectedRooms: ImmutableList<SelectRoomInfo>,
    val eventSink: (RoomSelectEvent) -> Unit,
) {
    val canSelectMoreRooms = selectedRooms.size < maxNumberOfRooms
}
