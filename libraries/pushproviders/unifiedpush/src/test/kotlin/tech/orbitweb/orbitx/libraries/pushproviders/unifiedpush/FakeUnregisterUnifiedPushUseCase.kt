/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeUnregisterUnifiedPushUseCase(
    private val unregisterLambda: (MatrixClient, String, Boolean) -> Result<Unit> = { _, _, _ -> lambdaError() },
    private val cleanupLambda: (String, Boolean) -> Unit = { _, _ -> lambdaError() },
) : UnregisterUnifiedPushUseCase {
    override suspend fun unregister(
        matrixClient: MatrixClient,
        clientSecret: String,
        unregisterUnifiedPush: Boolean,
    ): Result<Unit> {
        return unregisterLambda(matrixClient, clientSecret, unregisterUnifiedPush)
    }

    override fun cleanup(
        clientSecret: String,
        unregisterUnifiedPush: Boolean,
    ) {
        cleanupLambda(clientSecret, unregisterUnifiedPush)
    }
}
