/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.test

import androidx.compose.runtime.Composable
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMedia
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.local.LocalMediaActions
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeLocalMediaActions(
    val configureResult: () -> Unit = { },
    val saveOnDiskResult: (LocalMedia) -> Result<Unit> = { lambdaError() },
    val shareResult: (LocalMedia) -> Result<Unit> = { lambdaError() },
    val openResult: (LocalMedia) -> Result<Unit> = { lambdaError() },
) : LocalMediaActions {
    @Composable
    override fun Configure() {
        configureResult()
    }

    override suspend fun saveOnDisk(localMedia: LocalMedia): Result<Unit> = simulateLongTask {
        saveOnDiskResult(localMedia)
    }

    override suspend fun share(localMedia: LocalMedia): Result<Unit> = simulateLongTask {
        shareResult(localMedia)
    }

    override suspend fun open(localMedia: LocalMedia): Result<Unit> = simulateLongTask {
        openResult(localMedia)
    }
}
