/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.roles

import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

sealed interface ChangeRolesEvent {
    data object ToggleSearchActive : ChangeRolesEvent
    data class UserSelectionToggled(val matrixUser: MatrixUser) : ChangeRolesEvent
    data object Save : ChangeRolesEvent
    data object Exit : ChangeRolesEvent
    data object CloseDialog : ChangeRolesEvent
}
