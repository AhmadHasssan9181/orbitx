/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.leaveroom.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.leaveroom.api.LeaveRoomEvent
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

class InternalLeaveRoomStateProvider : PreviewParameterProvider<InternalLeaveRoomState> {
    override val values: Sequence<InternalLeaveRoomState>
        get() = sequenceOf(
            aLeaveRoomState(),
            aLeaveRoomState(
                leaveAction = Confirmation.Generic(roomId = A_ROOM_ID),
            ),
            aLeaveRoomState(
                leaveAction = Confirmation.PrivateRoom(roomId = A_ROOM_ID),
            ),
            aLeaveRoomState(
                leaveAction = Confirmation.LastUserInRoom(roomId = A_ROOM_ID),
            ),
            aLeaveRoomState(
                leaveAction = Confirmation.Dm(roomId = A_ROOM_ID),
            ),
            aLeaveRoomState(
                leaveAction = Confirmation.LastOwnerInRoom(roomId = A_ROOM_ID),
            ),
            aLeaveRoomState(
                leaveAction = AsyncAction.Loading,
            ),
            aLeaveRoomState(
                leaveAction = AsyncAction.Failure(RuntimeException("Something went wrong")),
            ),
        )
}

private val A_ROOM_ID = RoomId("!aRoomId:aDomain")

fun aLeaveRoomState(
    leaveAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    eventSink: (LeaveRoomEvent) -> Unit = {},
) = InternalLeaveRoomState(
    leaveAction = leaveAction,
    eventSink = eventSink,
)
