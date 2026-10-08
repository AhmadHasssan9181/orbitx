/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.test

import tech.orbitweb.orbitx.features.lockscreen.api.DeviceUnlockEntryPoint
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled

class FakeDeviceUnlockEntryPointCallback(
    private val onCancelLambda: () -> Unit = EnsureNeverCalled(),
    private val onUnlockedLambda: () -> Unit = EnsureNeverCalled(),
) : DeviceUnlockEntryPoint.Callback {
    override fun onCancel() = onCancelLambda()
    override fun onUnlock() = onUnlockedLambda()
}
