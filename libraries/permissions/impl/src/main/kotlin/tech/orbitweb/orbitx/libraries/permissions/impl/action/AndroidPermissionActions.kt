/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.permissions.impl.action

import android.Manifest
import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.androidutils.system.openAppSettingsPage
import tech.orbitweb.orbitx.libraries.androidutils.system.startNotificationSettingsIntent
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext

@ContributesBinding(AppScope::class)
class AndroidPermissionActions(
    @ApplicationContext private val context: Context
) : PermissionActions {
    override fun openSettings(permission: String) {
        when (permission) {
            Manifest.permission.POST_NOTIFICATIONS -> context.startNotificationSettingsIntent()
            else -> context.openAppSettingsPage()
        }
    }
}
