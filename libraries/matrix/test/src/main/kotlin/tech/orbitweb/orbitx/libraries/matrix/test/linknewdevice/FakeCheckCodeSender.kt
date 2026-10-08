/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.linknewdevice

import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.CheckCodeSender
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeCheckCodeSender(
    private val validateResult: (UByte) -> Boolean = { lambdaError() },
    private val sendResult: (UByte) -> Result<Unit> = { lambdaError() },
) : CheckCodeSender {
    override suspend fun validate(code: UByte): Boolean = simulateLongTask {
        validateResult(code)
    }

    override suspend fun send(code: UByte): Result<Unit> = simulateLongTask {
        sendResult(code)
    }
}
