/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.libraries.troubleshoot.impl

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import org.robolectric.annotation.Config
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class TroubleshootNotificationsViewTest : RobolectricTest() {
    @Test
    fun `press menu back invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<TroubleshootNotificationsEvents>(expectEvents = false)
        ensureCalledOnce {
            setTroubleshootNotificationsView(
                state = aTroubleshootNotificationsState(
                    eventSink = eventsRecorder
                ),
                onBackClick = it,
            )
            pressBack()
        }
    }

    @Test
    fun `clicking on run test emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<TroubleshootNotificationsEvents>()
        setTroubleshootNotificationsView(
            aTroubleshootNotificationsState(
                eventSink = eventsRecorder
            ),
        )
        onNodeWithText("Run tests").performClick()
        eventsRecorder.assertSingle(TroubleshootNotificationsEvents.StartTests)
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `clicking on run test again emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<TroubleshootNotificationsEvents>()
        setTroubleshootNotificationsView(
            aTroubleshootNotificationsState(
                tests = listOf(
                    aTroubleshootTestStateFailure(
                        hasQuickFix = false
                    )
                ),
                eventSink = eventsRecorder
            ),
        )
        onNodeWithText("Run tests again").performClick()
        eventsRecorder.assertList(
            listOf(
                TroubleshootNotificationsEvents.RetryFailedTests,
                TroubleshootNotificationsEvents.StartTests,
            )
        )
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `clicking on quick fix emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<TroubleshootNotificationsEvents>()
        setTroubleshootNotificationsView(
            aTroubleshootNotificationsState(
                tests = listOf(
                    aTroubleshootTestStateFailure(
                        hasQuickFix = true
                    )
                ),
                eventSink = eventsRecorder
            ),
        )
        onNodeWithText("Attempt to fix").performClick()
        eventsRecorder.assertList(
            listOf(
                TroubleshootNotificationsEvents.RetryFailedTests,
                TroubleshootNotificationsEvents.QuickFix(0),
            )
        )
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setTroubleshootNotificationsView(
    state: TroubleshootNotificationsState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        TroubleshootNotificationsView(
            state = state,
            onBackClick = onBackClick,
        )
    }
}
