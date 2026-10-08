/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.common

import tech.orbitweb.orbitx.libraries.matrix.api.room.MessageEventType
import tech.orbitweb.orbitx.libraries.matrix.api.room.StateEventType
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPermissions

/**
 * Permissions to send beacon and beacon_info events in the room.
 */
data class SendLiveLocationPermissions(
    val canSendBeacon: Boolean,
    val canSendBeaconInfo: Boolean,
) {
    val hasAll = canSendBeaconInfo && canSendBeacon

    companion object {
        val DEFAULT = SendLiveLocationPermissions(canSendBeacon = false, canSendBeaconInfo = false)
        val GRANTED = SendLiveLocationPermissions(canSendBeacon = true, canSendBeaconInfo = true)
    }
}

fun RoomPermissions.sendLiveLocationPermissions(): SendLiveLocationPermissions {
    return SendLiveLocationPermissions(
        canSendBeaconInfo = canOwnUserSendState(StateEventType.BeaconInfo),
        canSendBeacon = canOwnUserSendMessage(MessageEventType.Beacon),
    )
}
