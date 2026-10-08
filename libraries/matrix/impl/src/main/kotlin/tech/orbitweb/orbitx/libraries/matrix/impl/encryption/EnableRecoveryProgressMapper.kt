/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.encryption

import tech.orbitweb.orbitx.libraries.matrix.api.encryption.EnableRecoveryProgress
import org.matrix.rustcomponents.sdk.EnableRecoveryProgress as RustEnableRecoveryProgress

class EnableRecoveryProgressMapper {
    fun map(rustEnableProgress: RustEnableRecoveryProgress): EnableRecoveryProgress {
        return when (rustEnableProgress) {
            is RustEnableRecoveryProgress.Starting -> EnableRecoveryProgress.Starting
            is RustEnableRecoveryProgress.CreatingBackup -> EnableRecoveryProgress.CreatingBackup
            is RustEnableRecoveryProgress.CreatingRecoveryKey -> EnableRecoveryProgress.CreatingRecoveryKey
            is RustEnableRecoveryProgress.BackingUp -> EnableRecoveryProgress.BackingUp(
                backedUpCount = rustEnableProgress.backedUpCount.toInt(),
                totalCount = rustEnableProgress.totalCount.toInt(),
            )
            is RustEnableRecoveryProgress.RoomKeyUploadError -> EnableRecoveryProgress.RoomKeyUploadError
            is RustEnableRecoveryProgress.Done -> EnableRecoveryProgress.Done(
                recoveryKey = rustEnableProgress.recoveryKey
            )
        }
    }
}
