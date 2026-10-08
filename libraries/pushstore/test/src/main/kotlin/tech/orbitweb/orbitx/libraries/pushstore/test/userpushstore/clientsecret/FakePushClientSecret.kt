/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushstore.test.userpushstore.clientsecret

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret.PushClientSecret
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakePushClientSecret(
    private val getSecretForUserResult: (SessionId) -> String = { lambdaError() },
    private val getUserIdFromSecretResult: (String) -> SessionId? = { lambdaError() }
) : PushClientSecret {
    override suspend fun getSecretForUser(userId: SessionId): String {
        return getSecretForUserResult(userId)
    }

    override suspend fun getUserIdFromSecret(clientSecret: String): SessionId? {
        return getUserIdFromSecretResult(clientSecret)
    }
}
