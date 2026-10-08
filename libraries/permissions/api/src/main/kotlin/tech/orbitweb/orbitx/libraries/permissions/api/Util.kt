/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.permissions.api

fun createDummyPostNotificationPermissionsState() = PermissionsState(
    permission = "Manifest.permission.POST_NOTIFICATIONS",
    permissionGranted = true,
    shouldShowRationale = false,
    showDialog = false,
    permissionAlreadyAsked = false,
    permissionAlreadyDenied = false,
    eventSink = { }
)
