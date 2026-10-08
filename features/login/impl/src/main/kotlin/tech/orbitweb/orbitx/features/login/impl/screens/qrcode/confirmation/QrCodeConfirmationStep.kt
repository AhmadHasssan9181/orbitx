/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.confirmation

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs

@Immutable
sealed interface QrCodeConfirmationStep : NodeInputs, Parcelable {
    @Parcelize
    data class DisplayCheckCode(val code: String) : QrCodeConfirmationStep

    @Parcelize
    data class DisplayVerificationCode(val code: String) : QrCodeConfirmationStep
}
