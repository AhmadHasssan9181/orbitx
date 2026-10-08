/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.filters

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.home.impl.filters.selection.FilterSelectionState
import kotlinx.collections.immutable.toImmutableList

class RoomListFiltersStateProvider : PreviewParameterProvider<RoomListFiltersState> {
    override val values: Sequence<RoomListFiltersState>
        get() = sequenceOf(
            aRoomListFiltersState(),
            aRoomListFiltersState(
                filterSelectionStates = RoomListFilter.entries.map { FilterSelectionState(it, isSelected = true) }
            ),
        )
}

fun aRoomListFiltersState(
    filterSelectionStates: List<FilterSelectionState> = RoomListFilter.entries.map { FilterSelectionState(it, isSelected = false) },
    eventSink: (RoomListFiltersEvent) -> Unit = {},
) = RoomListFiltersState(
    filterSelectionStates = filterSelectionStates.toImmutableList(),
    eventSink = eventSink,
)
