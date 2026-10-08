/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.setup.pin

import tech.orbitweb.orbitx.features.lockscreen.impl.pin.model.PinEntry
import tech.orbitweb.orbitx.features.lockscreen.impl.setup.pin.validation.SetupPinFailure

data class SetupPinState(
    val choosePinEntry: PinEntry,
    val confirmPinEntry: PinEntry,
    val isConfirmationStep: Boolean,
    val setupPinFailure: SetupPinFailure?,
    val appName: String,
    val eventSink: (SetupPinEvent) -> Unit
) {
    val activePinEntry = if (isConfirmationStep) {
        confirmPinEntry
    } else {
        choosePinEntry
    }
}
