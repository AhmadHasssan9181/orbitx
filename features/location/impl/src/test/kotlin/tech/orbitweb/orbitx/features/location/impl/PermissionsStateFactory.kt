/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl

import tech.orbitweb.orbitx.features.location.impl.common.permissions.PermissionsState

fun aPermissionsState(
    permissions: PermissionsState.Permissions = PermissionsState.Permissions.NoneGranted,
    shouldShowRationale: Boolean = false,
    permissionsRequested: Boolean = false,
): PermissionsState {
    return PermissionsState(
        permissions = permissions,
        shouldShowRationale = shouldShowRationale,
        permissionsAlreadyRequested = permissionsRequested,
        eventSink = {},
    )
}
