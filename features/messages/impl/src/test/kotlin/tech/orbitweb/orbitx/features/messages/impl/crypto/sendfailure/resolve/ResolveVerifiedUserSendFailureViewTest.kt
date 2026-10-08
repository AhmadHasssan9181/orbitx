/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.resolve

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import tech.orbitweb.orbitx.tests.testutils.setSafeContent
import org.junit.Test

class ResolveVerifiedUserSendFailureViewTest : RobolectricTest() {
    @Test
    fun `clicking on resolve and resend emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ResolveVerifiedUserSendFailureEvent>()
        setResolveVerifiedUserSendFailureView(
            state = aResolveVerifiedUserSendFailureState(
                verifiedUserSendFailure = aChangedIdentitySendFailure(),
                eventSink = eventsRecorder,
            ),
        )

        clickOn(res = CommonStrings.screen_resolve_send_failure_changed_identity_primary_button_title)
        eventsRecorder.assertSingle(ResolveVerifiedUserSendFailureEvent.ResolveAndResend)
    }

    @Test
    fun `clicking on retry emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ResolveVerifiedUserSendFailureEvent>()
        setResolveVerifiedUserSendFailureView(
            state = aResolveVerifiedUserSendFailureState(
                verifiedUserSendFailure = aChangedIdentitySendFailure(),
                eventSink = eventsRecorder,
            ),
        )

        clickOn(res = CommonStrings.action_retry)
        eventsRecorder.assertSingle(ResolveVerifiedUserSendFailureEvent.Retry)
    }

    private fun AndroidComposeUiTest<ComponentActivity>.setResolveVerifiedUserSendFailureView(
        state: ResolveVerifiedUserSendFailureState,
    ) {
        setSafeContent {
            ResolveVerifiedUserSendFailureView(state = state)
        }
    }
}
