/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.tasks

import android.content.Context
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.withContext
import tech.orbitweb.orbitx.libraries.androidutils.file.getSizeOfFiles
import tech.orbitweb.orbitx.libraries.androidutils.filesize.FileSizeFormatter
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient

interface ComputeCacheSizeUseCase {
    suspend operator fun invoke(): String
}

@ContributesBinding(SessionScope::class)
class DefaultComputeCacheSizeUseCase(
    @ApplicationContext private val context: Context,
    private val matrixClient: MatrixClient,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val fileSizeFormatter: FileSizeFormatter,
) : ComputeCacheSizeUseCase {
    override suspend fun invoke(): String = withContext(coroutineDispatchers.io) {
        var cumulativeSize = 0L
        cumulativeSize += matrixClient.getCacheSize()
        // - 4096 to not include the size fo the folder
        cumulativeSize += (context.cacheDir.getSizeOfFiles() - 4096).coerceAtLeast(0)
        fileSizeFormatter.format(cumulativeSize)
    }
}
