/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.loginpassword

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProvider
import tech.orbitweb.orbitx.features.login.impl.accountprovider.anAccountProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

open class LoginPasswordStateProvider : PreviewParameterProvider<LoginPasswordState> {
    override val values: Sequence<LoginPasswordState>
        get() = sequenceOf(
            aLoginPasswordState(),
            // Loading
            aLoginPasswordState(loginAction = AsyncData.Loading()),
            // Error
            aLoginPasswordState(loginAction = AsyncData.Failure(Exception("An error occurred"))),
        )
}

fun aLoginPasswordState(
    accountProvider: AccountProvider = anAccountProvider(),
    formState: LoginFormState = LoginFormState.Default,
    loginAction: AsyncData<SessionId> = AsyncData.Uninitialized,
    eventSink: (LoginPasswordEvents) -> Unit = {},
) = LoginPasswordState(
    accountProvider = accountProvider,
    formState = formState,
    loginAction = loginAction,
    eventSink = eventSink,
)

fun aLoginFormState(
    login: String = "",
    password: String = "",
) = LoginFormState(
    login = login,
    password = password,
)
