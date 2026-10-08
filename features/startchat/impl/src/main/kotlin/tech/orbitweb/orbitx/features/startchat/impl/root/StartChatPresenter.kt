/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.impl.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch
import tech.orbitweb.orbitx.features.startchat.api.StartDMAction
import tech.orbitweb.orbitx.features.startchat.impl.userlist.SelectionMode
import tech.orbitweb.orbitx.features.startchat.impl.userlist.UserListDataStore
import tech.orbitweb.orbitx.features.startchat.impl.userlist.UserListPresenter
import tech.orbitweb.orbitx.features.startchat.impl.userlist.UserListPresenterArgs
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.usersearch.api.UserRepository

@Inject
class StartChatPresenter(
    presenterFactory: UserListPresenter.Factory,
    userRepository: UserRepository,
    userListDataStore: UserListDataStore,
    private val startDMAction: StartDMAction,
    private val buildMeta: BuildMeta,
) : Presenter<StartChatState> {
    private val presenter = presenterFactory.create(
        UserListPresenterArgs(
            selectionMode = SelectionMode.Single,
        ),
        userRepository,
        userListDataStore,
    )

    @Composable
    override fun present(): StartChatState {
        val userListState = presenter.present()

        val localCoroutineScope = rememberCoroutineScope()
        val startDmActionState: MutableState<AsyncAction<RoomId>> = remember { mutableStateOf(AsyncAction.Uninitialized) }

        fun handleEvent(event: StartChatEvents) {
            when (event) {
                is StartChatEvents.StartDM -> localCoroutineScope.launch {
                    startDMAction.execute(
                        matrixUser = event.matrixUser,
                        createIfDmDoesNotExist = startDmActionState.value is AsyncAction.Confirming,
                        actionState = startDmActionState,
                    )
                }
                StartChatEvents.CancelStartDM -> startDmActionState.value = AsyncAction.Uninitialized
            }
        }

        return StartChatState(
            applicationName = buildMeta.applicationName,
            userListState = userListState,
            startDmAction = startDmActionState.value,
            eventSink = ::handleEvent,
        )
    }
}
