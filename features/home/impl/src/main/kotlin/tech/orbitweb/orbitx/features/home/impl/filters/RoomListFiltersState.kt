/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.filters

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.home.impl.filters.selection.FilterSelectionState

data class RoomListFiltersState(
    val filterSelectionStates: ImmutableList<FilterSelectionState>,
    val eventSink: (RoomListFiltersEvent) -> Unit,
) {
    val hasAnyFilterSelected = filterSelectionStates.any { it.isSelected }

    fun selectedFilters(): ImmutableList<RoomListFilter> {
        return filterSelectionStates
            .filter { it.isSelected }
            .map { it.filter }
            .toImmutableList()
    }
}
