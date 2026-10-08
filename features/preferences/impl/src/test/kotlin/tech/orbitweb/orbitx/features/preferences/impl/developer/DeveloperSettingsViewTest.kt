/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.preferences.impl.developer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import org.robolectric.annotation.Config
import tech.orbitweb.orbitx.features.preferences.impl.R
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class DeveloperSettingsViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeveloperSettingsEvents>(expectEvents = false)
        ensureCalledOnce {
            setDeveloperSettingsView(
                state = aDeveloperSettingsState(
                    eventSink = eventsRecorder
                ),
                onBackClick = it
            )
            pressBack()
        }
    }

    @Config(qualifiers = "h2000dp")
    @Test
    fun `clicking on push history notification invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeveloperSettingsEvents>(expectEvents = false)
        ensureCalledOnce {
            setDeveloperSettingsView(
                state = aDeveloperSettingsState(
                    eventSink = eventsRecorder
                ),
                onPushHistoryClick = it
            )
            clickOn(R.string.troubleshoot_notifications_entry_point_push_history_title)
        }
    }

    @Config(qualifiers = "h2000dp")
    @Test
    fun `clicking on open showkase invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeveloperSettingsEvents>(expectEvents = false)
        ensureCalledOnce {
            setDeveloperSettingsView(
                state = aDeveloperSettingsState(
                    eventSink = eventsRecorder
                ),
                onOpenShowkase = it
            )
            onNodeWithText("Open Showkase browser").performClick()
        }
    }

    @Config(qualifiers = "h2400dp")
    @Test
    fun `clicking on clear cache emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DeveloperSettingsEvents>()
        setDeveloperSettingsView(
            state = aDeveloperSettingsState(
                eventSink = eventsRecorder
            ),
        )
        onNodeWithText("Clear cache").performClick()
        eventsRecorder.assertSingle(DeveloperSettingsEvents.ClearCache)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setDeveloperSettingsView(
    state: DeveloperSettingsState,
    onOpenShowkase: () -> Unit = EnsureNeverCalled(),
    onPushHistoryClick: () -> Unit = EnsureNeverCalled(),
    onBackClick: () -> Unit = EnsureNeverCalled()
) {
    setContent {
        DeveloperSettingsView(
            state = state,
            onOpenShowkase = onOpenShowkase,
            onPushHistoryClick = onPushHistoryClick,
            onBackClick = onBackClick,
        )
    }
}
