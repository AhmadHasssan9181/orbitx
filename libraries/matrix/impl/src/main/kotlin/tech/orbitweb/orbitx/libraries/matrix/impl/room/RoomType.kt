/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room

import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomType
import org.matrix.rustcomponents.sdk.RoomType as RustRoomType

fun RustRoomType.map(): RoomType {
    return when (this) {
        RustRoomType.Room -> RoomType.Room
        RustRoomType.Space -> RoomType.Space
        is RustRoomType.Custom -> RoomType.Other(this.value)
    }
}
