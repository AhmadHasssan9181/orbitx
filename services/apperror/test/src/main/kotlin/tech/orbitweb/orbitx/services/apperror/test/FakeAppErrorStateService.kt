/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.apperror.test

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.orbitweb.orbitx.services.apperror.api.AppErrorState
import tech.orbitweb.orbitx.services.apperror.api.AppErrorStateService
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeAppErrorStateService(
    initialState: AppErrorState = AppErrorState.NoError,
    private val showErrorResult: (String, String) -> Unit = { _, _ -> lambdaError() },
    private val showErrorResResult: (Int, Int) -> Unit = { _, _ -> lambdaError() }
) : AppErrorStateService {
    private val mutableAppErrorStateFlow = MutableStateFlow(initialState)
    override val appErrorStateFlow: StateFlow<AppErrorState> = mutableAppErrorStateFlow.asStateFlow()

    override fun showError(title: String, body: String) {
        showErrorResult(title, body)
    }

    override fun showError(titleRes: Int, bodyRes: Int) {
        showErrorResResult(titleRes, bodyRes)
    }

    fun setAppErrorState(state: AppErrorState) {
        mutableAppErrorStateFlow.value = state
    }
}
