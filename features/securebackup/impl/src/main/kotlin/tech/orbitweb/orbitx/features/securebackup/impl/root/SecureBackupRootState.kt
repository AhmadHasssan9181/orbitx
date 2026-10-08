/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl.root

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.BackupState
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.RecoveryState

data class SecureBackupRootState(
    val enableAction: AsyncAction<Unit>,
    val backupState: BackupState,
    val doesBackupExistOnServer: AsyncData<Boolean>,
    val recoveryState: RecoveryState,
    val appName: String,
    val displayKeyStorageDisabledError: Boolean,
    val snackbarMessage: SnackbarMessage?,
    val eventSink: (SecureBackupRootEvents) -> Unit,
) {
    val isKeyStorageEnabled: Boolean
        get() = when (backupState) {
            BackupState.UNKNOWN -> doesBackupExistOnServer.dataOrNull() == true
            BackupState.CREATING,
            BackupState.ENABLING,
            BackupState.RESUMING,
            BackupState.DOWNLOADING,
            BackupState.ENABLED -> true
            BackupState.WAITING_FOR_SYNC,
            BackupState.DISABLING -> false
        }
}
