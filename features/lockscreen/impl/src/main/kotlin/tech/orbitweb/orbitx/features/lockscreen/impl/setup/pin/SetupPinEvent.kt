/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl.setup.pin

sealed interface SetupPinEvent {
    data class OnPinEntryChanged(val entryAsText: String, val fromConfirmationStep: Boolean) : SetupPinEvent
    data object ClearFailure : SetupPinEvent
}
