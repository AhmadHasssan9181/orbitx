/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.announcement.impl.fullscreen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.announcement.api.Announcement
import tech.orbitweb.orbitx.features.announcement.impl.AnnouncementEvent
import tech.orbitweb.orbitx.features.announcement.impl.AnnouncementState
import tech.orbitweb.orbitx.features.announcement.impl.anAnnouncementState
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.pressBackKey
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class FullscreenAnnouncementViewTest : RobolectricTest() {
    @Test
    fun `clicking on back sends a AnnouncementEvent`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AnnouncementEvent>()
        setFullscreenAnnouncementView(
            anAnnouncementState(
                announcement = Announcement.Fullscreen.Space,
                eventSink = eventsRecorder,
            ),
        )
        pressBackKey()
        eventsRecorder.assertSingle(AnnouncementEvent.Continue(Announcement.Fullscreen.Space))
    }

    @Test
    fun `clicking on Continue sends a AnnouncementEvent`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AnnouncementEvent>()
        setFullscreenAnnouncementView(
            anAnnouncementState(
                announcement = Announcement.Fullscreen.Space,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_continue)
        eventsRecorder.assertSingle(AnnouncementEvent.Continue(Announcement.Fullscreen.Space))
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setFullscreenAnnouncementView(
    state: AnnouncementState,
) {
    setContent {
        FullscreenAnnouncementView(
            state = state,
        )
    }
}
