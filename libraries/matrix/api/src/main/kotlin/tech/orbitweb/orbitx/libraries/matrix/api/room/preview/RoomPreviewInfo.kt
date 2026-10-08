/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room.preview

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.CurrentUserMembership
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomType
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule

data class RoomPreviewInfo(
    /** The room id for this room. */
    val roomId: RoomId,
    /** The canonical alias for the room. */
    val canonicalAlias: RoomAlias?,
    /** The room's name, if set. */
    val name: String?,
    /** The room's topic, if set. */
    val topic: String?,
    /** The MXC URI to the room's avatar, if set. */
    val avatarUrl: String?,
    /** The number of joined members. */
    val numberOfJoinedMembers: Long,
    /** The room type (space, custom) or nothing, if it's a regular room. */
    val roomType: RoomType,
    /** Is the history world-readable for this room? */
    val isHistoryWorldReadable: Boolean,
    /** the membership of the current user. */
    val membership: CurrentUserMembership?,
    /** The room's join rule. */
    val joinRule: JoinRule?,
)
