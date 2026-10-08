/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.spaces

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.CurrentUserMembership
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomType
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

@Immutable
data class SpaceRoom(
    val rawName: String?,
    val displayName: String,
    val avatarUrl: String?,
    val canonicalAlias: RoomAlias?,
    val childrenCount: Int,
    val guestCanJoin: Boolean,
    val heroes: ImmutableList<MatrixUser>,
    val joinRule: JoinRule?,
    val numJoinedMembers: Int,
    val roomId: RoomId,
    val roomType: RoomType,
    val state: CurrentUserMembership?,
    val topic: String?,
    val worldReadable: Boolean,
    /**
     * The via parameters of the room.
     */
    val via: ImmutableList<String>,
    val isDirect: Boolean?,
    val isDm: Boolean?,
) {
    val isSpace = roomType == RoomType.Space

    val visibility = SpaceRoomVisibility.fromJoinRule(joinRule)
}
