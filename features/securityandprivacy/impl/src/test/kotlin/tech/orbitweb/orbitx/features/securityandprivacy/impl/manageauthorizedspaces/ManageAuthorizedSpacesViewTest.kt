/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.securityandprivacy.impl.manageauthorizedspaces

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.previewutils.room.aSpaceRoom
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import org.junit.Test

class ManageAuthorizedSpacesViewTest : RobolectricTest() {
    @Test
    fun `clicking back emits Cancel event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<ManageAuthorizedSpacesEvent>()
        val state = aManageAuthorizedSpacesState(eventSink = recorder)
        setManageAuthorizedSpacesView(state)
        pressBack()
        recorder.assertSingle(ManageAuthorizedSpacesEvent.Cancel)
    }

    @Test
    fun `clicking space checkbox emits ToggleSpace event`() = runAndroidComposeUiTest {
        val roomId = A_ROOM_ID
        val space = aSpaceRoom(roomId = roomId, displayName = "Test Space")
        val recorder = EventsRecorder<ManageAuthorizedSpacesEvent>()
        val state = aManageAuthorizedSpacesState(
            selectableSpaces = listOf(space),
            eventSink = recorder
        )
        setManageAuthorizedSpacesView(state)
        onNodeWithText("Test Space").performClick()
        recorder.assertSingle(ManageAuthorizedSpacesEvent.ToggleSpace(roomId))
    }

    @Test
    fun `clicking done button emits Done event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<ManageAuthorizedSpacesEvent>()
        val state = aManageAuthorizedSpacesState(
            selectedIds = listOf(A_ROOM_ID),
            eventSink = recorder
        )
        setManageAuthorizedSpacesView(state)
        clickOn(CommonStrings.action_done)
        recorder.assertSingle(ManageAuthorizedSpacesEvent.Done)
    }

    @Test
    fun `done button is disabled when no spaces selected`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<ManageAuthorizedSpacesEvent>(expectEvents = false)
        val state = aManageAuthorizedSpacesState(
            selectedIds = emptyList(),
            eventSink = recorder
        )
        setManageAuthorizedSpacesView(state)
        clickOn(CommonStrings.action_done)
        recorder.assertEmpty()
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setManageAuthorizedSpacesView(
    state: ManageAuthorizedSpacesState = aManageAuthorizedSpacesState(
        eventSink = EventsRecorder(expectEvents = false)
    ),
) {
    setContent {
        ManageAuthorizedSpacesView(state = state)
    }
}

private fun aManageAuthorizedSpacesState(
    selectableSpaces: List<SpaceRoom> = emptyList(),
    unknownSpaceIds: List<RoomId> = emptyList(),
    selectedIds: List<RoomId> = emptyList(),
    eventSink: (ManageAuthorizedSpacesEvent) -> Unit = {},
) = ManageAuthorizedSpacesState(
    selectableSpaces = selectableSpaces.toImmutableSet(),
    unknownSpaceIds = unknownSpaceIds.toImmutableList(),
    selectedIds = selectedIds.toImmutableList(),
    eventSink = eventSink,
)
