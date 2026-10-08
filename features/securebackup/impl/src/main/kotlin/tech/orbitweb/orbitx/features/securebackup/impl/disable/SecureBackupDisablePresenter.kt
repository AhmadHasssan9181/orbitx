/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl.disable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.securebackup.impl.loggerTagDisable
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.architecture.runCatchingUpdatingState
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.EncryptionService
import timber.log.Timber

@Inject
class SecureBackupDisablePresenter(
    private val encryptionService: EncryptionService,
    private val buildMeta: BuildMeta,
) : Presenter<SecureBackupDisableState> {
    @Composable
    override fun present(): SecureBackupDisableState {
        val backupState by encryptionService.backupStateStateFlow.collectAsState()
        Timber.tag(loggerTagDisable.value).d("backupState: $backupState")
        val disableAction: MutableState<AsyncAction<Unit>> = remember { mutableStateOf(AsyncAction.Uninitialized) }
        val coroutineScope = rememberCoroutineScope()
        fun handleEvent(event: SecureBackupDisableEvents) {
            when (event) {
                is SecureBackupDisableEvents.DisableBackup -> coroutineScope.disableBackup(disableAction)
                SecureBackupDisableEvents.DismissDialogs -> {
                    disableAction.value = AsyncAction.Uninitialized
                }
            }
        }

        return SecureBackupDisableState(
            backupState = backupState,
            disableAction = disableAction.value,
            appName = buildMeta.applicationName,
            eventSink = ::handleEvent,
        )
    }

    private fun CoroutineScope.disableBackup(disableAction: MutableState<AsyncAction<Unit>>) = launch {
        suspend {
            Timber.tag(loggerTagDisable.value).d("Calling encryptionService.disableRecovery()")
            encryptionService.disableRecovery().getOrThrow()
        }.runCatchingUpdatingState(disableAction)
    }
}
