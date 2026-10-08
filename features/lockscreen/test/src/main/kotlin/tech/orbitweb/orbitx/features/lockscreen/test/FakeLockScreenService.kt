/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.test

import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenLockState
import tech.orbitweb.orbitx.features.lockscreen.api.LockScreenService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class FakeLockScreenService : LockScreenService {
    private var isPinSetup = MutableStateFlow(false)
    private val _lockState: MutableStateFlow<LockScreenLockState> = MutableStateFlow(LockScreenLockState.Locked)
    override val lockState: StateFlow<LockScreenLockState> = _lockState

    override fun isSetupRequired(): Flow<Boolean> {
        return isPinSetup.map { !it }
    }

    fun setIsPinSetup(isPinSetup: Boolean) {
        this.isPinSetup.value = isPinSetup
    }

    override fun isPinSetup(): Flow<Boolean> {
        return isPinSetup
    }

    fun setLockState(lockState: LockScreenLockState) {
        _lockState.value = lockState
    }
}
