/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.searchaccountprovider

import tech.orbitweb.orbitx.features.login.impl.changeserver.ChangeServerState
import tech.orbitweb.orbitx.features.login.impl.resolver.HomeserverData
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

data class SearchAccountProviderState(
    val userInput: String,
    val userInputResult: AsyncData<List<HomeserverData>>,
    val changeServerState: ChangeServerState,
    val eventSink: (SearchAccountProviderEvents) -> Unit
)
