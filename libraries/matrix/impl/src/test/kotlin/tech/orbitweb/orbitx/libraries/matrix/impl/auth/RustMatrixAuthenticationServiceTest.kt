/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.auth

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.features.enterprise.test.FakeEnterpriseService
import tech.orbitweb.orbitx.libraries.matrix.impl.ClientBuilderProvider
import tech.orbitweb.orbitx.libraries.matrix.impl.FakeClientBuilderProvider
import tech.orbitweb.orbitx.libraries.matrix.impl.createRustMatrixClientFactory
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiClient
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiClientBuilder
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiHomeserverLoginDetails
import tech.orbitweb.orbitx.libraries.matrix.impl.paths.SessionPathsFactory
import tech.orbitweb.orbitx.libraries.matrix.test.auth.FakeOAuthRedirectUrlProvider
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import tech.orbitweb.orbitx.libraries.sessionstorage.api.SessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.test.InMemorySessionStore
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers
import java.io.File

class RustMatrixAuthenticationServiceTest {
    @Test
    fun `setHomeserver is successful`() = runTest {
        val sut = createRustMatrixAuthenticationService(
            clientBuilderProvider = FakeClientBuilderProvider(
                provideResult = {
                    FakeFfiClientBuilder(
                        buildResult = {
                            FakeFfiClient(
                                homeserverLoginDetailsResult = {
                                    FakeFfiHomeserverLoginDetails()
                                }
                            )
                        }
                    )
                }
            ),
        )
        assertThat(sut.setHomeserver("matrix.org").isSuccess).isTrue()
    }

    private fun TestScope.createRustMatrixAuthenticationService(
        sessionStore: SessionStore = InMemorySessionStore(),
        clientBuilderProvider: ClientBuilderProvider = FakeClientBuilderProvider(),
        enterpriseService: EnterpriseService = FakeEnterpriseService(),
    ): RustMatrixAuthenticationService {
        val baseDirectory = File("/base")
        val cacheDirectory = File("/cache")
        val rustMatrixClientFactory = createRustMatrixClientFactory(
            cacheDirectory = cacheDirectory,
            sessionStore = sessionStore,
            clientBuilderProvider = clientBuilderProvider,
        )
        return RustMatrixAuthenticationService(
            sessionPathsFactory = SessionPathsFactory(baseDirectory, cacheDirectory),
            coroutineDispatchers = testCoroutineDispatchers(),
            sessionStore = sessionStore,
            rustMatrixClientFactory = rustMatrixClientFactory,
            secretGenerator = FakeSecretGenerator(),
            oAuthConfigurationProvider = OAuthConfigurationProvider(
                buildMeta = aBuildMeta(),
                oAuthRedirectUrlProvider = FakeOAuthRedirectUrlProvider(),
            ),
            enterpriseService = enterpriseService,
        )
    }
}
