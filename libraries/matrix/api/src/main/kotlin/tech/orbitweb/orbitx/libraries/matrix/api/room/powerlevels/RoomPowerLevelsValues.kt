/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels

data class RoomPowerLevelsValues(
    val ban: Long,
    val invite: Long,
    val kick: Long,
    val eventsDefault: Long,
    val stateDefault: Long,
    val redactEvents: Long,
    val roomName: Long,
    val roomAvatar: Long,
    val roomTopic: Long,
    val spaceChild: Long,
    val beacon: Long,
    val beaconInfo: Long,
)
