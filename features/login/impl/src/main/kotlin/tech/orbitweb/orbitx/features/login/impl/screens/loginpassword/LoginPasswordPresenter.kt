/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.loginpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProviderDataSource
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.matrix.api.auth.MatrixAuthenticationService
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

@AssistedInject
class LoginPasswordPresenter(
    @Assisted
    private val initialLogin: String,
    private val authenticationService: MatrixAuthenticationService,
    private val accountProviderDataSource: AccountProviderDataSource,
) : Presenter<LoginPasswordState> {
    @AssistedFactory
    interface Factory {
        fun create(initialLogin: String): LoginPasswordPresenter
    }

    @Composable
    override fun present(): LoginPasswordState {
        val localCoroutineScope = rememberCoroutineScope()
        val loginAction: MutableState<AsyncData<SessionId>> = remember {
            mutableStateOf(AsyncData.Uninitialized)
        }

        val formState = rememberSaveable {
            mutableStateOf(
                LoginFormState(
                    login = initialLogin,
                    password = "",
                )
            )
        }
        val accountProvider by accountProviderDataSource.flow.collectAsState()

        fun handleEvent(event: LoginPasswordEvents) {
            when (event) {
                is LoginPasswordEvents.SetLogin -> updateFormState(formState) {
                    copy(login = event.login)
                }
                is LoginPasswordEvents.SetPassword -> updateFormState(formState) {
                    copy(password = event.password)
                }
                LoginPasswordEvents.Submit -> {
                    localCoroutineScope.submit(formState.value, loginAction)
                }
                LoginPasswordEvents.ClearError -> loginAction.value = AsyncData.Uninitialized
            }
        }

        return LoginPasswordState(
            accountProvider = accountProvider,
            formState = formState.value,
            loginAction = loginAction.value,
            eventSink = ::handleEvent,
        )
    }

    private fun CoroutineScope.submit(formState: LoginFormState, loggedInState: MutableState<AsyncData<SessionId>>) = launch {
        loggedInState.value = AsyncData.Loading()
        authenticationService.login(formState.login.trim(), formState.password)
            .onSuccess { sessionId ->
                loggedInState.value = AsyncData.Success(sessionId)
            }
            .onFailure { failure ->
                loggedInState.value = AsyncData.Failure(failure)
            }
    }

    private fun updateFormState(formState: MutableState<LoginFormState>, updateLambda: LoginFormState.() -> LoginFormState) {
        formState.value = updateLambda(formState.value)
    }
}
