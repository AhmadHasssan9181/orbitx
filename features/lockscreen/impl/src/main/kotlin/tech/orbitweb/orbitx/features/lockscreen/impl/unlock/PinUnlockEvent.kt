/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.unlock

import tech.orbitweb.orbitx.features.lockscreen.impl.unlock.keypad.PinKeypadModel

sealed interface PinUnlockEvent {
    data class OnPinKeypadPressed(val pinKeypadModel: PinKeypadModel) : PinUnlockEvent
    data class OnPinEntryChanged(val entryAsText: String) : PinUnlockEvent
    data object OnForgetPin : PinUnlockEvent
    data object ClearSignOutPrompt : PinUnlockEvent
    data object SignOut : PinUnlockEvent
    data object OnUseBiometric : PinUnlockEvent
    data object ClearBiometricError : PinUnlockEvent
}
