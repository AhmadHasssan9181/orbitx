/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat

import com.bumble.appyx.core.plugin.Plugin
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import tech.orbitweb.orbitx.features.startchat.impl.StartChatFlowNode.NavTarget
import tech.orbitweb.orbitx.libraries.architecture.overlay.Overlay
import tech.orbitweb.orbitx.libraries.architecture.overlay.operation.hide
import tech.orbitweb.orbitx.libraries.architecture.overlay.operation.show
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias

interface StartChatNavigator : Plugin {
    fun onRoomCreated(roomIdOrAlias: RoomIdOrAlias, serverNames: List<String>)
    fun onCreateNewRoom()
    fun onShowJoinRoomByAddress()
    fun onDismissJoinRoomByAddress()
    fun onOpenRoomDirectory()
}

class DefaultStartChatNavigator(
    private val backstack: BackStack<NavTarget>,
    private val overlay: Overlay<NavTarget>,
    private val openRoom: (RoomIdOrAlias, List<String>) -> Unit,
    private val openRoomDirectory: () -> Unit,
) : StartChatNavigator {
    override fun onRoomCreated(roomIdOrAlias: RoomIdOrAlias, serverNames: List<String>) =
        openRoom(roomIdOrAlias, serverNames)

    override fun onOpenRoomDirectory() = openRoomDirectory()

    override fun onCreateNewRoom() {
        backstack.push(NavTarget.NewRoom)
    }

    override fun onShowJoinRoomByAddress() {
        overlay.show(NavTarget.JoinByAddress)
    }

    override fun onDismissJoinRoomByAddress() {
        overlay.hide()
    }
}
