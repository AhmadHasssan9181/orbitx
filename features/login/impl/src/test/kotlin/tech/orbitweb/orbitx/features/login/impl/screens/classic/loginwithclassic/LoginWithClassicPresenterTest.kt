/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.classic.loginwithclassic

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.enterprise.test.FakeEnterpriseService
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProviderDataSource
import tech.orbitweb.orbitx.features.login.impl.classic.ElementClassicConnection
import tech.orbitweb.orbitx.features.login.impl.classic.ElementClassicConnectionState
import tech.orbitweb.orbitx.features.login.impl.classic.FakeElementClassicConnection
import tech.orbitweb.orbitx.features.login.impl.classic.ROOM_KEYS_VERSION
import tech.orbitweb.orbitx.features.login.impl.classic.anElementClassicReady
import tech.orbitweb.orbitx.features.login.impl.classic.anElementClassicSession
import tech.orbitweb.orbitx.features.login.impl.login.LoginHelper
import tech.orbitweb.orbitx.features.login.impl.screens.onboarding.createLoginHelper
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EXCEPTION
import tech.orbitweb.orbitx.libraries.matrix.test.A_FAILURE_REASON
import tech.orbitweb.orbitx.libraries.matrix.test.A_SECRET
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.auth.FakeMatrixAuthenticationService
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.test
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoginWithClassicPresenterTest {
    @Test
    fun `present - initial state`() = runTest {
        val presenter = createPresenter()
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.isElementPro).isFalse()
            assertThat(initialState.userId).isEqualTo(A_USER_ID)
            assertThat(initialState.displayName).isNull()
            assertThat(initialState.avatar).isNull()
            assertThat(initialState.loginWithClassicAction.isUninitialized()).isTrue()
            assertThat(initialState.loginMode.isUninitialized()).isTrue()
        }
    }

    @Test
    fun `present - initial state - element Pro`() = runTest {
        val presenter = createPresenter(
            isEnterpriseBuild = true,
        )
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.isElementPro).isTrue()
        }
    }

    @Test
    fun `present - start login with correct state - user can login`() = runTest {
        val authenticationService = FakeMatrixAuthenticationService(
            setHomeserverResult = {
                Result.failure(AN_EXCEPTION)
            },
        )
        val elementClassicConnection = FakeElementClassicConnection(
            startResult = {},
        )
        val presenter = createPresenter(
            elementClassicConnection = elementClassicConnection,
            loginHelper = createLoginHelper(
                authenticationService = authenticationService,
            ),
        )
        presenter.test {
            skipItems(1)
            elementClassicConnection.emitState(
                anElementClassicReady(
                    elementClassicSession = anElementClassicSession(
                        userId = A_USER_ID,
                        secrets = A_SECRET,
                        roomKeysVersion = ROOM_KEYS_VERSION,
                        doesContainBackupKey = true,
                    ),
                    displayName = A_USER_NAME,
                )
            )
            val readyState = awaitItem()
            assertThat(readyState.userId).isEqualTo(A_USER_ID)
            assertThat(readyState.displayName).isEqualTo(A_USER_NAME)
            readyState.eventSink(LoginWithClassicEvent.Submit)
            val loadingState = awaitItem()
            assertThat(loadingState.loginWithClassicAction.isLoading()).isTrue()
            skipItems(1)
        }
    }

    @Test
    fun `present - start login with no secrets - user can login and will have to verify manually`() = runTest {
        val authenticationService = FakeMatrixAuthenticationService(
            setHomeserverResult = {
                Result.failure(AN_EXCEPTION)
            },
        )
        val elementClassicConnection = FakeElementClassicConnection(
            startResult = {},
        )
        val presenter = createPresenter(
            elementClassicConnection = elementClassicConnection,
            loginHelper = createLoginHelper(
                authenticationService = authenticationService,
            ),
        )
        presenter.test {
            skipItems(1)
            elementClassicConnection.emitState(
                anElementClassicReady(
                    elementClassicSession = anElementClassicSession(
                        userId = A_USER_ID,
                        secrets = null,
                        roomKeysVersion = null,
                    ),
                    displayName = A_USER_NAME,
                )
            )
            val readyState = awaitItem()
            assertThat(readyState.userId).isEqualTo(A_USER_ID)
            assertThat(readyState.displayName).isEqualTo(A_USER_NAME)
            readyState.eventSink(LoginWithClassicEvent.Submit)
            val loadingState = awaitItem()
            assertThat(loadingState.loginWithClassicAction.isLoading()).isTrue()
            skipItems(1)
        }
    }

    @Test
    fun `present - start login with secrets and without key backup - user will see the screen to enable key backup`() = runTest {
        val authenticationService = FakeMatrixAuthenticationService(
            setHomeserverResult = {
                Result.failure(AN_EXCEPTION)
            },
        )
        val elementClassicConnection = FakeElementClassicConnection(
            startResult = {},
        )
        val navigateToMissingKeyBackupResult = lambdaRecorder<Unit> { }
        val presenter = createPresenter(
            elementClassicConnection = elementClassicConnection,
            loginHelper = createLoginHelper(
                authenticationService = authenticationService,
            ),
            navigator = FakeLoginWithClassicNavigator(
                navigateToMissingKeyBackupResult = navigateToMissingKeyBackupResult,
            ),
        )
        presenter.test {
            skipItems(1)
            elementClassicConnection.emitState(
                anElementClassicReady(
                    elementClassicSession = anElementClassicSession(
                        userId = A_USER_ID,
                        secrets = A_SECRET,
                        roomKeysVersion = null,
                        doesContainBackupKey = false,
                    ),
                    displayName = A_USER_NAME,
                )
            )
            val readyState = awaitItem()
            assertThat(readyState.userId).isEqualTo(A_USER_ID)
            assertThat(readyState.displayName).isEqualTo(A_USER_NAME)
            readyState.eventSink(LoginWithClassicEvent.Submit)
            navigateToMissingKeyBackupResult.assertions().isCalledOnce()
        }
    }

    @Test
    fun `present - start login with secrets and with invalid key backup - user will see the screen to enable key backup`() = runTest {
        val authenticationService = FakeMatrixAuthenticationService(
            setHomeserverResult = {
                Result.failure(AN_EXCEPTION)
            },
        )
        val elementClassicConnection = FakeElementClassicConnection(
            startResult = {},
        )
        val navigateToMissingKeyBackupResult = lambdaRecorder<Unit> { }
        val presenter = createPresenter(
            elementClassicConnection = elementClassicConnection,
            loginHelper = createLoginHelper(
                authenticationService = authenticationService,
            ),
            navigator = FakeLoginWithClassicNavigator(
                navigateToMissingKeyBackupResult = navigateToMissingKeyBackupResult,
            ),
        )
        presenter.test {
            skipItems(1)
            elementClassicConnection.emitState(
                anElementClassicReady(
                    elementClassicSession = anElementClassicSession(
                        userId = A_USER_ID,
                        secrets = A_SECRET,
                        roomKeysVersion = ROOM_KEYS_VERSION,
                        // false here
                        doesContainBackupKey = false,
                    ),
                    displayName = A_USER_NAME,
                )
            )
            val readyState = awaitItem()
            assertThat(readyState.userId).isEqualTo(A_USER_ID)
            assertThat(readyState.displayName).isEqualTo(A_USER_NAME)
            readyState.eventSink(LoginWithClassicEvent.Submit)
            navigateToMissingKeyBackupResult.assertions().isCalledOnce()
        }
    }

    @Test
    fun `present - submit in wrong state and clear error`() = runTest {
        val elementClassicConnection = FakeElementClassicConnection(
            startResult = {},
        )
        val presenter = createPresenter(
            elementClassicConnection = elementClassicConnection,
        )
        presenter.test {
            skipItems(1)
            elementClassicConnection.emitState(
                ElementClassicConnectionState.Error(
                    error = A_FAILURE_REASON,
                )
            )
            val initialState = awaitItem()
            assertThat(initialState.loginWithClassicAction.isUninitialized()).isTrue()
            initialState.eventSink(LoginWithClassicEvent.Submit)
            val errorState = awaitItem()
            assertThat(errorState.loginWithClassicAction.isFailure()).isTrue()
            errorState.eventSink(LoginWithClassicEvent.ClearError)
            val clearedState = awaitItem()
            assertThat(clearedState.loginWithClassicAction.isUninitialized()).isTrue()
        }
    }
}

private fun createPresenter(
    userId: UserId = A_USER_ID,
    navigator: LoginWithClassicNavigator = FakeLoginWithClassicNavigator(),
    loginHelper: LoginHelper = createLoginHelper(),
    elementClassicConnection: ElementClassicConnection = FakeElementClassicConnection(),
    accountProviderDataSource: AccountProviderDataSource = AccountProviderDataSource(FakeEnterpriseService()),
    isEnterpriseBuild: Boolean = false,
) = LoginWithClassicPresenter(
    userId = userId,
    navigator = navigator,
    loginHelper = loginHelper,
    elementClassicConnection = elementClassicConnection,
    accountProviderDataSource = accountProviderDataSource,
    buildMeta = aBuildMeta(
        isEnterpriseBuild = isEnterpriseBuild,
    ),
)
