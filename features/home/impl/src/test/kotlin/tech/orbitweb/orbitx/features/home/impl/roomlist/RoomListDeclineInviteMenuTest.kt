/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.home.impl.roomlist

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.home.impl.model.aRoomListRoomSummary
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import tech.orbitweb.orbitx.tests.testutils.setSafeContent
import org.junit.Test

class RoomListDeclineInviteMenuTest : RobolectricTest() {
    @Test
    fun `clicking on decline emits the expected Events`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomListEvent>()
        val menu = RoomListState.DeclineInviteMenu.Shown(roomSummary = aRoomListRoomSummary())
        setSafeContent {
            RoomListDeclineInviteMenu(
                menu = menu,
                canReportRoom = false,
                onDeclineAndBlockClick = EnsureNeverCalledWithParam(),
                eventSink = eventsRecorder,
            )
        }
        clickOn(CommonStrings.action_decline)
        eventsRecorder.assertList(
            listOf(
                RoomListEvent.HideDeclineInviteMenu,
                RoomListEvent.DeclineInvite(menu.roomSummary, blockUser = false),
            )
        )
    }

    @Test
    fun `clicking on decline and block when canReportRoom=true, it emits the expected Events and callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomListEvent>()
        val menu = RoomListState.DeclineInviteMenu.Shown(roomSummary = aRoomListRoomSummary())
        setSafeContent {
            RoomListDeclineInviteMenu(
                menu = menu,
                canReportRoom = true,
                onDeclineAndBlockClick = EnsureCalledOnceWithParam(menu.roomSummary, Unit),
                eventSink = eventsRecorder,
            )
        }
        clickOn(CommonStrings.action_decline_and_block)
        val expectedEvents = listOf(RoomListEvent.HideDeclineInviteMenu)
        eventsRecorder.assertList(expectedEvents)
    }

    @Test
    fun `clicking on decline and block when canReportRoom=false, it emits the expected Events`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomListEvent>()
        val menu = RoomListState.DeclineInviteMenu.Shown(roomSummary = aRoomListRoomSummary())
        setSafeContent {
            RoomListDeclineInviteMenu(
                menu = menu,
                canReportRoom = false,
                onDeclineAndBlockClick = EnsureNeverCalledWithParam(),
                eventSink = eventsRecorder,
            )
        }
        clickOn(CommonStrings.action_decline_and_block)
        val expectedEvents = listOf(
            RoomListEvent.HideDeclineInviteMenu,
            RoomListEvent.DeclineInvite(menu.roomSummary, blockUser = true),
        )
        eventsRecorder.assertList(expectedEvents)
    }

    @Test
    fun `clicking on cancel emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomListEvent>()
        val menu = RoomListState.DeclineInviteMenu.Shown(roomSummary = aRoomListRoomSummary())
        setSafeContent {
            RoomListDeclineInviteMenu(
                menu = menu,
                canReportRoom = false,
                onDeclineAndBlockClick = EnsureNeverCalledWithParam(),
                eventSink = eventsRecorder,
            )
        }
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertList(listOf(RoomListEvent.HideDeclineInviteMenu))
    }
}
