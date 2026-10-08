/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalPermissionsApi::class)

package tech.orbitweb.orbitx.libraries.permissions.impl

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

interface ComposablePermissionStateProvider {
    @Composable
    fun provide(permission: String, onPermissionResult: (Boolean) -> Unit): PermissionState
}

@ContributesBinding(AppScope::class)
class AccompanistPermissionStateProvider : ComposablePermissionStateProvider {
    @Composable
    override fun provide(permission: String, onPermissionResult: (Boolean) -> Unit): PermissionState {
        return rememberPermissionState(
            permission = permission,
            onPermissionResult = onPermissionResult
        )
    }
}
