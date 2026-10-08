/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.chooseaccountprovider

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.features.login.impl.accountprovider.anAccountProvider
import tech.orbitweb.orbitx.features.login.impl.login.LoginMode
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

class ChooseAccountProviderStateTest {
    @Test
    fun `submitEnabled returns false when there is no selectedAccountProvider`() {
        val sut = aChooseAccountProviderState(
            selectedAccountProvider = null,
        )
        assertThat(sut.submitEnabled).isFalse()
    }

    @Test
    fun `submitEnabled returns true when there is a selectedAccountProvider`() {
        val sut = aChooseAccountProviderState(
            selectedAccountProvider = anAccountProvider(),
        )
        assertThat(sut.submitEnabled).isTrue()
    }

    @Test
    fun `submitEnabled returns false when there is a selectedAccountProvider but there is an error`() {
        val sut = aChooseAccountProviderState(
            selectedAccountProvider = anAccountProvider(),
            loginMode = AsyncData.Failure(Throwable("Error")),
        )
        assertThat(sut.submitEnabled).isFalse()
    }

    @Test
    fun `submitEnabled returns false when there is a selectedAccountProvider but the result is successful`() {
        val sut = aChooseAccountProviderState(
            selectedAccountProvider = anAccountProvider(),
            loginMode = AsyncData.Success(LoginMode.PasswordLogin),
        )
        assertThat(sut.submitEnabled).isFalse()
    }
}
