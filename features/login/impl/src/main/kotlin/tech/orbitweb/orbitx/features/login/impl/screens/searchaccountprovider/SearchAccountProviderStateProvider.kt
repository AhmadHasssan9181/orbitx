/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.searchaccountprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.appconfig.AuthenticationConfig
import tech.orbitweb.orbitx.features.login.impl.changeserver.aChangeServerState
import tech.orbitweb.orbitx.features.login.impl.resolver.HomeserverData
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

open class SearchAccountProviderStateProvider : PreviewParameterProvider<SearchAccountProviderState> {
    override val values: Sequence<SearchAccountProviderState>
        get() = sequenceOf(
            aSearchAccountProviderState(),
            aSearchAccountProviderState(userInputResult = AsyncData.Success(aHomeserverDataList())),
            // Add other state here
        )
}

fun aSearchAccountProviderState(
    userInput: String = "",
    userInputResult: AsyncData<List<HomeserverData>> = AsyncData.Uninitialized,
) = SearchAccountProviderState(
    userInput = userInput,
    userInputResult = userInputResult,
    changeServerState = aChangeServerState(),
    eventSink = {}
)

fun aHomeserverDataList(): List<HomeserverData> {
    return listOf(
        aHomeserverData(homeserverUrl = AuthenticationConfig.MATRIX_ORG_URL),
        aHomeserverData(homeserverUrl = "https://no.sliding.sync"),
        aHomeserverData(homeserverUrl = "https://invalid"),
    )
}

fun aHomeserverData(
    homeserverUrl: String = AuthenticationConfig.MATRIX_ORG_URL,
): HomeserverData {
    return HomeserverData(homeserverUrl = homeserverUrl)
}
