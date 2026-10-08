/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.logout.impl.direct

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Ignore
import org.junit.Test
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutEvents
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutState
import tech.orbitweb.orbitx.features.logout.api.direct.aDirectLogoutState
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.pressBackKey
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class DefaultDirectLogoutViewTest : RobolectricTest() {
    @Test
    fun `clicking on confirm logout sends expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DirectLogoutEvents>()
        setDefaultDirectLogoutView(
            state = aDirectLogoutState(
                logoutAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder,
            )
        )
        clickOn(CommonStrings.action_signout)
        eventsRecorder.assertSingle(DirectLogoutEvents.Logout(false))
    }

    @Test
    fun `clicking on cancel logout sends expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DirectLogoutEvents>()
        setDefaultDirectLogoutView(
            state = aDirectLogoutState(
                logoutAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder,
            )
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(DirectLogoutEvents.CloseDialogs)
    }

    @Ignore("Pressing back key should dismiss the dialog, and so generate the expected event, but it's not the case.")
    @Test
    fun `clicking on back invoke back callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DirectLogoutEvents>()
        setDefaultDirectLogoutView(
            state = aDirectLogoutState(
                logoutAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder,
            )
        )
        pressBackKey()
        eventsRecorder.assertSingle(DirectLogoutEvents.CloseDialogs)
    }

    @Test
    fun `clicking on confirm after error sends expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DirectLogoutEvents>()
        setDefaultDirectLogoutView(
            state = aDirectLogoutState(
                logoutAction = AsyncAction.Failure(Exception("Error")),
                eventSink = eventsRecorder,
            )
        )
        clickOn(CommonStrings.action_signout_anyway)
        eventsRecorder.assertSingle(DirectLogoutEvents.Logout(true))
    }

    @Test
    fun `clicking on cancel after error sends expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<DirectLogoutEvents>()
        setDefaultDirectLogoutView(
            state = aDirectLogoutState(
                logoutAction = AsyncAction.Failure(Exception("Error")),
                eventSink = eventsRecorder,
            )
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(DirectLogoutEvents.CloseDialogs)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setDefaultDirectLogoutView(
    state: DirectLogoutState,
) {
    setContent {
        DefaultDirectLogoutView().Render(state)
    }
}
