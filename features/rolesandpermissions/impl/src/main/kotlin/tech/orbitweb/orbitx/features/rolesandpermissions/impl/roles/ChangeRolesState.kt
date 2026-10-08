/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.roles

import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.SearchBarResultState
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

data class ChangeRolesState(
    val role: RoomMember.Role,
    val searchQuery: TextFieldState,
    val isSearchActive: Boolean,
    val searchResults: SearchBarResultState<MembersByRole>,
    val selectedUsers: ImmutableList<MatrixUser>,
    val hasPendingChanges: Boolean,
    val savingState: AsyncAction<Boolean>,
    val canChangeMemberRole: (UserId) -> Boolean,
    val eventSink: (ChangeRolesEvent) -> Unit,
)

data class MembersByRole(
    val owners: ImmutableList<RoomMember> = persistentListOf(),
    val admins: ImmutableList<RoomMember> = persistentListOf(),
    val moderators: ImmutableList<RoomMember> = persistentListOf(),
    val members: ImmutableList<RoomMember> = persistentListOf(),
) {
    constructor(members: List<RoomMember>, comparator: Comparator<RoomMember>) : this(
        owners = members.filterAndSort(comparator) { it.role is RoomMember.Role.Owner },
        admins = members.filterAndSort(comparator) { it.role == RoomMember.Role.Admin },
        moderators = members.filterAndSort(comparator) { it.role == RoomMember.Role.Moderator },
        members = members.filterAndSort(comparator) { it.role == RoomMember.Role.User },
    )

    fun isEmpty() = owners.isEmpty() && admins.isEmpty() && moderators.isEmpty() && members.isEmpty()
}

private fun Iterable<RoomMember>.filterAndSort(
    comparator: Comparator<RoomMember>,
    predicate: (RoomMember) -> Boolean,
): ImmutableList<RoomMember> {
    return filter(predicate).sortedWith(comparator).toImmutableList()
}
