/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.securityandprivacy.impl.editroomaddress

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.ui.room.address.RoomAddressValidity
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class EditRoomAddressViewTest : RobolectricTest() {
    @Test
    fun `click on back invokes expected callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setEditRoomAddressView(onBackClick = callback)
            pressBack()
        }
    }

    @Test
    fun `click on disabled save doesn't emit event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<EditRoomAddressEvents>(expectEvents = false)
        val state = anEditRoomAddressState(eventSink = recorder)
        setEditRoomAddressView(state)
        clickOn(CommonStrings.action_save)
        recorder.assertEmpty()
    }

    @Test
    fun `click on enabled save emits the expected event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<EditRoomAddressEvents>()
        val state = anEditRoomAddressState(
            roomAddress = "room",
            roomAddressValidity = RoomAddressValidity.Valid,
            eventSink = recorder
        )
        setEditRoomAddressView(state)
        clickOn(CommonStrings.action_save)
        recorder.assertSingle(EditRoomAddressEvents.Save)
    }

    @Test
    fun `text changes on text field emits the expected event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<EditRoomAddressEvents>()
        val state = anEditRoomAddressState(
            roomAddress = "",
            eventSink = recorder
        )
        setEditRoomAddressView(state)

        onNodeWithTag(TestTags.roomAddressField.value).performTextInput("alias")
        recorder.assertSingle(EditRoomAddressEvents.RoomAddressChanged("alias"))
    }

    @Test
    fun `click on dismiss error emits the expected event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<EditRoomAddressEvents>()
        val state = anEditRoomAddressState(
            roomAddress = "",
            saveAction = AsyncAction.Failure(IllegalStateException()),
            eventSink = recorder
        )
        setEditRoomAddressView(state)
        clickOn(CommonStrings.action_cancel)
        recorder.assertSingle(EditRoomAddressEvents.DismissError)
    }

    @Test
    fun `click on retry error emits the expected event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<EditRoomAddressEvents>()
        val state = anEditRoomAddressState(
            roomAddress = "",
            saveAction = AsyncAction.Failure(IllegalStateException()),
            eventSink = recorder
        )
        setEditRoomAddressView(state)
        clickOn(CommonStrings.action_retry)
        recorder.assertSingle(EditRoomAddressEvents.Save)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setEditRoomAddressView(
    state: EditRoomAddressState = anEditRoomAddressState(
        eventSink = EventsRecorder(expectEvents = false),
    ),
    onBackClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        EditRoomAddressView(
            state = state,
            onBackClick = onBackClick,
        )
    }
}
