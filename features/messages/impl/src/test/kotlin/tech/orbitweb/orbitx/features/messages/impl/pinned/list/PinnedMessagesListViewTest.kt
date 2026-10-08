/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.messages.impl.pinned.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.messages.impl.actionlist.ActionListEvent
import tech.orbitweb.orbitx.features.messages.impl.actionlist.anActionListState
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemList
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemFileContent
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithTwoParams
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import tech.orbitweb.orbitx.tests.testutils.setSafeContent
import io.element.android.wysiwyg.link.Link
import org.junit.Test

class PinnedMessagesListViewTest : RobolectricTest() {
    @Test
    fun `clicking on back calls the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<PinnedMessagesListEvent>(expectEvents = false)
        val state = aLoadedPinnedMessagesListState(
            eventSink = eventsRecorder
        )
        ensureCalledOnce { callback ->
            setPinnedMessagesListView(
                state = state,
                onBackClick = callback
            )
            pressBack()
        }
    }

    @Test
    fun `click on an event calls the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<PinnedMessagesListEvent>(expectEvents = false)
        val content = aTimelineItemFileContent()
        val state = aLoadedPinnedMessagesListState(
            timelineItems = aTimelineItemList(content),
            eventSink = eventsRecorder
        )

        val event = state.timelineItems.first() as TimelineItem.Event
        ensureCalledOnceWithParam(event) { callback ->
            setPinnedMessagesListView(
                state = state,
                onEventClick = callback
            )
            onAllNodesWithText(content.filename).onFirst().performClick()
        }
    }

    @Test
    fun `long click on an event emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ActionListEvent>(expectEvents = true)
        val content = aTimelineItemFileContent()
        val state = aLoadedPinnedMessagesListState(
            timelineItems = aTimelineItemList(content),
            actionListState = anActionListState(eventSink = eventsRecorder)
        )

        setPinnedMessagesListView(
            state = state,
        )
        onAllNodesWithText(content.filename).onFirst()
            .performTouchInput {
                longClick()
            }
        val event = state.timelineItems.first() as TimelineItem.Event
        eventsRecorder.assertSingle(ActionListEvent.ComputeForMessage(event, state.userEventPermissions))
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setPinnedMessagesListView(
    state: PinnedMessagesListState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
    onEventClick: (event: TimelineItem.Event) -> Unit = EnsureNeverCalledWithParam(),
    onGalleryItemClick: (event: TimelineItem.Event, index: Int) -> Unit = EnsureNeverCalledWithTwoParams(),
    onUserDataClick: (MatrixUser) -> Unit = EnsureNeverCalledWithParam(),
    onLinkClick: (Link) -> Unit = EnsureNeverCalledWithParam(),
    onLinkLongClick: (Link) -> Unit = EnsureNeverCalledWithParam(),
) {
    setSafeContent(clearAndroidUiDispatcher = true) {
        PinnedMessagesListView(
            state = state,
            onBackClick = onBackClick,
            onEventClick = onEventClick,
            onGalleryItemClick = onGalleryItemClick,
            onUserDataClick = onUserDataClick,
            onLinkClick = onLinkClick,
            onLinkLongClick = onLinkLongClick,
        )
    }
}
