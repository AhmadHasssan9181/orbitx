/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.permissions.impl

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionStateProvider
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsStore

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultPermissionStateProvider(
    @ApplicationContext private val context: Context,
    private val permissionsStore: PermissionsStore,
) : PermissionStateProvider {
    override fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }

    override suspend fun setPermissionDenied(permission: String, value: Boolean) = permissionsStore.setPermissionDenied(permission, value)

    override fun isPermissionDenied(permission: String): Flow<Boolean> = permissionsStore.isPermissionDenied(permission)

    override suspend fun setPermissionAsked(permission: String, value: Boolean) = permissionsStore.setPermissionAsked(permission, value)

    override fun isPermissionAsked(permission: String): Flow<Boolean> = permissionsStore.isPermissionAsked(permission)
}
