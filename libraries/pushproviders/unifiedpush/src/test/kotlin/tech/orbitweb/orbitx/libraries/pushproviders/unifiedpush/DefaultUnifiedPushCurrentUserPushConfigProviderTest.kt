/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.test.A_SECRET
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.pushproviders.api.Config
import tech.orbitweb.orbitx.libraries.pushstore.api.clientsecret.PushClientSecret
import tech.orbitweb.orbitx.libraries.pushstore.test.userpushstore.clientsecret.FakePushClientSecret
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultUnifiedPushCurrentUserPushConfigProviderTest {
    @Test
    fun `getCurrentUserPushConfig no push gateway`() = runTest {
        val sut = createDefaultUnifiedPushCurrentUserPushConfigProvider(
            pushClientSecret = FakePushClientSecret(
                getSecretForUserResult = { A_SECRET }
            ),
            unifiedPushStore = FakeUnifiedPushStore(
                getPushGatewayResult = { null }
            ),
        )
        val result = sut.provide(A_SESSION_ID)
        assertThat(result).isNull()
    }

    @Test
    fun `getCurrentUserPushConfig no push key`() = runTest {
        val sut = createDefaultUnifiedPushCurrentUserPushConfigProvider(
            pushClientSecret = FakePushClientSecret(
                getSecretForUserResult = { A_SECRET }
            ),
            unifiedPushStore = FakeUnifiedPushStore(
                getPushGatewayResult = { "aPushGateway" },
                getEndpointResult = { null }
            ),
        )
        val result = sut.provide(A_SESSION_ID)
        assertThat(result).isNull()
    }

    @Test
    fun `getCurrentUserPushConfig ok`() = runTest {
        val sut = createDefaultUnifiedPushCurrentUserPushConfigProvider(
            pushClientSecret = FakePushClientSecret(
                getSecretForUserResult = { A_SECRET }
            ),
            unifiedPushStore = FakeUnifiedPushStore(
                getPushGatewayResult = { "aPushGateway" },
                getEndpointResult = { "aEndpoint" }
            ),
        )
        val result = sut.provide(A_SESSION_ID)
        assertThat(result).isEqualTo(Config("aPushGateway", "aEndpoint"))
    }

    private fun createDefaultUnifiedPushCurrentUserPushConfigProvider(
        pushClientSecret: PushClientSecret = FakePushClientSecret(),
        unifiedPushStore: UnifiedPushStore = FakeUnifiedPushStore(),
    ): DefaultUnifiedPushPushConfigProvider {
        return DefaultUnifiedPushPushConfigProvider(
            pushClientSecret = pushClientSecret,
            unifiedPushStore = unifiedPushStore,
        )
    }
}
