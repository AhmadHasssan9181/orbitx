/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.confirmaccountprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProvider
import tech.orbitweb.orbitx.features.login.impl.accountprovider.anAccountProvider
import tech.orbitweb.orbitx.features.login.impl.login.LoginMode
import tech.orbitweb.orbitx.features.login.impl.screens.createaccount.AccountCreationNotSupported
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

open class ConfirmAccountProviderStateProvider : PreviewParameterProvider<ConfirmAccountProviderState> {
    override val values: Sequence<ConfirmAccountProviderState>
        get() = sequenceOf(
            aConfirmAccountProviderState(),
            aConfirmAccountProviderState(
                isAccountCreation = true,
            ),
            aConfirmAccountProviderState(
                isAccountCreation = true,
                loginMode = AsyncData.Failure(AccountCreationNotSupported())
            ),
        )
}

private fun aConfirmAccountProviderState(
    accountProvider: AccountProvider = anAccountProvider(),
    isAccountCreation: Boolean = false,
    loginMode: AsyncData<LoginMode> = AsyncData.Uninitialized,
    eventSink: (ConfirmAccountProviderEvents) -> Unit = {},
) = ConfirmAccountProviderState(
    accountProvider = accountProvider,
    isAccountCreation = isAccountCreation,
    loginMode = loginMode,
    eventSink = eventSink
)
