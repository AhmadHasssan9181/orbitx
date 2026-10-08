/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.history

import tech.orbitweb.orbitx.libraries.matrix.api.room.history.RoomHistoryVisibility
import org.matrix.rustcomponents.sdk.RoomHistoryVisibility as RustRoomHistoryVisibility

fun RoomHistoryVisibility.map(): RustRoomHistoryVisibility {
    return when (this) {
        RoomHistoryVisibility.WorldReadable -> RustRoomHistoryVisibility.WorldReadable
        RoomHistoryVisibility.Invited -> RustRoomHistoryVisibility.Invited
        RoomHistoryVisibility.Joined -> RustRoomHistoryVisibility.Joined
        RoomHistoryVisibility.Shared -> RustRoomHistoryVisibility.Shared
        is RoomHistoryVisibility.Custom -> RustRoomHistoryVisibility.Custom(value)
    }
}

fun RustRoomHistoryVisibility.map(): RoomHistoryVisibility {
    return when (this) {
        RustRoomHistoryVisibility.WorldReadable -> RoomHistoryVisibility.WorldReadable
        RustRoomHistoryVisibility.Invited -> RoomHistoryVisibility.Invited
        RustRoomHistoryVisibility.Joined -> RoomHistoryVisibility.Joined
        RustRoomHistoryVisibility.Shared -> RoomHistoryVisibility.Shared
        is RustRoomHistoryVisibility.Custom -> RoomHistoryVisibility.Custom(value)
    }
}
