/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes

import org.matrix.rustcomponents.sdk.CheckCodeSender
import org.matrix.rustcomponents.sdk.NoHandle
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeFfiCheckCodeSender(
    private val sendResult: (UByte) -> Unit = { _ -> lambdaError() }
) : CheckCodeSender(NoHandle) {
    override suspend fun send(code: UByte) {
        sendResult(code)
    }
}
