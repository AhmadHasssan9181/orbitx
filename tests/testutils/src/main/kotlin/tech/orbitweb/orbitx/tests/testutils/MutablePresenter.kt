/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.tests.testutils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import kotlinx.coroutines.flow.MutableStateFlow

class MutablePresenter<State>(initialState: State) : Presenter<State> {
    private val stateFlow = MutableStateFlow(initialState)

    fun updateState(state: State) {
        stateFlow.value = state
    }

    @Composable
    override fun present(): State {
        return stateFlow.collectAsState().value
    }
}
