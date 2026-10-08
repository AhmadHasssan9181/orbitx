/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdirectory.impl.root.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tech.orbitweb.orbitx.features.roomdirectory.api.RoomDescription

internal data class RoomDirectoryListState(
    val hasMoreToLoad: Boolean,
    val items: ImmutableList<RoomDescription>,
) {
    companion object {
        val Default = RoomDirectoryListState(
            hasMoreToLoad = true,
            items = persistentListOf()
        )
    }
}
