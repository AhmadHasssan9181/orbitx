/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.changeserver

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.login.impl.error.ChangeServerError
import tech.orbitweb.orbitx.libraries.architecture.AsyncData

open class ChangeServerStateProvider : PreviewParameterProvider<ChangeServerState> {
    override val values: Sequence<ChangeServerState>
        get() = sequenceOf(
            aChangeServerState(),
            aChangeServerState(changeServerAction = AsyncData.Failure(ChangeServerError.Error(null))),
            aChangeServerState(changeServerAction = AsyncData.Failure(ChangeServerError.SlidingSyncAlert)),
            aChangeServerState(
                changeServerAction = AsyncData.Failure(
                    ChangeServerError.UnauthorizedAccountProvider(
                        unauthorisedAccountProviderTitle = "example.com",
                        authorisedAccountProviderTitles = listOf("orbitweb.tech"),
                    )
                )
            ),
            aChangeServerState(
                changeServerAction = AsyncData.Failure(
                    ChangeServerError.NeedElementPro(
                        unauthorisedAccountProviderTitle = "example.com",
                        applicationId = "applicationId",
                    ),
                )
            ),
            aChangeServerState(
                changeServerAction = AsyncData.Failure(
                    ChangeServerError.UnsupportedServer
                )
            ),
        )
}

fun aChangeServerState(
    changeServerAction: AsyncData<Unit> = AsyncData.Uninitialized,
) = ChangeServerState(
    changeServerAction = changeServerAction,
    eventSink = {}
)
