/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.changeaccountprovider

import tech.orbitweb.orbitx.features.login.impl.accountprovider.AccountProvider
import tech.orbitweb.orbitx.features.login.impl.changeserver.ChangeServerState
import kotlinx.collections.immutable.ImmutableList

data class ChangeAccountProviderState(
    val accountProviders: ImmutableList<AccountProvider>,
    val canSearchForAccountProviders: Boolean,
    val changeServerState: ChangeServerState,
)
