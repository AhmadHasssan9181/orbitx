/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth.qrlogin

import org.matrix.rustcomponents.sdk.QrLoginProgress
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.QrCodeLoginStep

fun QrLoginProgress.toStep(): QrCodeLoginStep {
    return when (this) {
        is QrLoginProgress.EstablishingSecureChannel -> QrCodeLoginStep.EstablishingSecureChannel(checkCodeString)
        is QrLoginProgress.Starting -> QrCodeLoginStep.Starting
        is QrLoginProgress.WaitingForToken -> QrCodeLoginStep.WaitingForToken(userCode)
        is QrLoginProgress.SyncingSecrets -> QrCodeLoginStep.SyncingSecrets
        is QrLoginProgress.Done -> QrCodeLoginStep.Finished
    }
}
