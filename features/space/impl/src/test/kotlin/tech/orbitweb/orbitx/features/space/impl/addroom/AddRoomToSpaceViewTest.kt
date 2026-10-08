/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.space.impl.addroom

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import kotlinx.collections.immutable.toImmutableList
import org.junit.Test
import org.robolectric.annotation.Config
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.SearchBarResultState
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class AddRoomToSpaceViewTest : RobolectricTest() {
    @Test
    fun `clicking back when search inactive emits Dismiss and invokes onBackClick`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AddRoomToSpaceEvent>()
        ensureCalledOnce {
            setAddRoomToSpaceView(
                anAddRoomToSpaceState(
                    isSearchActive = false,
                    eventSink = eventsRecorder,
                ),
                onBackClick = it,
            )
            pressBack()
        }
        eventsRecorder.assertSingle(AddRoomToSpaceEvent.Dismiss)
    }

    @Test
    fun `clicking back when search active emits CloseSearch event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AddRoomToSpaceEvent>()
        setAddRoomToSpaceView(
            anAddRoomToSpaceState(
                isSearchActive = true,
                eventSink = eventsRecorder,
            ),
        )
        pressBack()
        eventsRecorder.assertSingle(AddRoomToSpaceEvent.OnSearchActiveChanged(false))
    }

    @Test
    fun `clicking save emits Save event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AddRoomToSpaceEvent>()
        setAddRoomToSpaceView(
            anAddRoomToSpaceState(
                selectedRooms = aSelectRoomInfoList().take(1).toImmutableList(),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_save)
        eventsRecorder.assertSingle(AddRoomToSpaceEvent.Save)
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `clicking room in suggestions emits ToggleRoom event`() = runAndroidComposeUiTest {
        val suggestions = aSelectRoomInfoList()
        val eventsRecorder = EventsRecorder<AddRoomToSpaceEvent>()
        setAddRoomToSpaceView(
            anAddRoomToSpaceState(
                suggestions = suggestions,
                eventSink = eventsRecorder,
            ),
        )
        onNodeWithText(suggestions.first().name!!).performClick()
        eventsRecorder.assertSingle(AddRoomToSpaceEvent.ToggleRoom(suggestions.first()))
    }

    @Test
    fun `onRoomsAdded called when saveAction is Success`() = runAndroidComposeUiTest {
        ensureCalledOnce {
            setAddRoomToSpaceView(
                anAddRoomToSpaceState(
                    saveAction = AsyncAction.Success(Unit),
                ),
                onRoomsAdded = it,
            )
        }
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `displaying search results sends UpdateSearchVisibleRange event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AddRoomToSpaceEvent>()
        val rooms = aSelectRoomInfoList()
        setAddRoomToSpaceView(
            anAddRoomToSpaceState(
                isSearchActive = true,
                searchResults = SearchBarResultState.Results(rooms),
                eventSink = eventsRecorder,
            ),
        )
        eventsRecorder.assertTrue(0) { it is AddRoomToSpaceEvent.UpdateSearchVisibleRange }
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setAddRoomToSpaceView(
    state: AddRoomToSpaceState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
    onRoomsAdded: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        AddRoomToSpaceView(
            state = state,
            onBackClick = onBackClick,
            onAddRoom = onRoomsAdded,
        )
    }
}
