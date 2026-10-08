/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.share.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

open class ShareStateProvider : PreviewParameterProvider<ShareState> {
    override val values: Sequence<ShareState>
        get() = sequenceOf(
            aShareState(),
            aShareState(
                shareAction = AsyncAction.Loading,
            ),
            aShareState(
                shareAction = AsyncAction.Success(
                    listOf(RoomId("!room2:domain")),
                )
            ),
            aShareState(
                shareAction = AsyncAction.Failure(RuntimeException("error")),
            ),
        )
}

fun aShareState(
    shareAction: AsyncAction<List<RoomId>> = AsyncAction.Uninitialized,
    eventSink: (ShareEvents) -> Unit = {}
) = ShareState(
    shareAction = shareAction,
    eventSink = eventSink
)
