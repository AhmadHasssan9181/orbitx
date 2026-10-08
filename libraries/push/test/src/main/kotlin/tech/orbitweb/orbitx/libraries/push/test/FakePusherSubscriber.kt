/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.test

import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.pushproviders.api.PusherSubscriber
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakePusherSubscriber(
    private val registerPusherResult: (MatrixClient, String, String) -> Result<Unit> = { _, _, _ -> lambdaError() },
    private val unregisterPusherResult: (MatrixClient, String, String) -> Result<Unit> = { _, _, _ -> lambdaError() },
) : PusherSubscriber {
    override suspend fun registerPusher(matrixClient: MatrixClient, pushKey: String, gateway: String): Result<Unit> {
        return registerPusherResult(matrixClient, pushKey, gateway)
    }

    override suspend fun unregisterPusher(matrixClient: MatrixClient, pushKey: String, gateway: String): Result<Unit> {
        return unregisterPusherResult(matrixClient, pushKey, gateway)
    }
}
