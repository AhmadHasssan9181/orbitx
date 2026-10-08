/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room

import org.matrix.rustcomponents.sdk.RoomInfo
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

/**
 * Extract the heroes from the room info.
 * For now we only use heroes for direct rooms with 2 members.
 * Also we keep the heroes only if there is one single hero.
 */
fun RoomInfo.elementHeroes(): List<MatrixUser> {
    return heroes
        .takeIf { isDm }
        ?.takeIf { it.size == 1 }
        ?.map { it.map() }
        .orEmpty()
}
