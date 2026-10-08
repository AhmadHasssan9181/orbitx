/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.logout.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.BackupState
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.BackupUploadState
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.RecoveryState
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.SteadyStateException

open class LogoutStateProvider : PreviewParameterProvider<LogoutState> {
    override val values: Sequence<LogoutState>
        get() = sequenceOf(
            aLogoutState(),
            aLogoutState(isLastDevice = true),
            aLogoutState(isLastDevice = false, backupUploadState = BackupUploadState.Uploading(66, 200)),
            aLogoutState(isLastDevice = true, backupUploadState = BackupUploadState.Done),
            aLogoutState(logoutAction = AsyncAction.ConfirmingNoParams),
            aLogoutState(logoutAction = AsyncAction.Loading),
            aLogoutState(logoutAction = AsyncAction.Failure(Exception("Failed to logout"))),
            aLogoutState(backupUploadState = BackupUploadState.SteadyException(SteadyStateException.Connection("No network"))),
            // Last session no recovery
            aLogoutState(isLastDevice = true, recoveryState = RecoveryState.DISABLED),
            // Last session no backup
            aLogoutState(isLastDevice = true, backupState = BackupState.UNKNOWN, doesBackupExistOnServer = false),
            aLogoutState(
                isLastDevice = false,
                backupUploadState = BackupUploadState.Waiting,
            ),
            aLogoutState(
                isLastDevice = false,
                backupUploadState = BackupUploadState.Waiting,
                waitingForALongTime = true,
            ),
        )
}

fun aLogoutState(
    isLastDevice: Boolean = false,
    backupState: BackupState = BackupState.ENABLED,
    doesBackupExistOnServer: Boolean = true,
    recoveryState: RecoveryState = RecoveryState.ENABLED,
    backupUploadState: BackupUploadState = BackupUploadState.Unknown,
    waitingForALongTime: Boolean = false,
    logoutAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    eventSink: (LogoutEvents) -> Unit = {},
) = LogoutState(
    isLastDevice = isLastDevice,
    backupState = backupState,
    doesBackupExistOnServer = doesBackupExistOnServer,
    recoveryState = recoveryState,
    backupUploadState = backupUploadState,
    waitingForALongTime = waitingForALongTime,
    logoutAction = logoutAction,
    eventSink = eventSink,
)
