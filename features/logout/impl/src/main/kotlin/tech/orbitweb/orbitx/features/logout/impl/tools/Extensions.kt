/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.logout.impl.tools

import tech.orbitweb.orbitx.libraries.matrix.api.encryption.BackupUploadState
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.SteadyStateException

internal fun BackupUploadState.isBackingUp(): Boolean {
    return when (this) {
        BackupUploadState.Waiting,
        is BackupUploadState.Uploading -> true
        // The backup is in progress, but there have been a network issue, so we have to warn the user.
        is BackupUploadState.SteadyException -> exception is SteadyStateException.Connection
        BackupUploadState.Unknown,
        BackupUploadState.Done,
        BackupUploadState.Error -> false
    }
}
