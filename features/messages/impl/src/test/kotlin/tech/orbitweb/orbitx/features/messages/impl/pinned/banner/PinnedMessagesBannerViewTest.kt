/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.messages.impl.pinned.banner

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class PinnedMessagesBannerViewTest : RobolectricTest() {
    @Test
    fun `clicking on the banner invoke expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<PinnedMessagesBannerEvent>()
        val state = aLoadedPinnedMessagesBannerState(
            eventSink = eventsRecorder
        )
        val pinnedEventId = state.currentPinnedMessage.eventId
        ensureCalledOnceWithParam(pinnedEventId) { callback ->
            setPinnedMessagesBannerView(
                state = state,
                onClick = callback
            )
            onRoot().performClick()
            eventsRecorder.assertSingle(PinnedMessagesBannerEvent.MoveToNextPinned)
        }
    }

    @Test
    fun `clicking on view all emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<PinnedMessagesBannerEvent>(expectEvents = true)
        val state = aLoadedPinnedMessagesBannerState(
            eventSink = eventsRecorder
        )
        ensureCalledOnce { callback ->
            setPinnedMessagesBannerView(
                state = state,
                onViewAllClick = callback
            )
            clickOn(CommonStrings.screen_room_pinned_banner_view_all_button_title)
        }
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setPinnedMessagesBannerView(
    state: PinnedMessagesBannerState,
    onClick: (EventId) -> Unit = EnsureNeverCalledWithParam(),
    onViewAllClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        PinnedMessagesBannerView(
            state = state,
            onClick = onClick,
            onViewAllClick = onViewAllClick
        )
    }
}
