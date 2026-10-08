/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.root

import tech.orbitweb.orbitx.features.rolesandpermissions.impl.R
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import kotlinx.collections.immutable.ImmutableList

data class RolesAndPermissionsState(
    val roomSupportsOwnerRole: Boolean,
    val adminCount: Int?,
    val moderatorCount: Int?,
    val availableSelfDemoteActions: ImmutableList<SelfDemoteAction>,
    val changeOwnRoleAction: AsyncAction<Unit>,
    val resetPermissionsAction: AsyncAction<Unit>,
    val eventSink: (RolesAndPermissionsEvents) -> Unit,
) {
    val canSelfDemote = availableSelfDemoteActions.isNotEmpty()
}

enum class SelfDemoteAction(val role: RoomMember.Role, val titleRes: Int) {
    ToModerator(RoomMember.Role.Moderator, R.string.screen_room_roles_and_permissions_change_role_demote_to_moderator),
    ToMember(RoomMember.Role.User, R.string.screen_room_roles_and_permissions_change_role_demote_to_member)
}
