/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl.declineandblock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction

open class DeclineAndBlockStateProvider : PreviewParameterProvider<DeclineAndBlockState> {
    override val values: Sequence<DeclineAndBlockState>
        get() = sequenceOf(
            aDeclineAndBlockState(),
            aDeclineAndBlockState(
                reportRoom = true,
                reportReason = "Inappropriate content",
            ),
            aDeclineAndBlockState(
                blockUser = true,
            ),
            aDeclineAndBlockState(
                declineAction = AsyncAction.Loading,
            ),
            aDeclineAndBlockState(
                declineAction = AsyncAction.Failure(Exception("Failed to decline")),
            ),
        )
}

fun aDeclineAndBlockState(
    reportRoom: Boolean = false,
    reportReason: String = "",
    blockUser: Boolean = false,
    declineAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    eventSink: (DeclineAndBlockEvents) -> Unit = {},
) = DeclineAndBlockState(
    reportRoom = reportRoom,
    reportReason = reportReason,
    blockUser = blockUser,
    declineAction = declineAction,
    eventSink = eventSink,
)
