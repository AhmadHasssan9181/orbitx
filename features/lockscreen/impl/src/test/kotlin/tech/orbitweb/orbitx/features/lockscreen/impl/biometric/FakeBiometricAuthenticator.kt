/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.biometric

import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeBiometricAuthenticator(
    override val isActive: Boolean = false,
    private val setupLambda: () -> Unit = { },
    private val authenticateLambda: () -> BiometricAuthenticator.AuthenticationResult = { BiometricAuthenticator.AuthenticationResult.Success },
) : BiometricAuthenticator {
    override suspend fun setup() = simulateLongTask {
        setupLambda()
    }

    override suspend fun authenticate() = simulateLongTask {
        authenticateLambda()
    }
}
