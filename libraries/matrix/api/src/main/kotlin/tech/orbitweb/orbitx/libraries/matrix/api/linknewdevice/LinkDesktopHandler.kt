/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice

import kotlinx.coroutines.flow.StateFlow
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.QrCodeDecodeException

interface LinkDesktopHandler {
    val linkDesktopStep: StateFlow<LinkDesktopStep>
    suspend fun handleScannedQrCode(data: ByteArray)
}

sealed interface LinkDesktopStep {
    data object Uninitialized : LinkDesktopStep
    data object Starting : LinkDesktopStep
    data class WaitingForAuth(
        val verificationUri: String,
    ) : LinkDesktopStep

    data class EstablishingSecureChannel(
        val checkCode: UByte,
        val checkCodeString: String,
    ) : LinkDesktopStep

    data class InvalidQrCode(
        val error: QrCodeDecodeException,
    ) : LinkDesktopStep

    data class Error(
        val errorType: ErrorType,
    ) : LinkDesktopStep

    data object SyncingSecrets : LinkDesktopStep

    data object Done : LinkDesktopStep
}
