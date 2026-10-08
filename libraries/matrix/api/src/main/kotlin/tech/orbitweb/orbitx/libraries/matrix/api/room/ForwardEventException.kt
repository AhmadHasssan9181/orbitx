/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

class ForwardEventException(
    val roomIds: List<RoomId>
) : Exception() {
    override val message: String? = "Failed to deliver event to $roomIds rooms"
}
