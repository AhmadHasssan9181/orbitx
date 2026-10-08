/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.powerlevels

import org.matrix.rustcomponents.sdk.PowerLevel
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPowerLevelsValues
import org.matrix.rustcomponents.sdk.RoomPowerLevelsValues as RustRoomPowerLevelsValues

object RoomPowerLevelsValuesMapper {
    fun map(values: RustRoomPowerLevelsValues): RoomPowerLevelsValues {
        return RoomPowerLevelsValues(
            ban = values.ban,
            invite = values.invite,
            kick = values.kick,
            eventsDefault = values.eventsDefault,
            stateDefault = values.stateDefault,
            redactEvents = values.redact,
            roomName = values.roomName,
            roomAvatar = values.roomAvatar,
            roomTopic = values.roomTopic,
            spaceChild = values.spaceChild,
            beacon = values.beacon,
            beaconInfo = values.beaconInfo,
        )
    }
}

fun PowerLevel.into(): Long = when (this) {
    PowerLevel.Infinite -> RoomMember.Role.Owner(isCreator = true).powerLevel
    is PowerLevel.Value -> this.value
}
