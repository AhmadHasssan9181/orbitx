/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.test

import tech.orbitweb.orbitx.appconfig.PushConfig
import tech.orbitweb.orbitx.libraries.push.impl.pushgateway.PushGatewayNotifyRequest
import tech.orbitweb.orbitx.libraries.pushproviders.test.aSessionPushConfig
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultTestPushTest {
    @Test
    fun `test DefaultTestPush`() = runTest {
        val executeResult = lambdaRecorder<PushGatewayNotifyRequest.Params, Unit> { }
        val defaultTestPush = DefaultTestPush(
            pushGatewayNotifyRequest = FakePushGatewayNotifyRequest(
                executeResult = executeResult,
            )
        )
        val aConfig = aSessionPushConfig()
        defaultTestPush.execute(aConfig)
        executeResult.assertions()
            .isCalledOnce()
            .with(
                value(
                    PushGatewayNotifyRequest.Params(
                        url = aConfig.url,
                        appId = PushConfig.PUSHER_APP_ID,
                        pushKey = aConfig.pushKey,
                        eventId = DefaultTestPush.TEST_EVENT_ID,
                        roomId = DefaultTestPush.TEST_ROOM_ID,
                    )
                )
            )
    }
}
