/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.tombstone

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.tombstone.SuccessorRoom
import org.matrix.rustcomponents.sdk.SuccessorRoom as RustSuccessorRoom

fun RustSuccessorRoom.map(): SuccessorRoom {
    return SuccessorRoom(
        roomId = RoomId(roomId),
        reason = reason
    )
}
