/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaupload.impl

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.mediaupload.api.MaxUploadSizeProvider

/**
 * Provides the maximum upload size allowed by the Matrix server.
 */
@ContributesBinding(SessionScope::class)
class DefaultMaxUploadSizeProvider(
    private val matrixClient: MatrixClient,
) : MaxUploadSizeProvider {
    override suspend fun getMaxUploadSize(): Result<Long> {
        return matrixClient.getMaxFileUploadSize()
    }
}
