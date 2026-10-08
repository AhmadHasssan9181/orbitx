/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.pushers

import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.pusher.SetHttpPusherData
import tech.orbitweb.orbitx.libraries.matrix.api.pusher.UnsetHttpPusherData
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiClient
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers

class RustPushersServiceTest {
    @Test
    fun `setPusher should invoke the client method`() = runTest {
        val sut = RustPushersService(
            client = FakeFfiClient(),
            dispatchers = testCoroutineDispatchers()
        )
        sut.setHttpPusher(
            setHttpPusherData = aSetHttpPusherData()
        ).getOrThrow()
    }

    @Test
    fun `unsetPusher should invoke the client method`() = runTest {
        val sut = RustPushersService(
            client = FakeFfiClient(),
            dispatchers = testCoroutineDispatchers()
        )
        sut.unsetHttpPusher(
            unsetHttpPusherData = aUnsetHttpPusherData(),
        ).getOrThrow()
    }
}

private fun aSetHttpPusherData(
    pushKey: String = "pushKey",
    appId: String = "appId",
    url: String = "url",
    defaultPayload: String = "defaultPayload",
    appDisplayName: String = "appDisplayName",
    deviceDisplayName: String = "deviceDisplayName",
    profileTag: String = "profileTag",
    lang: String = "lang",
    append: Boolean = false,
) = SetHttpPusherData(
    pushKey = pushKey,
    appId = appId,
    url = url,
    defaultPayload = defaultPayload,
    appDisplayName = appDisplayName,
    deviceDisplayName = deviceDisplayName,
    profileTag = profileTag,
    lang = lang,
    append = append,
)

private fun aUnsetHttpPusherData(
    pushKey: String = "pushKey",
    appId: String = "appId",
) = UnsetHttpPusherData(
    pushKey = pushKey,
    appId = appId,
)
