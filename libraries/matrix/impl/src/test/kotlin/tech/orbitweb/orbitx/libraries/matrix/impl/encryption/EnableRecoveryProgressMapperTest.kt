/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.encryption

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.EnableRecoveryProgress
import org.matrix.rustcomponents.sdk.EnableRecoveryProgress as RustEnableRecoveryProgress

class EnableRecoveryProgressMapperTest {
    @Test
    fun `Ensure that mapping is correct`() {
        val sut = EnableRecoveryProgressMapper()
        assertThat(sut.map(RustEnableRecoveryProgress.CreatingRecoveryKey))
            .isEqualTo(EnableRecoveryProgress.CreatingRecoveryKey)
        assertThat(sut.map(RustEnableRecoveryProgress.CreatingBackup))
            .isEqualTo(EnableRecoveryProgress.CreatingBackup)
        assertThat(sut.map(RustEnableRecoveryProgress.Starting))
            .isEqualTo(EnableRecoveryProgress.Starting)
        assertThat(sut.map(RustEnableRecoveryProgress.BackingUp(1.toUInt(), 2.toUInt())))
            .isEqualTo(EnableRecoveryProgress.BackingUp(1, 2))
        assertThat(sut.map(RustEnableRecoveryProgress.RoomKeyUploadError))
            .isEqualTo(EnableRecoveryProgress.RoomKeyUploadError)
        assertThat(sut.map(RustEnableRecoveryProgress.Done("recoveryKey")))
            .isEqualTo(EnableRecoveryProgress.Done("recoveryKey"))
    }
}
