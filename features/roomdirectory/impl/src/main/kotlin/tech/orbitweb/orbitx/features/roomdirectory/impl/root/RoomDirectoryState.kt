/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdirectory.impl.root

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.roomdirectory.api.RoomDescription

data class RoomDirectoryState(
    val query: String,
    val roomDescriptions: ImmutableList<RoomDescription>,
    val displayLoadMoreIndicator: Boolean,
    val eventSink: (RoomDirectoryEvents) -> Unit
) {
    val displayEmptyState = roomDescriptions.isEmpty() && !displayLoadMoreIndicator
}
