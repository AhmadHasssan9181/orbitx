/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemPollContent
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.pressTag
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class TimelineItemPollViewTest : RobolectricTest() {
    @Test
    fun `answering a poll with first answer should emit a PollAnswerSelected event`() {
        testAnswer(answerIndex = 0)
    }

    @Test
    fun `answering a poll with second answer should emit a PollAnswerSelected event`() {
        testAnswer(answerIndex = 1)
    }

    private fun testAnswer(answerIndex: Int) = runAndroidComposeUiTest<ComponentActivity> {
        val eventsRecorder = EventsRecorder<TimelineEvent.TimelineItemPollEvent>()
        val content = aTimelineItemPollContent()
        setContent {
            TimelineItemPollView(
                content = content,
                eventSink = eventsRecorder
            )
        }
        val answer = content.answerItems[answerIndex].answer
        onNode(
            matcher = hasText(answer.text),
            useUnmergedTree = true,
        ).performClick()
        eventsRecorder.assertSingle(TimelineEvent.SelectPollAnswer(content.eventId!!, answer.id))
    }

    @Test
    fun `editing a poll should emit a PollEditClicked event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<TimelineEvent.TimelineItemPollEvent>()
        val content = aTimelineItemPollContent(
            isMine = true,
            isEditable = true,
        )
        setContent {
            TimelineItemPollView(
                content = content,
                eventSink = eventsRecorder
            )
        }
        clickOn(CommonStrings.action_edit_poll)
        eventsRecorder.assertSingle(TimelineEvent.EditPoll(content.eventId!!))
    }

    @Test
    fun `closing a poll should emit a PollEndClicked event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<TimelineEvent.TimelineItemPollEvent>()
        val content = aTimelineItemPollContent(
            isMine = true,
        )
        setContent {
            TimelineItemPollView(
                content = content,
                eventSink = eventsRecorder
            )
        }
        clickOn(CommonStrings.action_end_poll)
        // A confirmation dialog should be shown
        eventsRecorder.assertEmpty()
        pressTag(TestTags.dialogPositive.value)
        eventsRecorder.assertSingle(TimelineEvent.EndPoll(content.eventId!!))
    }
}
