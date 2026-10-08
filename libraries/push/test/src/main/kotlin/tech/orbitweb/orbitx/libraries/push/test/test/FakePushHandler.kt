/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.test.test

import tech.orbitweb.orbitx.libraries.pushproviders.api.PushData
import tech.orbitweb.orbitx.libraries.pushproviders.api.PushHandler
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakePushHandler(
    private val handleResult: (PushData, String) -> Boolean = { _, _ -> lambdaError() },
    private val handleInvalidResult: (String, String) -> Unit = { _, _ -> lambdaError() },
) : PushHandler {
    override suspend fun handle(pushData: PushData, providerInfo: String): Boolean {
        return handleResult(pushData, providerInfo)
    }

    override suspend fun handleInvalid(providerInfo: String, data: String) {
        handleInvalidResult(providerInfo, data)
    }
}
