/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.startchat.impl.joinbyaddress

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.features.startchat.impl.R
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import tech.orbitweb.orbitx.tests.testutils.setSafeContent

class JoinBaseRoomByAddressViewTest : RobolectricTest() {
    @Test
    fun `entering text emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomByAddressEvent>()
        setJoinRoomByAddressView(
            aJoinRoomByAddressState(
                eventSink = eventsRecorder,
            )
        )
        val text = activity!!.getString(R.string.screen_start_chat_join_room_by_address_action)
        onNodeWithText(text).performTextInput("#address:matrix.org")
        eventsRecorder.assertSingle(JoinRoomByAddressEvent.UpdateAddress("#address:matrix.org"))
    }

    @Test
    fun `clicking on continue emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomByAddressEvent>()
        setJoinRoomByAddressView(
            aJoinRoomByAddressState(
                eventSink = eventsRecorder,
            )
        )
        clickOn(CommonStrings.action_continue)
        eventsRecorder.assertSingle(JoinRoomByAddressEvent.Continue)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setJoinRoomByAddressView(
    state: JoinRoomByAddressState,
) {
    setSafeContent {
        JoinRoomByAddressView(state = state)
    }
}
