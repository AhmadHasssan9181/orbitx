/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl.disable

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.BackupState

data class SecureBackupDisableState(
    val backupState: BackupState,
    val disableAction: AsyncAction<Unit>,
    val appName: String,
    val eventSink: (SecureBackupDisableEvents) -> Unit
)
