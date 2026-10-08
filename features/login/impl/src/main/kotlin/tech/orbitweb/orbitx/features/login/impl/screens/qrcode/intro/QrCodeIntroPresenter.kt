/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.intro

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsEvent
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsPresenter

@Inject
class QrCodeIntroPresenter(
    private val buildMeta: BuildMeta,
    permissionsPresenterFactory: PermissionsPresenter.Factory,
) : Presenter<QrCodeIntroState> {
    private val cameraPermissionPresenter: PermissionsPresenter = permissionsPresenterFactory.create(Manifest.permission.CAMERA)
    private var pendingPermissionRequest by mutableStateOf(false)

    @Composable
    override fun present(): QrCodeIntroState {
        val cameraPermissionState = cameraPermissionPresenter.present()
        var canContinue by remember { mutableStateOf(false) }
        LaunchedEffect(cameraPermissionState.permissionGranted) {
            if (cameraPermissionState.permissionGranted && pendingPermissionRequest) {
                pendingPermissionRequest = false
                canContinue = true
            }
        }

        fun handleEvent(event: QrCodeIntroEvents) {
            when (event) {
                QrCodeIntroEvents.Continue -> if (cameraPermissionState.permissionGranted) {
                    canContinue = true
                } else {
                    pendingPermissionRequest = true
                    cameraPermissionState.eventSink(PermissionsEvent.RequestPermissions)
                }
            }
        }

        return QrCodeIntroState(
            appName = buildMeta.applicationName,
            desktopAppName = buildMeta.desktopApplicationName,
            cameraPermissionState = cameraPermissionState,
            canContinue = canContinue,
            eventSink = ::handleEvent,
        )
    }
}
