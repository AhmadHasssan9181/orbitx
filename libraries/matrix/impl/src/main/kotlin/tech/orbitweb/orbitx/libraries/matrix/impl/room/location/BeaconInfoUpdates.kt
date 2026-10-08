/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.location

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.location.BeaconInfoUpdate
import org.matrix.rustcomponents.sdk.BeaconInfoUpdate as RustBeaconInfoUpdate

fun RustBeaconInfoUpdate.map(): BeaconInfoUpdate {
    return BeaconInfoUpdate(
        roomId = RoomId(roomId),
        beaconId = EventId(eventId),
        isLive = live
    )
}
