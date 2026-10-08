/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.encryption

import tech.orbitweb.orbitx.libraries.matrix.api.encryption.RecoveryState
import org.matrix.rustcomponents.sdk.RecoveryState as RustRecoveryState

class RecoveryStateMapper {
    fun map(state: RustRecoveryState): RecoveryState {
        return when (state) {
            RustRecoveryState.UNKNOWN -> RecoveryState.UNKNOWN
            RustRecoveryState.ENABLED -> RecoveryState.ENABLED
            RustRecoveryState.DISABLED -> RecoveryState.DISABLED
            RustRecoveryState.INCOMPLETE -> RecoveryState.INCOMPLETE
        }
    }
}
