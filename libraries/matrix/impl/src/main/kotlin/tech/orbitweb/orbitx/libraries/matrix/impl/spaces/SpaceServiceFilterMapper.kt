/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.spaces

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceServiceFilter
import org.matrix.rustcomponents.sdk.SpaceFilter as RustSpaceFilter

class SpaceServiceFilterMapper(
    private val spaceRoomMapper: SpaceRoomMapper,
) {
    fun map(spaceFilter: RustSpaceFilter): SpaceServiceFilter {
        return SpaceServiceFilter(
            spaceRoom = spaceRoomMapper.map(spaceFilter.spaceRoom),
            level = spaceFilter.level.toInt(),
            descendants = spaceFilter.descendants.map { RoomId(it) },
        )
    }
}
