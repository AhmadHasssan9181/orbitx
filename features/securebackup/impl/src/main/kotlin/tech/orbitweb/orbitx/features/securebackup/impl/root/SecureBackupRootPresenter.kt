/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.securebackup.impl.loggerTagDisable
import tech.orbitweb.orbitx.features.securebackup.impl.loggerTagRoot
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.architecture.runCatchingUpdatingState
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarDispatcher
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.collectSnackbarMessageAsState
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.BackupState
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.EncryptionService
import timber.log.Timber

@Inject
class SecureBackupRootPresenter(
    private val encryptionService: EncryptionService,
    private val buildMeta: BuildMeta,
    private val snackbarDispatcher: SnackbarDispatcher,
) : Presenter<SecureBackupRootState> {
    @Composable
    override fun present(): SecureBackupRootState {
        val localCoroutineScope = rememberCoroutineScope()
        val snackbarMessage by snackbarDispatcher.collectSnackbarMessageAsState()

        val backupState by encryptionService.backupStateStateFlow.collectAsState()
        val recoveryState by encryptionService.recoveryStateStateFlow.collectAsState()
        val enableAction: MutableState<AsyncAction<Unit>> = remember { mutableStateOf(AsyncAction.Uninitialized) }
        var displayKeyStorageDisabledError by remember { mutableStateOf(false) }
        Timber.tag(loggerTagRoot.value).d("backupState: $backupState")
        Timber.tag(loggerTagRoot.value).d("recoveryState: $recoveryState")

        val doesBackupExistOnServerAction: MutableState<AsyncData<Boolean>> = remember { mutableStateOf(AsyncData.Uninitialized) }

        LaunchedEffect(backupState) {
            if (backupState == BackupState.UNKNOWN) {
                getKeyBackupStatus(doesBackupExistOnServerAction)
            }
        }

        fun handleEvent(event: SecureBackupRootEvents) {
            when (event) {
                SecureBackupRootEvents.RetryKeyBackupState -> localCoroutineScope.getKeyBackupStatus(doesBackupExistOnServerAction)
                SecureBackupRootEvents.EnableKeyStorage -> localCoroutineScope.enableBackup(enableAction)
                SecureBackupRootEvents.DismissDialog -> {
                    enableAction.value = AsyncAction.Uninitialized
                    displayKeyStorageDisabledError = false
                }
                SecureBackupRootEvents.DisplayKeyStorageDisabledError -> displayKeyStorageDisabledError = true
            }
        }

        return SecureBackupRootState(
            enableAction = enableAction.value,
            backupState = backupState,
            doesBackupExistOnServer = doesBackupExistOnServerAction.value,
            recoveryState = recoveryState,
            appName = buildMeta.applicationName,
            displayKeyStorageDisabledError = displayKeyStorageDisabledError,
            snackbarMessage = snackbarMessage,
            eventSink = ::handleEvent,
        )
    }

    private fun CoroutineScope.getKeyBackupStatus(action: MutableState<AsyncData<Boolean>>) = launch {
        suspend {
            encryptionService.doesBackupExistOnServer().getOrThrow()
        }.runCatchingUpdatingState(action)
    }

    private fun CoroutineScope.enableBackup(action: MutableState<AsyncAction<Unit>>) = launch {
        suspend {
            Timber.tag(loggerTagDisable.value).d("Calling encryptionService.enableBackups()")
            encryptionService.enableBackups().getOrThrow()
        }.runCatchingUpdatingState(action)
    }
}
