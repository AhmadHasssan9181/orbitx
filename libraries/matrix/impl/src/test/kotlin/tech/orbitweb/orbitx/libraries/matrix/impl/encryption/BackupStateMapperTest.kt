/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.encryption

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.BackupState
import org.junit.Test
import org.matrix.rustcomponents.sdk.BackupState as RustBackupState

class BackupStateMapperTest {
    @Test
    fun `Ensure that mapping is correct`() {
        val sut = BackupStateMapper()
        assertThat(sut.map(RustBackupState.UNKNOWN)).isEqualTo(BackupState.UNKNOWN)
        assertThat(sut.map(RustBackupState.CREATING)).isEqualTo(BackupState.CREATING)
        assertThat(sut.map(RustBackupState.ENABLING)).isEqualTo(BackupState.ENABLING)
        assertThat(sut.map(RustBackupState.RESUMING)).isEqualTo(BackupState.RESUMING)
        assertThat(sut.map(RustBackupState.ENABLED)).isEqualTo(BackupState.ENABLED)
        assertThat(sut.map(RustBackupState.DOWNLOADING)).isEqualTo(BackupState.DOWNLOADING)
        assertThat(sut.map(RustBackupState.DISABLING)).isEqualTo(BackupState.DISABLING)
    }
}
