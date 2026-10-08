/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.setup.biometric

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.BiometricAuthenticator
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.BiometricAuthenticatorManager
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.FakeBiometricAuthenticator
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.FakeBiometricAuthenticatorManager
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.storage.InMemoryLockScreenStore
import tech.orbitweb.orbitx.features.lockscreen.impl.storage.LockScreenStore
import tech.orbitweb.orbitx.tests.testutils.test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetupBiometricPresenterTest {
    @Test
    fun `present - allow flow with biometric authentication success`() = runTest {
        val lockScreenStore = InMemoryLockScreenStore()
        val fakeBiometricAuthenticatorManager = FakeBiometricAuthenticatorManager(createBiometricAuthenticator = {
            FakeBiometricAuthenticator(authenticateLambda = { BiometricAuthenticator.AuthenticationResult.Success })
        })
        val presenter = createSetupBiometricPresenter(lockScreenStore, fakeBiometricAuthenticatorManager)
        presenter.test {
            awaitItem().also { state ->
                assertThat(state.isBiometricSetupDone).isFalse()
                state.eventSink(SetupBiometricEvent.AllowBiometric)
            }
            awaitItem().also { state ->
                assertThat(state.isBiometricSetupDone).isTrue()
            }
        }
        assertThat(lockScreenStore.isBiometricUnlockAllowed().first()).isTrue()
    }

    @Test
    fun `present - allow flow with biometric authentication failure`() = runTest {
        val lockScreenStore = InMemoryLockScreenStore()
        val fakeBiometricAuthenticatorManager = FakeBiometricAuthenticatorManager(createBiometricAuthenticator = {
            FakeBiometricAuthenticator(authenticateLambda = { BiometricAuthenticator.AuthenticationResult.Failure() })
        })
        val presenter = createSetupBiometricPresenter(lockScreenStore, fakeBiometricAuthenticatorManager)
        presenter.test {
            awaitItem().also { state ->
                assertThat(state.isBiometricSetupDone).isFalse()
                state.eventSink(SetupBiometricEvent.AllowBiometric)
            }
        }
        assertThat(lockScreenStore.isBiometricUnlockAllowed().first()).isFalse()
    }

    @Test
    fun `present - skip flow`() = runTest {
        val lockScreenStore = InMemoryLockScreenStore()
        val presenter = createSetupBiometricPresenter(lockScreenStore)
        presenter.test {
            awaitItem().also { state ->
                assertThat(state.isBiometricSetupDone).isFalse()
                state.eventSink(SetupBiometricEvent.UsePin)
            }
            awaitItem().also { state ->
                assertThat(state.isBiometricSetupDone).isTrue()
            }
        }
        assertThat(lockScreenStore.isBiometricUnlockAllowed().first()).isFalse()
    }

    private fun createSetupBiometricPresenter(
        lockScreenStore: LockScreenStore = InMemoryLockScreenStore(),
        biometricAuthenticatorManager: BiometricAuthenticatorManager = FakeBiometricAuthenticatorManager(),
    ): SetupBiometricPresenter {
        return SetupBiometricPresenter(
            lockScreenStore = lockScreenStore,
            biometricAuthenticatorManager = biometricAuthenticatorManager
        )
    }
}
