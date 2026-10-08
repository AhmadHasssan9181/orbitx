/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.setup.biometric

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.BiometricAuthenticator
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.BiometricAuthenticatorManager
import tech.orbitweb.orbitx.features.lockscreen.impl.storage.LockScreenStore
import tech.orbitweb.orbitx.libraries.architecture.Presenter

@Inject
class SetupBiometricPresenter(
    private val lockScreenStore: LockScreenStore,
    private val biometricAuthenticatorManager: BiometricAuthenticatorManager,
) : Presenter<SetupBiometricState> {
    @Composable
    override fun present(): SetupBiometricState {
        var isBiometricSetupDone by remember {
            mutableStateOf(false)
        }

        val coroutineScope = rememberCoroutineScope()
        val biometricUnlock = biometricAuthenticatorManager.rememberConfirmBiometricAuthenticator()

        fun handleEvent(event: SetupBiometricEvent) {
            when (event) {
                SetupBiometricEvent.AllowBiometric -> coroutineScope.launch {
                    biometricUnlock.setup()
                    if (biometricUnlock.authenticate() == BiometricAuthenticator.AuthenticationResult.Success) {
                        lockScreenStore.setIsBiometricUnlockAllowed(true)
                        isBiometricSetupDone = true
                    }
                }
                SetupBiometricEvent.UsePin -> coroutineScope.launch {
                    lockScreenStore.setIsBiometricUnlockAllowed(false)
                    isBiometricSetupDone = true
                }
            }
        }

        return SetupBiometricState(
            isBiometricSetupDone = isBiometricSetupDone,
            eventSink = ::handleEvent,
        )
    }
}
