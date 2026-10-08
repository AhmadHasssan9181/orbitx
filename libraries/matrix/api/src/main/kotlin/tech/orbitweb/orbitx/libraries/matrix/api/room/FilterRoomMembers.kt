/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room

import tech.orbitweb.orbitx.libraries.core.bool.orFalse
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Method to filter members by userId or displayName.
 * It does filter through the already known members, it doesn't perform additional requests.
 */
suspend fun BaseRoom.filterMembers(query: String, coroutineContext: CoroutineContext): List<RoomMember> = withContext(coroutineContext) {
    val activeRoomMembers = membersStateFlow.value.activeRoomMembers()
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
