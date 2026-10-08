/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.device

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import tech.orbitweb.orbitx.features.lockscreen.api.DeviceUnlockEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Inject
@SingleIn(AppScope::class)
class DeviceUnlockCallbackHolder {
    private val _deviceUnlockCallback = MutableStateFlow<DeviceUnlockEntryPoint.Callback?>(null)
    val deviceUnlockCallback: StateFlow<DeviceUnlockEntryPoint.Callback?> = _deviceUnlockCallback

    fun requestUnlock(callback: DeviceUnlockEntryPoint.Callback) {
        _deviceUnlockCallback.value = callback
    }

    fun onDone() {
        _deviceUnlockCallback.value = null
    }
}
