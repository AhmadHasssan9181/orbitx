/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.model

import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomInfo
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomSummary
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import kotlinx.collections.immutable.ImmutableList

data class SelectRoomInfo(
    val roomId: RoomId,
    val name: String?,
    val canonicalAlias: RoomAlias?,
    val avatarUrl: String?,
    val heroes: ImmutableList<MatrixUser>,
    val isTombstoned: Boolean,
) {
    fun getAvatarData(size: AvatarSize) = AvatarData(
        id = roomId.value,
        name = name,
        url = avatarUrl,
        size = size,
    )
}

fun RoomSummary.toSelectRoomInfo() = info.toSelectRoomInfo()

fun RoomInfo.toSelectRoomInfo() = SelectRoomInfo(
    roomId = id,
    name = name,
    avatarUrl = avatarUrl,
    heroes = heroes,
    canonicalAlias = canonicalAlias,
    isTombstoned = successorRoom != null,
)
