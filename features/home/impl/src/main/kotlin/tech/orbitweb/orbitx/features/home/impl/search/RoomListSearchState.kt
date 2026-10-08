/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.search

import androidx.compose.foundation.text.input.TextFieldState
import tech.orbitweb.orbitx.features.home.impl.model.RoomListRoomSummary
import kotlinx.collections.immutable.ImmutableList

data class RoomListSearchState(
    val isSearchActive: Boolean,
    val query: TextFieldState,
    val results: ImmutableList<RoomListRoomSummary>,
    val eventSink: (RoomListSearchEvent) -> Unit
)
