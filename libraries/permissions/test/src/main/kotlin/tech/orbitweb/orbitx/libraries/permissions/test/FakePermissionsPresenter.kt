/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.permissions.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsEvent
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsPresenter
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsState
import tech.orbitweb.orbitx.libraries.permissions.api.aPermissionsState

class FakePermissionsPresenter(
    private val initialState: PermissionsState = aPermissionsState(showDialog = false),
) : PermissionsPresenter {
    private fun handleEvent(event: PermissionsEvent) {
        when (event) {
            PermissionsEvent.RequestPermissions -> state.value = state.value.copy(showDialog = true, permissionAlreadyAsked = true)
            PermissionsEvent.CloseDialog -> state.value = state.value.copy(showDialog = false)
            PermissionsEvent.OpenSystemSettingAndCloseDialog -> state.value = state.value.copy(showDialog = false)
        }
    }

    private val state = mutableStateOf(initialState.copy(eventSink = ::handleEvent))

    fun setPermissionGranted() {
        state.value = state.value.copy(permissionGranted = true)
    }

    fun setPermissionDenied() {
        state.value = state.value.copy(permissionGranted = false, permissionAlreadyDenied = true)
    }

    @Composable
    override fun present(): PermissionsState {
        return state.value
    }
}
