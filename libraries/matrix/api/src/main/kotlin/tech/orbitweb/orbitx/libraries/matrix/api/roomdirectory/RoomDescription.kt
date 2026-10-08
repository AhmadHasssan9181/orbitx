/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

data class RoomDescription(
    val roomId: RoomId,
    val name: String?,
    val topic: String?,
    val alias: RoomAlias?,
    val avatarUrl: String?,
    val joinRule: JoinRule,
    val isWorldReadable: Boolean,
    val numberOfMembers: Long
) {
    enum class JoinRule {
        PUBLIC,
        KNOCK,
        RESTRICTED,
        KNOCK_RESTRICTED,
        INVITE,
        UNKNOWN
    }
}
