/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.permissions.noop

import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsPresenter
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsState

class NoopPermissionsPresenter(
    private val isGranted: Boolean = false,
) : PermissionsPresenter {
    @Composable
    override fun present(): PermissionsState {
        return PermissionsState(
            permission = "",
            permissionGranted = isGranted,
            shouldShowRationale = false,
            showDialog = false,
            permissionAlreadyAsked = false,
            permissionAlreadyDenied = false,
            eventSink = {},
        )
    }
}
