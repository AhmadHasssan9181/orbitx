/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.core

import tech.orbitweb.orbitx.libraries.matrix.api.core.SendHandle
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeSendHandle(
    var retryLambda: () -> Result<Unit> = { Result.success(Unit) }
) : SendHandle {
    override suspend fun retry(): Result<Unit> = simulateLongTask {
        return retryLambda()
    }
}
