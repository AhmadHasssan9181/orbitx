/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.pushers

import tech.orbitweb.orbitx.libraries.matrix.api.pusher.PushersService
import tech.orbitweb.orbitx.libraries.matrix.api.pusher.SetHttpPusherData
import tech.orbitweb.orbitx.libraries.matrix.api.pusher.UnsetHttpPusherData
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakePushersService(
    private val setHttpPusherResult: (SetHttpPusherData) -> Result<Unit> = { lambdaError() },
    private val unsetHttpPusherResult: (UnsetHttpPusherData) -> Result<Unit> = { lambdaError() },
) : PushersService {
    override suspend fun setHttpPusher(setHttpPusherData: SetHttpPusherData) = setHttpPusherResult(setHttpPusherData)
    override suspend fun unsetHttpPusher(unsetHttpPusherData: UnsetHttpPusherData): Result<Unit> = unsetHttpPusherResult(unsetHttpPusherData)
}
