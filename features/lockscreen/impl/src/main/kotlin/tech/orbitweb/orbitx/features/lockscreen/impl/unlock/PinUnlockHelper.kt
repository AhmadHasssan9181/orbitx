/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.unlock

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.BiometricAuthenticatorManager
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.DefaultBiometricUnlockCallback
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.DefaultPinCodeManagerCallback
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.PinCodeManager

@Inject
class PinUnlockHelper(
    private val biometricAuthenticatorManager: BiometricAuthenticatorManager,
    private val pinCodeManager: PinCodeManager
) {
    @Composable
    fun OnUnlockEffect(onUnlock: (Boolean) -> Unit) {
        val latestOnUnlock by rememberUpdatedState(onUnlock)
        DisposableEffect(Unit) {
            val biometricUnlockCallback = object : DefaultBiometricUnlockCallback() {
                override fun onBiometricAuthenticationSuccess() {
                    latestOnUnlock(true)
                }

                override fun onBiometricAuthenticationFailed(error: Exception?) {
                    if (error != null) {
                        latestOnUnlock(false)
                    }
                }
            }
            val pinCodeVerifiedCallback = object : DefaultPinCodeManagerCallback() {
                override fun onPinCodeVerified() {
                    latestOnUnlock(true)
                }
            }
            biometricAuthenticatorManager.addCallback(biometricUnlockCallback)
            pinCodeManager.addCallback(pinCodeVerifiedCallback)
            onDispose {
                biometricAuthenticatorManager.removeCallback(biometricUnlockCallback)
                pinCodeManager.removeCallback(pinCodeVerifiedCallback)
            }
        }
    }
}
