/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.intro

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.login.impl.R
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.pressBackKey
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class QrCodeIntroViewTest : RobolectricTest() {
    @Test
    fun `on back pressed - calls the expected callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setQrCodeIntroView(
                state = aQrCodeIntroState(),
                onBackClicked = callback
            )
            pressBackKey()
        }
    }

    @Test
    fun `on back button clicked - calls the expected callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setQrCodeIntroView(
                state = aQrCodeIntroState(),
                onBackClicked = callback
            )
            pressBack()
        }
    }

    @Test
    fun `when can continue - calls the expected callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setQrCodeIntroView(
                state = aQrCodeIntroState(canContinue = true),
                onContinue = callback
            )
        }
    }

    @Test
    fun `on submit button clicked - emits the Continue event`() = runAndroidComposeUiTest {
        val eventRecorder = EventsRecorder<QrCodeIntroEvents>()
        setQrCodeIntroView(
            state = aQrCodeIntroState(eventSink = eventRecorder),
        )
        clickOn(R.string.screen_qr_code_login_initial_state_button_title)
        eventRecorder.assertSingle(QrCodeIntroEvents.Continue)
    }

    private fun AndroidComposeUiTest<ComponentActivity>.setQrCodeIntroView(
        state: QrCodeIntroState,
        onBackClicked: () -> Unit = EnsureNeverCalled(),
        onContinue: () -> Unit = EnsureNeverCalled(),
    ) {
        setContent {
            QrCodeIntroView(
                state = state,
                onBackClick = onBackClicked,
                onContinue = onContinue,
            )
        }
    }
}
