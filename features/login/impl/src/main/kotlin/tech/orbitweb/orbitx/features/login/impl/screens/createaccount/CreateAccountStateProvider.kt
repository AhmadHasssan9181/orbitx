/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.screens.createaccount

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

open class CreateAccountStateProvider : PreviewParameterProvider<CreateAccountState> {
    override val values: Sequence<CreateAccountState>
        get() = sequenceOf(
            aCreateAccountState(),
            aCreateAccountState(pageProgress = 33),
            aCreateAccountState(createAction = AsyncAction.Loading),
            aCreateAccountState(createAction = AsyncAction.Failure(RuntimeException("Failed to create account"))),
        )
}

private fun aCreateAccountState(
    pageProgress: Int = 100,
    createAction: AsyncAction<SessionId> = AsyncAction.Uninitialized,
) = CreateAccountState(
    url = "https://example.com",
    isDebugBuild = true,
    pageProgress = pageProgress,
    createAction = createAction,
    eventSink = {}
)
