/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.withContext
import tech.orbitweb.orbitx.libraries.core.bool.orFalse
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.matrix.api.room.BaseRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.room.roomMembers

@Inject
class RoomMemberListDataSource(
    private val room: BaseRoom,
    private val coroutineDispatchers: CoroutineDispatchers,
) {
    suspend fun search(query: String): List<RoomMember> = withContext(coroutineDispatchers.io) {
        val roomMembersState = room.membersStateFlow.value
        val activeRoomMembers = roomMembersState.roomMembers()
            ?.filter { it.membership.isActive() }
            .orEmpty()
        val filteredMembers = if (query.isBlank()) {
            activeRoomMembers
        } else {
            activeRoomMembers.filter { member ->
                member.userId.value.contains(query, ignoreCase = true) ||
                        member.displayName?.contains(query, ignoreCase = true).orFalse()
            }
        }
        filteredMembers
    }
}
