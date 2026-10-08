/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.model

import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomInfo
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember

fun RoomInfo.getAvatarData(size: AvatarSize) = AvatarData(
    id = id.value,
    name = name,
    url = avatarUrl,
    size = size,
)

/**
 * Returns the power level of the user in the room.
 * If the user is a creator and [RoomInfo.privilegedCreatorRole] is true, returns the power level of [RoomMember.Role.Owner].
 * Otherwise, checks the room's power levels for the user's power level.
 * If no specific power level is set for the user, defaults to 0.
 */
fun RoomInfo.powerLevelOf(userId: UserId): Long {
    return if (privilegedCreatorRole && creators.contains(userId)) {
        RoomMember.Role.Owner(isCreator = true).powerLevel
    } else {
        roomPowerLevels?.powerLevelOf(userId = userId) ?: 0L
    }
}

/**
 * Returns the role of the user in the room.
 * If the user is a creator and [RoomInfo.privilegedCreatorRole] is true, returns [RoomMember.Role.Owner].
 * Otherwise, checks the power levels and returns the corresponding role.
 * If no specific power level is set for the user, defaults to [RoomMember.Role.User].
 */
fun RoomInfo.roleOf(userId: UserId): RoomMember.Role {
    val powerLevel = powerLevelOf(userId = userId)
    return RoomMember.Role.forPowerLevel(powerLevel)
}
