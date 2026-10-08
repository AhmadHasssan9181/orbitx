/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.test

import tech.orbitweb.orbitx.features.ftue.api.state.FtueService
import tech.orbitweb.orbitx.features.ftue.api.state.FtueState
import kotlinx.coroutines.flow.MutableStateFlow

class FakeFtueService : FtueService {
    override val state: MutableStateFlow<FtueState> = MutableStateFlow(FtueState.Unknown)

    suspend fun emitState(newState: FtueState) {
        state.emit(newState)
    }
}
