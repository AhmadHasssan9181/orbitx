/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.desktop

import android.Manifest
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsState
import tech.orbitweb.orbitx.libraries.permissions.api.aPermissionsState

open class DesktopNoticeStateProvider : PreviewParameterProvider<DesktopNoticeState> {
    override val values: Sequence<DesktopNoticeState>
        get() = sequenceOf(
            aDesktopNoticeState(),
            aDesktopNoticeState(cameraPermissionState = aPermissionsState(showDialog = true, permission = Manifest.permission.CAMERA)),
        )
}

fun aDesktopNoticeState(
    cameraPermissionState: PermissionsState = aPermissionsState(
        showDialog = false,
        permission = Manifest.permission.CAMERA,
    ),
    canContinue: Boolean = false,
    eventSink: (DesktopNoticeEvent) -> Unit = {},
) = DesktopNoticeState(
    cameraPermissionState = cameraPermissionState,
    canContinue = canContinue,
    eventSink = eventSink
)
