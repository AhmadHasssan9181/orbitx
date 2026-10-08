/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.search

sealed interface RoomListSearchEvent {
    data object ToggleSearchVisibility : RoomListSearchEvent
    data object ClearQuery : RoomListSearchEvent
    data class UpdateVisibleRange(val range: IntRange) : RoomListSearchEvent
}
