/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.securebackup.impl.enter

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.securebackup.impl.setup.views.aFormattedRecoveryKey
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.pressBackKey
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test
import org.robolectric.annotation.Config

class SecureBackupEnterRecoveryKeyViewTest : RobolectricTest() {
    @Test
    fun `back key pressed - calls onBackClick`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setSecureBackupEnterRecoveryKeyView(
                aSecureBackupEnterRecoveryKeyState(),
                onBackClick = callback,
            )
            pressBackKey()
        }
    }

    @Test
    fun `back button clicked - calls onBackClick`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setSecureBackupEnterRecoveryKeyView(
                aSecureBackupEnterRecoveryKeyState(),
                onBackClick = callback,
            )
            pressBack()
        }
    }

    @Test
    @Config(qualifiers = "h1024dp")
    fun `tapping on Continue when key is valid - calls expected action`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<SecureBackupEnterRecoveryKeyEvents>()
        setSecureBackupEnterRecoveryKeyView(
            aSecureBackupEnterRecoveryKeyState(isSubmitEnabled = true, eventSink = recorder),
        )
        clickOn(CommonStrings.action_continue)

        recorder.assertSingle(SecureBackupEnterRecoveryKeyEvents.Submit)
    }

    @Test
    fun `entering a char emits the expected event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<SecureBackupEnterRecoveryKeyEvents>()
        val keyValue = aFormattedRecoveryKey()
        setSecureBackupEnterRecoveryKeyView(
            aSecureBackupEnterRecoveryKeyState(isSubmitEnabled = true, eventSink = recorder),
        )
        onNodeWithText(keyValue).performTextInput("X")
        recorder.assertSingle(
            SecureBackupEnterRecoveryKeyEvents.OnRecoveryKeyChange("X$keyValue")
        )
    }

    @Test
    @Config(qualifiers = "h1024dp")
    fun `toggling the visibility of the textfield changes it`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<SecureBackupEnterRecoveryKeyEvents>()
        val keyValue = aFormattedRecoveryKey()
        setSecureBackupEnterRecoveryKeyView(aSecureBackupEnterRecoveryKeyState(isSubmitEnabled = true, eventSink = recorder))

        // Initially, the text field should be visible
        onNodeWithText(keyValue).assertExists()

        onNodeWithContentDescription(activity!!.getString(CommonStrings.a11y_hide_password)).performClick()

        waitForIdle()

        recorder.assertSingle(SecureBackupEnterRecoveryKeyEvents.ChangeRecoveryKeyFieldContentsVisibility(false))
    }

    @Test
    fun `validating from keyboard emits the expected event`() = runAndroidComposeUiTest {
        val recorder = EventsRecorder<SecureBackupEnterRecoveryKeyEvents>()
        val keyValue = aFormattedRecoveryKey()
        setSecureBackupEnterRecoveryKeyView(
            aSecureBackupEnterRecoveryKeyState(isSubmitEnabled = true, eventSink = recorder),
        )
        onNodeWithText(keyValue).performImeAction()
        recorder.assertSingle(SecureBackupEnterRecoveryKeyEvents.Submit)
    }

    @Test
    fun `when submit action succeeds - calls onDone`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setSecureBackupEnterRecoveryKeyView(
                aSecureBackupEnterRecoveryKeyState(submitAction = AsyncAction.Success(Unit)),
                onDone = callback,
            )
        }
    }

    private fun AndroidComposeUiTest<ComponentActivity>.setSecureBackupEnterRecoveryKeyView(
        state: SecureBackupEnterRecoveryKeyState,
        onDone: () -> Unit = EnsureNeverCalled(),
        onBackClick: () -> Unit = EnsureNeverCalled(),
    ) {
        setContent {
            SecureBackupEnterRecoveryKeyView(
                state = state,
                onSuccess = onDone,
                onBackClick = onBackClick,
            )
        }
    }
}
