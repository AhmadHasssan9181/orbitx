/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.roomdirectory.impl.root

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.features.roomdirectory.api.RoomDescription
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class RoomDirectoryViewTest : RobolectricTest() {
    @Test
    fun `typing text in search field emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomDirectoryEvents>()
        setRoomDirectoryView(
            state = aRoomDirectoryState(
                eventSink = eventsRecorder,
            )
        )
        onNodeWithTag(TestTags.searchTextField.value).performTextInput(
            text = "Test"
        )
        eventsRecorder.assertSingle(RoomDirectoryEvents.Search("Test"))
    }

    @Test
    fun `clicking on room item then onResultClick lambda is called once`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomDirectoryEvents>()
        val state = aRoomDirectoryState(
            roomDescriptions = aRoomDescriptionList(),
            eventSink = eventsRecorder,
        )
        val clickedRoom = state.roomDescriptions.first()
        ensureCalledOnceWithParam(clickedRoom) { callback ->
            setRoomDirectoryView(
                state = state,
                onResultClick = callback,
            )
            onNodeWithText(clickedRoom.computedName).performClick()
        }
    }

    @Test
    fun `composing load more indicator emits expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomDirectoryEvents>()
        val state = aRoomDirectoryState(
            displayLoadMoreIndicator = true,
            eventSink = eventsRecorder,
        )
        setRoomDirectoryView(state = state)
        eventsRecorder.assertSingle(RoomDirectoryEvents.LoadMore)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setRoomDirectoryView(
    state: RoomDirectoryState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
    onResultClick: (RoomDescription) -> Unit = EnsureNeverCalledWithParam(),
) {
    setContent {
        RoomDirectoryView(
            state = state,
            onResultClick = onResultClick,
            onBackClick = onBackClick,
        )
    }
}
