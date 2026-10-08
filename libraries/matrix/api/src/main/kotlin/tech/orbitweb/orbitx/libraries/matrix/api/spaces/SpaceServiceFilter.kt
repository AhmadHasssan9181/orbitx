/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.spaces

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

/**
 * Represents a space filter for filtering rooms by space membership.
 *
 * @property spaceRoom The space room associated with this filter.
 * @property level The nesting level of the space (0 = top level, 1 = first level child, etc.).
 * @property descendants The list of room IDs that are descendants of this space.
 */
data class SpaceServiceFilter(
    val spaceRoom: SpaceRoom,
    val level: Int,
    val descendants: List<RoomId>,
)
