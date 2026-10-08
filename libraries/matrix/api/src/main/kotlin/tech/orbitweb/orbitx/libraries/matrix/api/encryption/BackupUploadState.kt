/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.encryption

import androidx.compose.runtime.Immutable

@Immutable
sealed interface BackupUploadState {
    data object Unknown : BackupUploadState

    data object Waiting : BackupUploadState

    data class Uploading(
        val backedUpCount: Int,
        val totalCount: Int,
    ) : BackupUploadState

    data object Done : BackupUploadState

    data object Error : BackupUploadState

    data class SteadyException(val exception: SteadyStateException) : BackupUploadState
}
