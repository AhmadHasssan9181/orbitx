/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.oauth

import tech.orbitweb.orbitx.libraries.matrix.api.oauth.AccountManagementAction
import org.matrix.rustcomponents.sdk.AccountManagementAction as RustAccountManagementAction

fun AccountManagementAction.toRustAction(): RustAccountManagementAction {
    return when (this) {
        AccountManagementAction.Profile -> RustAccountManagementAction.Profile
        is AccountManagementAction.DeviceDelete -> RustAccountManagementAction.DeviceDelete(deviceId.value)
        is AccountManagementAction.DeviceView -> RustAccountManagementAction.DeviceView(deviceId.value)
        AccountManagementAction.DevicesList -> RustAccountManagementAction.DevicesList
    }
}
