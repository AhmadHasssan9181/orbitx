/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.apperror.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.orbitweb.orbitx.services.apperror.api.AppErrorState
import tech.orbitweb.orbitx.services.apperror.api.AppErrorStateService
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class DefaultAppErrorStateService(
    private val stringProvider: StringProvider,
) : AppErrorStateService {
    private val currentAppErrorState = MutableStateFlow<AppErrorState>(AppErrorState.NoError)
    override val appErrorStateFlow: StateFlow<AppErrorState> = currentAppErrorState

    override fun showError(title: String, body: String) {
        currentAppErrorState.value = AppErrorState.Error(
            title = title,
            body = body,
            dismiss = {
                currentAppErrorState.value = AppErrorState.NoError
            },
        )
    }

    override fun showError(titleRes: Int, bodyRes: Int) {
        val title = stringProvider.getString(titleRes)
        val body = stringProvider.getString(bodyRes)
        showError(title, body)
    }
}
