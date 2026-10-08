/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.oauth

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.oauth.AccountManagementAction
import tech.orbitweb.orbitx.libraries.matrix.test.A_DEVICE_ID
import org.matrix.rustcomponents.sdk.AccountManagementAction as RustAccountManagementAction

class AccountManagementActionKtTest {
    @Test
    fun `test AccountManagementAction to RustAccountManagementAction`() {
        assertThat(AccountManagementAction.Profile.toRustAction())
            .isEqualTo(RustAccountManagementAction.Profile)
        assertThat(AccountManagementAction.DeviceDelete(A_DEVICE_ID).toRustAction())
            .isEqualTo(RustAccountManagementAction.DeviceDelete(A_DEVICE_ID.value))
        assertThat(AccountManagementAction.DeviceView(A_DEVICE_ID).toRustAction())
            .isEqualTo(RustAccountManagementAction.DeviceView(A_DEVICE_ID.value))
        assertThat(AccountManagementAction.DevicesList.toRustAction())
            .isEqualTo(RustAccountManagementAction.DevicesList)
    }
}
