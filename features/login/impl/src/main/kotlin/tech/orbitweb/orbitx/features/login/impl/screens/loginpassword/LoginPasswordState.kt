/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.loginpassword

import android.os.Parcelable
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import kotlinx.parcelize.Parcelize

data class LoginPasswordState(
    val accountProvider: AccountProvider,
    val formState: LoginFormState,
    val loginAction: AsyncData<SessionId>,
    val eventSink: (LoginPasswordEvents) -> Unit
) {
    val submitEnabled: Boolean
        get() = loginAction !is AsyncData.Failure &&
            formState.login.isNotEmpty() &&
            formState.password.isNotEmpty()
}

@Parcelize
data class LoginFormState(
    val login: String,
    val password: String,
) : Parcelable {
    companion object {
        val Default = LoginFormState("", "")
    }
}
