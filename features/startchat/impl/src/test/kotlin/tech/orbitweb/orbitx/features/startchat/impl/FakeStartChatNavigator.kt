/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.impl

import tech.orbitweb.orbitx.features.startchat.StartChatNavigator
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias

class FakeStartChatNavigator(
    private val openRoomLambda: (roomIdOrAlias: RoomIdOrAlias, serverNames: List<String>) -> Unit = { _, _ -> },
    private val createNewRoomLambda: () -> Unit = {},
    private val showJoinRoomByAddressLambda: () -> Unit = {},
    private val dismissJoinRoomByAddressLambda: () -> Unit = {},
    private val openRoomDirectoryLambda: () -> Unit = {},
) : StartChatNavigator {
    override fun onRoomCreated(roomIdOrAlias: RoomIdOrAlias, serverNames: List<String>) {
        openRoomLambda(roomIdOrAlias, serverNames)
    }

    override fun onCreateNewRoom() {
        createNewRoomLambda()
    }

    override fun onShowJoinRoomByAddress() {
        showJoinRoomByAddressLambda()
    }

    override fun onDismissJoinRoomByAddress() {
        dismissJoinRoomByAddressLambda()
    }

    override fun onOpenRoomDirectory() {
        openRoomDirectoryLambda()
    }
}
