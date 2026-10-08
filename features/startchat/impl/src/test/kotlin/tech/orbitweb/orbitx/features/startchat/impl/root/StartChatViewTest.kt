/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.startchat.impl.root

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.startchat.impl.R
import tech.orbitweb.orbitx.features.startchat.impl.userlist.aRecentDirectRoomList
import tech.orbitweb.orbitx.features.startchat.impl.userlist.aUserListState
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.ui.model.getBestName
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test
import org.robolectric.annotation.Config

class StartChatViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<StartChatEvents>(expectEvents = false)
        ensureCalledOnce {
            setStartChatView(
                aCreateRoomRootState(
                    eventSink = eventsRecorder,
                ),
                onCloseClick = it
            )
            pressBack()
        }
    }

    @Test
    fun `clicking on New room invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<StartChatEvents>(expectEvents = false)
        ensureCalledOnce {
            setStartChatView(
                aCreateRoomRootState(
                    eventSink = eventsRecorder,
                ),
                onNewRoomClick = it
            )
            clickOn(R.string.screen_create_room_action_create_room)
        }
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `clicking on Invite people invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<StartChatEvents>(expectEvents = false)
        ensureCalledOnce {
            setStartChatView(
                aCreateRoomRootState(
                    applicationName = "test",
                    eventSink = eventsRecorder,
                ),
                onInviteFriendsClick = it
            )
            val text = activity!!.getString(CommonStrings.action_invite_friends_to_app, "test")
            onNodeWithText(text).performClick()
        }
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `clicking on a user suggestion invokes the expected callback`() = runAndroidComposeUiTest {
        val recentDirectRoomList = aRecentDirectRoomList()
        val firstRoom = recentDirectRoomList[0]
        val eventsRecorder = EventsRecorder<StartChatEvents>(expectEvents = false)
        ensureCalledOnceWithParam(firstRoom.roomId) {
            setStartChatView(
                aCreateRoomRootState(
                    userListState = aUserListState(
                        recentDirectRooms = recentDirectRoomList
                    ),
                    eventSink = eventsRecorder,
                ),
                onOpenDM = it
            )
            onNodeWithText(firstRoom.matrixUser.getBestName()).performClick()
        }
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `clicking on Join room by address invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<StartChatEvents>(expectEvents = false)
        ensureCalledOnce {
            setStartChatView(
                aCreateRoomRootState(
                    eventSink = eventsRecorder,
                ),
                onJoinRoomByAddressClick = it
            )
            clickOn(R.string.screen_start_chat_join_room_by_address_action)
        }
    }

    @Test
    fun `clicking on room directory invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<StartChatEvents>(expectEvents = false)
        ensureCalledOnce {
            setStartChatView(
                aCreateRoomRootState(
                    eventSink = eventsRecorder,
                ),
                onRoomDirectorySearchClick = it
            )
            clickOn(R.string.screen_room_directory_search_title)
        }
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setStartChatView(
    state: StartChatState,
    onCloseClick: () -> Unit = EnsureNeverCalled(),
    onNewRoomClick: () -> Unit = EnsureNeverCalled(),
    onOpenDM: (RoomId) -> Unit = EnsureNeverCalledWithParam(),
    onInviteFriendsClick: () -> Unit = EnsureNeverCalled(),
    onJoinRoomByAddressClick: () -> Unit = EnsureNeverCalled(),
    onRoomDirectorySearchClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        StartChatView(
            state = state,
            onCloseClick = onCloseClick,
            onNewRoomClick = onNewRoomClick,
            onOpenDM = onOpenDM,
            onInviteFriendsClick = onInviteFriendsClick,
            onJoinByAddressClick = onJoinRoomByAddressClick,
            onRoomDirectorySearchClick = onRoomDirectorySearchClick,
        )
    }
}
