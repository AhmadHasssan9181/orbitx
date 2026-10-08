/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.fixtures

import tech.orbitweb.orbitx.features.lockscreen.impl.pin.DefaultPinCodeManager
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.PinCodeManager
import tech.orbitweb.orbitx.features.lockscreen.impl.pin.storage.InMemoryLockScreenStore
import tech.orbitweb.orbitx.features.lockscreen.impl.storage.LockScreenStore
import tech.orbitweb.orbitx.libraries.cryptography.api.EncryptionDecryptionService
import tech.orbitweb.orbitx.libraries.cryptography.impl.AESEncryptionDecryptionService
import tech.orbitweb.orbitx.libraries.cryptography.test.SimpleSecretKeyRepository

internal fun aPinCodeManager(
    lockScreenStore: LockScreenStore = InMemoryLockScreenStore(),
    secretKeyRepository: SimpleSecretKeyRepository = SimpleSecretKeyRepository(),
    encryptionDecryptionService: EncryptionDecryptionService = AESEncryptionDecryptionService(),
): PinCodeManager {
    return DefaultPinCodeManager(secretKeyRepository, encryptionDecryptionService, lockScreenStore)
}
