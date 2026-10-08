/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.settings

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

open class LockScreenSettingsStateProvider : PreviewParameterProvider<LockScreenSettingsState> {
    override val values: Sequence<LockScreenSettingsState>
        get() = sequenceOf(
            aLockScreenSettingsState(),
            aLockScreenSettingsState(isLockMandatory = true),
            aLockScreenSettingsState(showRemovePinConfirmation = true),
        )
}

fun aLockScreenSettingsState(
    isLockMandatory: Boolean = false,
    isBiometricEnabled: Boolean = false,
    showRemovePinConfirmation: Boolean = false,
    showToggleBiometric: Boolean = true,
) = LockScreenSettingsState(
    showRemovePinOption = isLockMandatory,
    isBiometricEnabled = isBiometricEnabled,
    showRemovePinConfirmation = showRemovePinConfirmation,
    showToggleBiometric = showToggleBiometric,
    eventSink = {}
)
