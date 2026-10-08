/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.settings

data class LockScreenSettingsState(
    val showRemovePinOption: Boolean,
    val isBiometricEnabled: Boolean,
    val showRemovePinConfirmation: Boolean,
    val showToggleBiometric: Boolean,
    val eventSink: (LockScreenSettingsEvent) -> Unit
)
