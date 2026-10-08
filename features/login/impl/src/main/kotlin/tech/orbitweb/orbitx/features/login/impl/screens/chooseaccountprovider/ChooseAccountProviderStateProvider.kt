/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.chooseaccountprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProvider
import tech.orbitweb.orbitx.features.login.impl.accountprovider.anAccountProvider
import tech.orbitweb.orbitx.features.login.impl.login.LoginMode
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import kotlinx.collections.immutable.toImmutableList

open class ChooseAccountProviderStateProvider : PreviewParameterProvider<ChooseAccountProviderState> {
    private val server1 = anAccountProvider(
        url = "https://server1.io",
        subtitle = null,
        isPublic = false,
        isMatrixOrg = false,
    )
    private val server2 = anAccountProvider(
        url = "https://server2.io",
        subtitle = null,
        isPublic = false,
        isMatrixOrg = false,
    )
    private val server3 = anAccountProvider(
        url = "https://server3.io",
        subtitle = null,
        isPublic = false,
        isMatrixOrg = false,
    )
    override val values: Sequence<ChooseAccountProviderState>
        get() = sequenceOf(
            aChooseAccountProviderState(
                accountProviders = listOf(
                    server1,
                    server2,
                    server3,
                )
            ),
            aChooseAccountProviderState(
                accountProviders = listOf(
                    server1,
                    server2,
                    server3,
                ),
                selectedAccountProvider = server2,
            ),
            aChooseAccountProviderState(
                accountProviders = listOf(
                    server1,
                    server2,
                    server3,
                ),
                selectedAccountProvider = server2,
                loginMode = AsyncData.Loading(),
            ),
            // Add other state here
        )
}

fun aChooseAccountProviderState(
    accountProviders: List<AccountProvider> = listOf(
        anAccountProvider()
    ),
    selectedAccountProvider: AccountProvider? = null,
    loginMode: AsyncData<LoginMode> = AsyncData.Uninitialized,
    eventSink: (ChooseAccountProviderEvents) -> Unit = {},
) = ChooseAccountProviderState(
    accountProviders = accountProviders.toImmutableList(),
    selectedAccountProvider = selectedAccountProvider,
    loginMode = loginMode,
    eventSink = eventSink,
)
