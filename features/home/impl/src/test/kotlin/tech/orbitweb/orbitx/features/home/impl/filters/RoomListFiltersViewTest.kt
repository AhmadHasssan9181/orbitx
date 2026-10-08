/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.home.impl.filters

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.features.home.impl.R
import tech.orbitweb.orbitx.features.home.impl.filters.selection.FilterSelectionState
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.pressTag
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class RoomListFiltersViewTest : RobolectricTest() {
    @Test
    fun `clicking on filters generates expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomListFiltersEvent>()
        setContent {
            RoomListFiltersView(
                state = aRoomListFiltersState(eventSink = eventsRecorder),
            )
        }
        clickOn(R.string.screen_roomlist_filter_rooms)
        eventsRecorder.assertList(
            listOf(
                RoomListFiltersEvent.ToggleFilter(RoomListFilter.Rooms),
            )
        )
    }

    @Test
    fun `clicking on clear filters generates expected Event`() = runAndroidComposeUiTest<ComponentActivity> {
        val eventsRecorder = EventsRecorder<RoomListFiltersEvent>()
        setContent {
            RoomListFiltersView(
                state = aRoomListFiltersState(
                    filterSelectionStates = RoomListFilter.entries.map { FilterSelectionState(it, isSelected = true) },
                    eventSink = eventsRecorder
                ),
            )
        }
        pressTag(TestTags.homeScreenClearFilters.value)
        eventsRecorder.assertList(
            listOf(
                RoomListFiltersEvent.ClearSelectedFilters,
            )
        )
    }
}
