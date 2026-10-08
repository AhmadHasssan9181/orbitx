/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.impl.notifications

import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsState

data class NotificationsOptInState(
    val notificationsPermissionState: PermissionsState,
    val eventSink: (NotificationsOptInEvents) -> Unit
)
