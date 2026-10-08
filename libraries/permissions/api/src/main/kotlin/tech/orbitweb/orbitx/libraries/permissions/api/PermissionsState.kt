/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.permissions.api

data class PermissionsState(
    // For instance Manifest.permission.POST_NOTIFICATIONS
    val permission: String,
    val permissionGranted: Boolean,
    val shouldShowRationale: Boolean,
    val showDialog: Boolean,
    val permissionAlreadyAsked: Boolean,
    // If true, there is no need to ask again, the system dialog will not be displayed
    val permissionAlreadyDenied: Boolean,
    val eventSink: (PermissionsEvent) -> Unit
)
