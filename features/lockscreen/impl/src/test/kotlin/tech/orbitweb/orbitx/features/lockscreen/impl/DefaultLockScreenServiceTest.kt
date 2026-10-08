/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.BiometricAuthenticatorManager
import tech.orbitweb.orbitx.features.lockscreen.impl.biometric.FakeBiometricAuthenticatorManager
import tech.orbitweb.orbitx.features.lockscreen.impl.fixtures.aLockScreenConfig
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.PinCodeManager
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.SECRET_KEY_ALIAS
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.createDefaultPinCodeManager
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.storage.InMemoryLockScreenStore
import tech.orbitweb.orbitx.features.lockscreen.impl.storage.LockScreenStore
import tech.orbitweb.orbitx.libraries.cryptography.api.SecretKeyRepository
import tech.orbitweb.orbitx.libraries.cryptography.test.SimpleSecretKeyRepository
import tech.orbitweb.orbitx.libraries.sessionstorage.api.observer.SessionObserver
import tech.orbitweb.orbitx.libraries.sessionstorage.test.observer.FakeSessionObserver
import tech.orbitweb.orbitx.services.appnavstate.api.AppForegroundStateService
import tech.orbitweb.orbitx.services.appnavstate.test.FakeAppForegroundStateService

class DefaultLockScreenServiceTest {
    @Test
    fun `when the pin is not mandatory and no pin is configured isSetupRequired emits false`() = runTest {
        val sut = createDefaultLockScreenService(
            lockScreenConfig = aLockScreenConfig(isPinMandatory = false)
        )
        sut.isSetupRequired().test {
            assertThat(awaitItem()).isFalse()
        }
    }

    @Test
    fun `when the pin is mandatory, isSetupRequired emits true`() = runTest {
        val secretKeyRepository = SimpleSecretKeyRepository()
        val sut = createDefaultLockScreenService(
            lockScreenConfig = aLockScreenConfig(isPinMandatory = true),
            secretKeyRepository = secretKeyRepository,
        )
        sut.isSetupRequired().test {
            assertThat(awaitItem()).isTrue()
            // When the user configures the pin code, the setup is not required anymore
            secretKeyRepository.getOrCreateKey(SECRET_KEY_ALIAS, true)
            assertThat(awaitItem()).isFalse()
            // Users deletes the pin code
            secretKeyRepository.deleteKey("elementx.SECRET_KEY_ALIAS_PIN_CODE")
            assertThat(awaitItem()).isTrue()
        }
    }

    @Test
    fun `when the last session is deleted, the pin code is removed`() = runTest {
        val sessionObserver = FakeSessionObserver()
        val secretKeyRepository = SimpleSecretKeyRepository()
        val sut = createDefaultLockScreenService(
            lockScreenConfig = aLockScreenConfig(isPinMandatory = true),
            secretKeyRepository = secretKeyRepository,
            sessionObserver = sessionObserver,
        )
        sut.isPinSetup().test {
            assertThat(awaitItem()).isFalse()
            // When the user configure the pin code, the setup is not required anymore
            secretKeyRepository.getOrCreateKey(SECRET_KEY_ALIAS, true)
            assertThat(awaitItem()).isTrue()
            sessionObserver.onSessionDeleted("userId", wasLastSession = false)
            expectNoEvents()
            sessionObserver.onSessionDeleted("userId", wasLastSession = true)
            assertThat(awaitItem()).isFalse()
        }
    }
}

private fun TestScope.createDefaultLockScreenService(
    lockScreenConfig: LockScreenConfig = aLockScreenConfig(),
    lockScreenStore: LockScreenStore = InMemoryLockScreenStore(),
    secretKeyRepository: SecretKeyRepository = SimpleSecretKeyRepository(),
    pinCodeManager: PinCodeManager = createDefaultPinCodeManager(
        lockScreenStore = lockScreenStore,
        secretKeyRepository = secretKeyRepository,
    ),
    sessionObserver: SessionObserver = FakeSessionObserver(),
    appForegroundStateService: AppForegroundStateService = FakeAppForegroundStateService(),
    biometricAuthenticatorManager: BiometricAuthenticatorManager = FakeBiometricAuthenticatorManager(),
) = DefaultLockScreenService(
    lockScreenConfig = lockScreenConfig,
    lockScreenStore = lockScreenStore,
    pinCodeManager = pinCodeManager,
    coroutineScope = backgroundScope,
    sessionObserver = sessionObserver,
    appForegroundStateService = appForegroundStateService,
    biometricAuthenticatorManager = biometricAuthenticatorManager,
)
