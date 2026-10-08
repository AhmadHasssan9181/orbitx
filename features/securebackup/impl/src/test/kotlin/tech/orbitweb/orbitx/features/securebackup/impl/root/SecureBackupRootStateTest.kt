/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl.root

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.BackupState
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EXCEPTION

class SecureBackupRootStateTest {
    @Test
    fun `isKeyStorageEnabled should be true for all these backup states`() {
        listOf(
            BackupState.CREATING,
            BackupState.ENABLING,
            BackupState.RESUMING,
            BackupState.DOWNLOADING,
            BackupState.ENABLED,
        ).forEach { backupState ->
            assertThat(aSecureBackupRootState(backupState = backupState).isKeyStorageEnabled).isTrue()
        }
    }

    @Test
    fun `isKeyStorageEnabled should be false for all these backup states`() {
        listOf(
            BackupState.WAITING_FOR_SYNC,
            BackupState.DISABLING,
        ).forEach { backupState ->
            assertThat(aSecureBackupRootState(backupState = backupState).isKeyStorageEnabled).isFalse()
        }
    }

    @Test
    fun `isKeyStorageEnabled should have value depending on doesBackupExistOnServer when state is UNKNOWN`() {
        assertThat(
            aSecureBackupRootState(
                backupState = BackupState.UNKNOWN,
                doesBackupExistOnServer = AsyncData.Success(true),
            ).isKeyStorageEnabled
        ).isTrue()

        listOf(
            AsyncData.Uninitialized,
            AsyncData.Loading(),
            AsyncData.Failure(AN_EXCEPTION),
            AsyncData.Success(false),
        ).forEach { doesBackupExistOnServer ->
            assertThat(
                aSecureBackupRootState(
                    backupState = BackupState.UNKNOWN,
                    doesBackupExistOnServer = doesBackupExistOnServer,
                ).isKeyStorageEnabled
            ).isFalse()
        }
    }
}
