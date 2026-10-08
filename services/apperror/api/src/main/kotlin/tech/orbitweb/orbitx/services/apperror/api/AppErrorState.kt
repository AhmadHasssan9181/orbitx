/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.apperror.api

import androidx.compose.runtime.Immutable

@Immutable
sealed interface AppErrorState {
    data object NoError : AppErrorState

    data class Error(
        val title: String,
        val body: String,
        val dismiss: () -> Unit,
    ) : AppErrorState
}
