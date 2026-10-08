/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenLockState
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenService
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.BiometricAuthenticatorManager
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.DefaultBiometricUnlockCallback
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.DefaultPinCodeManagerCallback
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.PinCodeManager
import tech.orbitweb.orbitx.features.lockscreen.impl.storage.LockScreenStore
import tech.orbitweb.orbitx.libraries.di.annotations.AppCoroutineScope
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionListener
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionObserver
import tech.orbitweb.orbitx.services.appnavstate.api.AppForegroundStateService
import kotlin.time.Duration

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultLockScreenService(
    private val lockScreenConfig: LockScreenConfig,
    private val lockScreenStore: LockScreenStore,
    private val pinCodeManager: PinCodeManager,
    @AppCoroutineScope
    private val coroutineScope: CoroutineScope,
    private val sessionObserver: SessionObserver,
    private val appForegroundStateService: AppForegroundStateService,
    private val biometricAuthenticatorManager: BiometricAuthenticatorManager,
) : LockScreenService {
    private val _lockState = MutableStateFlow<LockScreenLockState>(LockScreenLockState.Unlocked)
    override val lockState: StateFlow<LockScreenLockState> = _lockState

    private var lockJob: Job? = null

    init {
        pinCodeManager.addCallback(object : DefaultPinCodeManagerCallback() {
            override fun onPinCodeVerified() {
                _lockState.value = LockScreenLockState.Unlocked
            }

            override fun onPinCodeRemoved() {
                _lockState.value = LockScreenLockState.Unlocked
            }
        })
        biometricAuthenticatorManager.addCallback(object : DefaultBiometricUnlockCallback() {
            override fun onBiometricAuthenticationSuccess() {
                _lockState.value = LockScreenLockState.Unlocked
                coroutineScope.launch {
                    lockScreenStore.resetCounter()
                }
            }
        })
        coroutineScope.lockIfNeeded()
        observeAppForegroundState()
        observeSessionsState()
    }

    /**
     * Makes sure to delete the pin code when the last session is deleted.
     */
    private fun observeSessionsState() {
        sessionObserver.addListener(object : SessionListener {
            override suspend fun onSessionDeleted(userId: String, wasLastSession: Boolean) {
                if (wasLastSession) {
                    pinCodeManager.deletePinCode()
                    biometricAuthenticatorManager.disable()
                }
            }
        })
    }

    /**
     * Makes sure to lock the app if it goes in background for a certain amount of time.
     */
    private fun observeAppForegroundState() {
        coroutineScope.launch {
            appForegroundStateService.startObservingForeground()
            appForegroundStateService.isInForeground.collect { isInForeground ->
                if (isInForeground) {
                    lockJob?.cancel()
                } else {
                    lockJob = lockIfNeeded(gracePeriod = lockScreenConfig.gracePeriod)
                }
            }
        }
    }

    override fun isPinSetup(): Flow<Boolean> {
        return pinCodeManager.hasPinCode()
    }

    override fun isSetupRequired(): Flow<Boolean> {
        return isPinSetup().map { isPinSetup ->
            !isPinSetup && lockScreenConfig.isPinMandatory
        }
    }

    private fun CoroutineScope.lockIfNeeded(gracePeriod: Duration = Duration.ZERO) = launch {
        if (isPinSetup().first()) {
            delay(gracePeriod)
            _lockState.value = LockScreenLockState.Locked
        }
    }
}
