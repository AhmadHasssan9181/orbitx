/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.root

import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember

sealed interface RolesAndPermissionsEvents {
    data object ChangeOwnRole : RolesAndPermissionsEvents
    data class DemoteSelfTo(val role: RoomMember.Role) : RolesAndPermissionsEvents
    data object ResetPermissions : RolesAndPermissionsEvents
    data object CancelPendingAction : RolesAndPermissionsEvents
}
