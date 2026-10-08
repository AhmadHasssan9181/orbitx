/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.invite.impl.declineandblock

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.invite.impl.R
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class DeclineAndBlockViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invoke the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeclineAndBlockEvents>(expectEvents = false)
        ensureCalledOnce {
            setDeclineAndBlockView(
                aDeclineAndBlockState(
                    eventSink = eventsRecorder,
                ),
                onBackClick = it
            )
            pressBack()
        }
    }

    @Test
    fun `clicking on decline when enabled emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeclineAndBlockEvents>()
        setDeclineAndBlockView(
            aDeclineAndBlockState(
                blockUser = true,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_decline)
        eventsRecorder.assertSingle(DeclineAndBlockEvents.Decline)
    }

    @Test
    fun `clicking on decline when disabled does not emit event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeclineAndBlockEvents>(expectEvents = false)
        setDeclineAndBlockView(
            aDeclineAndBlockState(
                blockUser = false,
                reportRoom = false,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_decline)
        eventsRecorder.assertEmpty()
    }

    @Test
    fun `clicking on block option emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeclineAndBlockEvents>()
        setDeclineAndBlockView(
            aDeclineAndBlockState(
                blockUser = true,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_decline_and_block_block_user_option_title)
        eventsRecorder.assertSingle(DeclineAndBlockEvents.ToggleBlockUser)
    }

    @Test
    fun `clicking on report room option emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeclineAndBlockEvents>()
        setDeclineAndBlockView(
            aDeclineAndBlockState(
                reportRoom = true,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_report_room)
        eventsRecorder.assertSingle(DeclineAndBlockEvents.ToggleReportRoom)
    }

    @Test
    fun `typing text in the reason field emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeclineAndBlockEvents>()
        setDeclineAndBlockView(
            aDeclineAndBlockState(
                reportRoom = true,
                reportReason = "",
                eventSink = eventsRecorder,
            ),
        )
        onNodeWithText("").performTextInput("Spam!")
        eventsRecorder.assertSingle(DeclineAndBlockEvents.UpdateReportReason("Spam!"))
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setDeclineAndBlockView(
    state: DeclineAndBlockState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        DeclineAndBlockView(
            state = state,
            onBackClick = onBackClick,
        )
    }
}
