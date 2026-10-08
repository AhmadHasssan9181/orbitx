/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.powerlevels

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPowerLevelsValues
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories.aRustRoomPowerLevelsValues

class RoomPowerLevelsValuesMapperTest {
    @Test
    fun `test that mapping of RoomPowerLevelsValues is correct`() {
        assertThat(
            RoomPowerLevelsValuesMapper.map(
                aRustRoomPowerLevelsValues(
                    ban = 1,
                    invite = 2,
                    kick = 3,
                    redact = 4,
                    eventsDefault = 5,
                    stateDefault = 6,
                    usersDefault = 7,
                    roomName = 8,
                    roomAvatar = 9,
                    roomTopic = 10,
                    spaceChild = 11,
                    beacon = 12,
                    beaconInfo = 13,
                )
            )
        ).isEqualTo(
            RoomPowerLevelsValues(
                ban = 1,
                invite = 2,
                kick = 3,
                redactEvents = 4,
                eventsDefault = 5,
                stateDefault = 6,
                roomName = 8,
                roomAvatar = 9,
                roomTopic = 10,
                spaceChild = 11,
                beacon = 12,
                beaconInfo = 13,
            )
        )
    }
}
