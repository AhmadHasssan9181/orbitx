/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.filters.selection

import tech.orbitweb.orbitx.features.home.impl.filters.RoomListFilter

data class FilterSelectionState(
    val filter: RoomListFilter,
    val isSelected: Boolean,
)
