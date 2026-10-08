/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.oauth

import tech.orbitweb.orbitx.libraries.matrix.api.core.DeviceId

sealed interface AccountManagementAction {
    data object Profile : AccountManagementAction
    data object DevicesList : AccountManagementAction
    data class DeviceView(val deviceId: DeviceId) : AccountManagementAction
    data class DeviceDelete(val deviceId: DeviceId) : AccountManagementAction
}
