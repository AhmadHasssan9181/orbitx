/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.confirmation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class QrCodeConfirmationStepProvider : PreviewParameterProvider<QrCodeConfirmationStep> {
    override val values: Sequence<QrCodeConfirmationStep>
        get() = sequenceOf(
            QrCodeConfirmationStep.DisplayCheckCode("12"),
            QrCodeConfirmationStep.DisplayVerificationCode("123456"),
            QrCodeConfirmationStep.DisplayVerificationCode("123456789"),
        )
}
