/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl.acceptdecline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.invite.api.InviteData
import tech.orbitweb.orbitx.features.invite.api.acceptdecline.AcceptDeclineInviteEvents
import tech.orbitweb.orbitx.features.invite.api.acceptdecline.AcceptDeclineInviteState
import tech.orbitweb.orbitx.features.invite.api.acceptdecline.ConfirmingDeclineInvite
import tech.orbitweb.orbitx.features.invite.impl.AcceptInvite
import tech.orbitweb.orbitx.features.invite.impl.DeclineInvite
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.architecture.runUpdatingState
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

@Inject
class AcceptDeclineInvitePresenter(
    private val acceptInvite: AcceptInvite,
    private val declineInvite: DeclineInvite,
) : Presenter<AcceptDeclineInviteState> {
    @Composable
    override fun present(): AcceptDeclineInviteState {
        val localCoroutineScope = rememberCoroutineScope()
        val acceptedAction: MutableState<AsyncAction<RoomId>> =
            remember { mutableStateOf(AsyncAction.Uninitialized) }
        val declinedAction: MutableState<AsyncAction<RoomId>> =
            remember { mutableStateOf(AsyncAction.Uninitialized) }

        fun handleEvent(event: AcceptDeclineInviteEvents) {
            when (event) {
                is AcceptDeclineInviteEvents.AcceptInvite -> {
                    localCoroutineScope.acceptInvite(event.invite.roomId, acceptedAction)
                }

                is AcceptDeclineInviteEvents.DeclineInvite -> {
                    val inviteData = event.invite
                    if (event.shouldConfirm) {
                        declinedAction.value = ConfirmingDeclineInvite(inviteData, event.blockUser)
                    } else {
                        localCoroutineScope.declineInvite(
                            inviteData = inviteData,
                            blockUser = event.blockUser,
                            declinedAction = declinedAction,
                        )
                    }
                }
                is InternalAcceptDeclineInviteEvents.ClearAcceptActionState -> {
                    acceptedAction.value = AsyncAction.Uninitialized
                }

                is InternalAcceptDeclineInviteEvents.ClearDeclineActionState -> {
                    declinedAction.value = AsyncAction.Uninitialized
                }
            }
        }

        return AcceptDeclineInviteState(
            acceptAction = acceptedAction.value,
            declineAction = declinedAction.value,
            eventSink = ::handleEvent,
        )
    }

    private fun CoroutineScope.acceptInvite(
        roomId: RoomId,
        acceptedAction: MutableState<AsyncAction<RoomId>>,
    ) = launch {
        acceptedAction.runUpdatingState {
            acceptInvite(roomId)
        }
    }

    private fun CoroutineScope.declineInvite(
        inviteData: InviteData,
        blockUser: Boolean,
        declinedAction: MutableState<AsyncAction<RoomId>>,
    ) = launch {
        declinedAction.runUpdatingState {
            declineInvite(
                roomId = inviteData.roomId,
                blockUser = blockUser,
                reportRoom = false,
                reportReason = null
            )
        }
    }
}
