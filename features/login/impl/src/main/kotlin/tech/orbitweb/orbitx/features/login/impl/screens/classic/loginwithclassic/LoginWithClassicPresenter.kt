/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.classic.loginwithclassic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProviderDataSource
import tech.orbitweb.orbitx.features.login.impl.classic.ElementClassicConnection
import tech.orbitweb.orbitx.features.login.impl.classic.ElementClassicConnectionState
import tech.orbitweb.orbitx.features.login.impl.login.LoginHelper
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.core.uri.ensureProtocol
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

@AssistedInject
class LoginWithClassicPresenter(
    @Assisted private val userId: UserId,
    @Assisted private val navigator: LoginWithClassicNavigator,
    private val loginHelper: LoginHelper,
    private val elementClassicConnection: ElementClassicConnection,
    private val accountProviderDataSource: AccountProviderDataSource,
    private val buildMeta: BuildMeta,
) : Presenter<LoginWithClassicState> {
    @AssistedFactory
    interface Factory {
        fun create(
            userId: UserId,
            navigator: LoginWithClassicNavigator,
        ): LoginWithClassicPresenter
    }

    @Composable
    override fun present(): LoginWithClassicState {
        val coroutineScope = rememberCoroutineScope()
        var loginWithClassicAction by remember {
            mutableStateOf<AsyncAction<Unit>>(AsyncAction.Uninitialized)
        }
        val loginMode by loginHelper.collectLoginMode()
        val elementClassicConnectionState by elementClassicConnection.stateFlow.collectAsState()

        fun handleEvent(event: LoginWithClassicEvent) {
            when (event) {
                LoginWithClassicEvent.Submit -> {
                    val currentState = elementClassicConnection.stateFlow.value
                    if (currentState is ElementClassicConnectionState.ElementClassicReady) {
                        if (currentState.elementClassicSession.secrets != null &&
                            !currentState.elementClassicSession.doesContainBackupKey) {
                            navigator.navigateToMissingKeyBackup()
                        } else {
                            coroutineScope.launch {
                                loginWithClassicAction = AsyncAction.Loading
                                // Ensure that the current account provider is set
                                val elementClassicUserId = currentState.elementClassicSession.userId
                                val accountProvider = elementClassicUserId.domainName.orEmpty().ensureProtocol()
                                accountProviderDataSource.setUrl(accountProvider)
                                loginHelper.submit(
                                    isAccountCreation = false,
                                    homeserverUrl = accountProvider,
                                    resolvedHomeserverUrl = currentState.elementClassicSession.homeserverUrl,
                                    loginHint = "mxid:" + elementClassicUserId.value,
                                )
                            }
                        }
                    } else {
                        loginWithClassicAction = AsyncAction.Failure(IllegalStateException("Element Classic is not ready"))
                    }
                }
                LoginWithClassicEvent.ClearError -> {
                    loginWithClassicAction = AsyncAction.Uninitialized
                    loginHelper.clearError()
                }
            }
        }

        val elementClassicReady = elementClassicConnectionState as? ElementClassicConnectionState.ElementClassicReady
        return LoginWithClassicState(
            isElementPro = buildMeta.isEnterpriseBuild,
            userId = userId,
            displayName = elementClassicReady?.displayName,
            avatar = elementClassicReady?.avatar,
            loginMode = loginMode,
            loginWithClassicAction = loginWithClassicAction,
            eventSink = ::handleEvent,
        )
    }
}
