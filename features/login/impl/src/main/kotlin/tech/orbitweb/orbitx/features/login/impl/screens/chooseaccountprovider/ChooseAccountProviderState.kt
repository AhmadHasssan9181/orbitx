/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.chooseaccountprovider

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProvider
import tech.orbitweb.orbitx.features.login.impl.login.LoginMode
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

data class ChooseAccountProviderState(
    val accountProviders: ImmutableList<AccountProvider>,
    val selectedAccountProvider: AccountProvider?,
    val loginMode: AsyncData<LoginMode>,
    val eventSink: (ChooseAccountProviderEvents) -> Unit,
) {
    val submitEnabled: Boolean
        get() = selectedAccountProvider != null && (loginMode is AsyncData.Uninitialized || loginMode is AsyncData.Loading)
}
