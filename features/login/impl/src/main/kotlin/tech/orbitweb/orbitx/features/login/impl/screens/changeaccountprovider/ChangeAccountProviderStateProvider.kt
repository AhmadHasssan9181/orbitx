/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.changeaccountprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProvider
import tech.orbitweb.orbitx.features.login.impl.accountprovider.anAccountProvider
import tech.orbitweb.orbitx.features.login.impl.changeserver.ChangeServerState
import tech.orbitweb.orbitx.features.login.impl.changeserver.aChangeServerState

open class ChangeAccountProviderStateProvider : PreviewParameterProvider<ChangeAccountProviderState> {
    override val values: Sequence<ChangeAccountProviderState>
        get() = sequenceOf(
            aChangeAccountProviderState(),
            aChangeAccountProviderState(canSearchForAccountProviders = false),
            // Add other state here
        )
}

fun aChangeAccountProviderState(
    accountProviders: List<AccountProvider> = listOf(
        anAccountProvider()
    ),
    canSearchForAccountProviders: Boolean = true,
    changeServerState: ChangeServerState = aChangeServerState(),
) = ChangeAccountProviderState(
    accountProviders = accountProviders.toImmutableList(),
    canSearchForAccountProviders = canSearchForAccountProviders,
    changeServerState = changeServerState,
)
