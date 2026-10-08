/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.media

import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaUploadHandler
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask
import kotlin.coroutines.cancellation.CancellationException

class FakeMediaUploadHandler(
    private var result: Result<Unit> = Result.success(Unit),
) : MediaUploadHandler {
    override suspend fun await(): Result<Unit> = simulateLongTask { result }

    override fun cancel() {
        result = Result.failure(CancellationException())
    }
}
