/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.ftue.impl.notifications

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.permissions.api.aPermissionsState

open class NotificationsOptInStateProvider : PreviewParameterProvider<NotificationsOptInState> {
    override val values: Sequence<NotificationsOptInState>
        get() = sequenceOf(
            aNotificationsOptInState(),
            // Add other states here
        )
}

fun aNotificationsOptInState() = NotificationsOptInState(
    notificationsPermissionState = aPermissionsState(showDialog = false),
    eventSink = {}
)
