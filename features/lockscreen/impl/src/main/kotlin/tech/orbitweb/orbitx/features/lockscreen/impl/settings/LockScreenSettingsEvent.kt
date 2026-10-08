/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.settings

sealed interface LockScreenSettingsEvent {
    data object OnRemovePin : LockScreenSettingsEvent
    data object ConfirmRemovePin : LockScreenSettingsEvent
    data object CancelRemovePin : LockScreenSettingsEvent
    data object ToggleBiometricAllowed : LockScreenSettingsEvent
}
