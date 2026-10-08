/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.permissions

interface ChangeRoomPermissionsEvent {
    data class ChangeMinimumRoleForAction(val action: RoomPermissionType, val role: SelectableRole) : ChangeRoomPermissionsEvent
    data object Save : ChangeRoomPermissionsEvent
    data object Exit : ChangeRoomPermissionsEvent
    data object ResetPendingActions : ChangeRoomPermissionsEvent
}
