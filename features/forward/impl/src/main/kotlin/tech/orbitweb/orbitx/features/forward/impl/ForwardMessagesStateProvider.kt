/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.forward.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

open class ForwardMessagesStateProvider : PreviewParameterProvider<ForwardMessagesState> {
    override val values: Sequence<ForwardMessagesState>
        get() = sequenceOf(
            aForwardMessagesState(),
            aForwardMessagesState(
                forwardAction = AsyncAction.Loading,
            ),
            aForwardMessagesState(
                forwardAction = AsyncAction.Success(
                    listOf(RoomId("!room2:domain")),
                )
            ),
            aForwardMessagesState(
                forwardAction = AsyncAction.Failure(RuntimeException("error")),
            ),
        )
}

fun aForwardMessagesState(
    forwardAction: AsyncAction<List<RoomId>> = AsyncAction.Uninitialized,
    eventSink: (ForwardMessagesEvents) -> Unit = {}
) = ForwardMessagesState(
    forwardAction = forwardAction,
    eventSink = eventSink
)
