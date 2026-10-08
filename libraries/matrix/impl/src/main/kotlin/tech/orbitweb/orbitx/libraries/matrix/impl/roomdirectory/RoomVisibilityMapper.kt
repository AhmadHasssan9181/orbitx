/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.roomdirectory

import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomVisibility
import org.matrix.rustcomponents.sdk.RoomVisibility as RustRoomVisibility

fun RoomVisibility.map(): RustRoomVisibility {
    return when (this) {
        RoomVisibility.Public -> RustRoomVisibility.Public
        RoomVisibility.Private -> RustRoomVisibility.Private
        is RoomVisibility.Custom -> RustRoomVisibility.Custom(value)
    }
}

fun RustRoomVisibility.map(): RoomVisibility {
    return when (this) {
        RustRoomVisibility.Public -> RoomVisibility.Public
        RustRoomVisibility.Private -> RoomVisibility.Private
        is RustRoomVisibility.Custom -> RoomVisibility.Custom(value)
    }
}
