/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.knockrequests.impl.banner

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.features.knockrequests.impl.R
import tech.orbitweb.orbitx.features.knockrequests.impl.data.aKnockRequestPresentable
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class KnockRequestsBannerViewTest : RobolectricTest() {
    @Test
    fun `clicking on view on single request invoke the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsBannerEvents>(expectEvents = false)
        ensureCalledOnce {
            setKnockRequestsBannerView(
                state = aKnockRequestsBannerState(
                    eventSink = eventsRecorder,
                ),
                onViewRequestsClick = it
            )
            clickOn(R.string.screen_room_single_knock_request_view_button_title)
        }
    }

    @Test
    fun `clicking on view all when multiple requests invoke the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsBannerEvents>(expectEvents = false)
        ensureCalledOnce {
            setKnockRequestsBannerView(
                state = aKnockRequestsBannerState(
                    knockRequests = listOf(
                        aKnockRequestPresentable(displayName = "Alice"),
                        aKnockRequestPresentable(displayName = "Bob"),
                        aKnockRequestPresentable(displayName = "Charlie")
                    ),
                    eventSink = eventsRecorder,
                ),
                onViewRequestsClick = it
            )
            clickOn(R.string.screen_room_multiple_knock_requests_view_all_button_title)
        }
    }

    @Test
    fun `clicking on accept on a single request emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsBannerEvents>()
        setKnockRequestsBannerView(
            state = aKnockRequestsBannerState(
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_accept)
        eventsRecorder.assertSingle(KnockRequestsBannerEvents.AcceptSingleRequest)
    }

    @Test
    fun `clicking on dismiss emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsBannerEvents>()
        setKnockRequestsBannerView(
            state = aKnockRequestsBannerState(
                eventSink = eventsRecorder,
            ),
        )
        val close = activity!!.getString(CommonStrings.action_close)
        onNodeWithContentDescription(close).performClick()
        eventsRecorder.assertSingle(KnockRequestsBannerEvents.Dismiss)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setKnockRequestsBannerView(
    state: KnockRequestsBannerState,
    onViewRequestsClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        KnockRequestsBannerView(
            state = state,
            onViewRequestsClick = onViewRequestsClick,
        )
    }
}
