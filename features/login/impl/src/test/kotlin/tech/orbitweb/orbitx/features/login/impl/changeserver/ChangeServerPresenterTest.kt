/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.changeserver

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.enterprise.api.EnterpriseService
import tech.orbitweb.orbitx.features.enterprise.test.FakeEnterpriseService
import tech.orbitweb.orbitx.features.login.impl.accesscontrol.DefaultAccountProviderAccessControl
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProvider
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProviderDataSource
import tech.orbitweb.orbitx.features.login.impl.error.ChangeServerError
import tech.orbitweb.orbitx.features.wellknown.test.FakeWellknownRetriever
import tech.orbitweb.orbitx.features.wellknown.test.anElementWellKnown
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.core.uri.ensureProtocol
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EXCEPTION
import tech.orbitweb.orbitx.libraries.matrix.test.A_HOMESERVER_URL
import tech.orbitweb.orbitx.libraries.matrix.test.auth.FakeMatrixAuthenticationService
import tech.orbitweb.orbitx.libraries.matrix.test.auth.aMatrixHomeServerDetails
import tech.orbitweb.orbitx.libraries.wellknown.api.ElementWellKnown
import tech.orbitweb.orbitx.libraries.wellknown.api.WellknownRetriever
import tech.orbitweb.orbitx.libraries.wellknown.api.WellknownRetrieverResult
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import tech.orbitweb.orbitx.tests.testutils.test
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ChangeServerPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state`() = runTest {
        createPresenter().test {
            val initialState = awaitItem()
            assertThat(initialState.changeServerAction).isEqualTo(AsyncData.Uninitialized)
        }
    }

    @Test
    fun `present - change server ok`() = runTest {
        val authenticationService = FakeMatrixAuthenticationService(
            setHomeserverResult = {
                Result.success(aMatrixHomeServerDetails(supportsOAuthLogin = true))
            },
        )
        createPresenter(
            authenticationService = authenticationService,
            enterpriseService = FakeEnterpriseService(
                isAllowedToConnectToHomeserverResult = { true },
            ),
        ).test {
            val initialState = awaitItem()
            assertThat(initialState.changeServerAction).isEqualTo(AsyncData.Uninitialized)
            initialState.eventSink.invoke(ChangeServerEvents.ChangeServer(AccountProvider(url = A_HOMESERVER_URL)))
            val loadingState = awaitItem()
            assertThat(loadingState.changeServerAction).isInstanceOf(AsyncData.Loading::class.java)
            val successState = awaitItem()
            assertThat(successState.changeServerAction).isEqualTo(AsyncData.Success(Unit))
        }
    }

    @Test
    fun `present - change server error`() = runTest {
        val authenticationService = FakeMatrixAuthenticationService(
            setHomeserverResult = {
                Result.failure(AN_EXCEPTION)
            },
        )
        createPresenter(
            enterpriseService = FakeEnterpriseService(
                isAllowedToConnectToHomeserverResult = { true },
            ),
            authenticationService = authenticationService,
        ).test {
            val initialState = awaitItem()
            assertThat(initialState.changeServerAction).isEqualTo(AsyncData.Uninitialized)
            initialState.eventSink.invoke(ChangeServerEvents.ChangeServer(AccountProvider(url = A_HOMESERVER_URL)))
            val loadingState = awaitItem()
            assertThat(loadingState.changeServerAction).isInstanceOf(AsyncData.Loading::class.java)
            val failureState = awaitItem()
            assertThat(failureState.changeServerAction).isInstanceOf(AsyncData.Failure::class.java)
            // Clear error
            failureState.eventSink.invoke(ChangeServerEvents.ClearError)
            val finalState = awaitItem()
            assertThat(finalState.changeServerAction).isEqualTo(AsyncData.Uninitialized)
        }
    }

    @Test
    fun `present - change server unsupported server`() = runTest {
        val authenticationService = FakeMatrixAuthenticationService(
            setHomeserverResult = {
                Result.success(aMatrixHomeServerDetails())
            },
        )
        createPresenter(
            enterpriseService = FakeEnterpriseService(
                isAllowedToConnectToHomeserverResult = { true },
            ),
            authenticationService = authenticationService,
        ).test {
            val initialState = awaitItem()
            assertThat(initialState.changeServerAction).isEqualTo(AsyncData.Uninitialized)
            initialState.eventSink.invoke(ChangeServerEvents.ChangeServer(AccountProvider(url = A_HOMESERVER_URL)))
            val loadingState = awaitItem()
            assertThat(loadingState.changeServerAction).isInstanceOf(AsyncData.Loading::class.java)
            val failureState = awaitItem()
            assertThat(failureState.changeServerAction).isInstanceOf(AsyncData.Failure::class.java)
            assertThat(failureState.changeServerAction.errorOrNull()).isEqualTo(
                ChangeServerError.UnsupportedServer
            )
        }
    }

    @Test
    fun `present - change server not allowed error`() = runTest {
        val isAllowedToConnectToHomeserverResult = lambdaRecorder<String, Boolean> { false }
        createPresenter(
            enterpriseService = FakeEnterpriseService(
                isAllowedToConnectToHomeserverResult = isAllowedToConnectToHomeserverResult,
                defaultHomeserverListResult = { listOf("element.io") },
            ),
        ).test {
            val initialState = awaitItem()
            assertThat(initialState.changeServerAction).isEqualTo(AsyncData.Uninitialized)
            val anAccountProvider = AccountProvider(url = A_HOMESERVER_URL)
            initialState.eventSink.invoke(ChangeServerEvents.ChangeServer(anAccountProvider))
            val loadingState = awaitItem()
            assertThat(loadingState.changeServerAction).isInstanceOf(AsyncData.Loading::class.java)
            val failureState = awaitItem()
            assertThat(
                (failureState.changeServerAction.errorOrNull() as ChangeServerError.UnauthorizedAccountProvider).unauthorisedAccountProviderTitle
            ).isEqualTo(anAccountProvider.title)
            assertThat(
                (failureState.changeServerAction.errorOrNull() as ChangeServerError.UnauthorizedAccountProvider).authorisedAccountProviderTitles
            ).containsExactly("element.io")
            isAllowedToConnectToHomeserverResult.assertions()
                .isCalledOnce()
                .with(value(A_HOMESERVER_URL))
        }
    }

    @Test
    fun `present - change server element pro required error`() = runTest {
        val getElementWellKnownResult = lambdaRecorder<String, WellknownRetrieverResult<ElementWellKnown>> {
            WellknownRetrieverResult.Success(
                anElementWellKnown(
                    enforceElementPro = true,
                )
            )
        }
        createPresenter(
            wellknownRetriever = FakeWellknownRetriever(
                getElementWellKnownResult = getElementWellKnownResult,
            ),
        ).test {
            val initialState = awaitItem()
            assertThat(initialState.changeServerAction).isEqualTo(AsyncData.Uninitialized)
            val anAccountProvider = AccountProvider(url = A_HOMESERVER_URL)
            initialState.eventSink.invoke(ChangeServerEvents.ChangeServer(anAccountProvider))
            val loadingState = awaitItem()
            assertThat(loadingState.changeServerAction).isInstanceOf(AsyncData.Loading::class.java)
            val failureState = awaitItem()
            assertThat(
                (failureState.changeServerAction.errorOrNull() as ChangeServerError.NeedElementPro).unauthorisedAccountProviderTitle
            ).isEqualTo(anAccountProvider.title)
            assertThat(
                (failureState.changeServerAction.errorOrNull() as ChangeServerError.NeedElementPro).applicationId
            ).isEqualTo("io.element.enterprise")
            getElementWellKnownResult.assertions()
                .isCalledOnce()
                .with(value(A_HOMESERVER_URL.ensureProtocol()))
        }
    }

    private fun createPresenter(
        authenticationService: FakeMatrixAuthenticationService = FakeMatrixAuthenticationService(),
        accountProviderDataSource: AccountProviderDataSource = AccountProviderDataSource(FakeEnterpriseService()),
        enterpriseService: EnterpriseService = FakeEnterpriseService(),
        wellknownRetriever: WellknownRetriever = FakeWellknownRetriever(),
    ) = ChangeServerPresenter(
        authenticationService = authenticationService,
        accountProviderDataSource = accountProviderDataSource,
        defaultAccountProviderAccessControl = DefaultAccountProviderAccessControl(
            enterpriseService = enterpriseService,
            wellknownRetriever = wellknownRetriever,
        ),
    )
}
