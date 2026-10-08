/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room

sealed interface RoomType {
    data object Space : RoomType
    data object Room : RoomType
    data class Other(val type: String) : RoomType
}
