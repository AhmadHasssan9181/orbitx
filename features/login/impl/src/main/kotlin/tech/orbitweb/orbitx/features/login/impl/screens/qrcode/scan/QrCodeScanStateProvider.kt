/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.scan

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.login.impl.changeserver.AccountProviderAccessException
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.MatrixQrCodeLoginData
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.QrLoginException

open class QrCodeScanStateProvider : PreviewParameterProvider<QrCodeScanState> {
    override val values: Sequence<QrCodeScanState>
        get() = sequenceOf(
            aQrCodeScanState(),
            aQrCodeScanState(isScanning = false, authenticationAction = AsyncAction.Loading),
            aQrCodeScanState(isScanning = false, authenticationAction = AsyncAction.Failure(Exception("Error"))),
            aQrCodeScanState(isScanning = false, authenticationAction = AsyncAction.Failure(QrLoginException.OtherDeviceNotSignedIn)),
            aQrCodeScanState(
                isScanning = false,
                authenticationAction = AsyncAction.Failure(
                    AccountProviderAccessException.UnauthorizedAccountProviderException(
                        unauthorisedAccountProviderTitle = "example.com",
                        authorisedAccountProviderTitles = listOf("orbitweb.tech"),
                    )
                )
            ),
            aQrCodeScanState(
                isScanning = false,
                authenticationAction = AsyncAction.Failure(
                    AccountProviderAccessException.NeedElementProException(
                        unauthorisedAccountProviderTitle = "example.com",
                        applicationId = "applicationId"
                    )
                )
            ),
            // Add other state here
        )
}

fun aQrCodeScanState(
    isScanning: Boolean = true,
    authenticationAction: AsyncAction<MatrixQrCodeLoginData> = AsyncAction.Uninitialized,
    eventSink: (QrCodeScanEvents) -> Unit = {},
) = QrCodeScanState(
    isScanning = isScanning,
    authenticationAction = authenticationAction,
    eventSink = eventSink
)
