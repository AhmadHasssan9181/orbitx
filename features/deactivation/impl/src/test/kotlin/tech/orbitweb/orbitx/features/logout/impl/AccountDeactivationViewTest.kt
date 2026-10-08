/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.logout.impl

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import org.robolectric.annotation.Config
import tech.orbitweb.orbitx.features.deactivation.impl.R
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EXCEPTION
import tech.orbitweb.orbitx.libraries.matrix.test.A_PASSWORD
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.pressTag
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class AccountDeactivationViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AccountDeactivationEvents>(expectEvents = false)
        ensureCalledOnce {
            setAccountDeactivationView(
                state = anAccountDeactivationState(eventSink = eventsRecorder),
                onBackClick = it,
            )
            pressBack()
        }
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `clicking on Deactivate emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AccountDeactivationEvents>()
        setAccountDeactivationView(
            state = anAccountDeactivationState(
                deactivateFormState = aDeactivateFormState(
                    password = A_PASSWORD,
                ),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_delete)
        eventsRecorder.assertSingle(AccountDeactivationEvents.DeactivateAccount(false))
    }

    @Test
    fun `clicking on Deactivate on the confirmation dialog emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AccountDeactivationEvents>()
        setAccountDeactivationView(
            state = anAccountDeactivationState(
                deactivateFormState = aDeactivateFormState(
                    password = A_PASSWORD,
                ),
                accountDeactivationAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder,
            ),
        )
        pressTag(TestTags.dialogPositive.value)
        eventsRecorder.assertSingle(AccountDeactivationEvents.DeactivateAccount(false))
    }

    @Test
    fun `clicking on retry on the confirmation dialog emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AccountDeactivationEvents>()
        setAccountDeactivationView(
            state = anAccountDeactivationState(
                deactivateFormState = aDeactivateFormState(
                    password = A_PASSWORD,
                ),
                accountDeactivationAction = AsyncAction.Failure(AN_EXCEPTION),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_retry)
        eventsRecorder.assertSingle(AccountDeactivationEvents.DeactivateAccount(true))
    }

    @Test
    fun `switching on the erase all switch emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AccountDeactivationEvents>()
        setAccountDeactivationView(
            state = anAccountDeactivationState(
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_deactivate_account_delete_all_messages)
        eventsRecorder.assertSingle(AccountDeactivationEvents.SetEraseData(true))
    }

    @Test
    fun `switching off the erase all switch emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AccountDeactivationEvents>()
        setAccountDeactivationView(
            state = anAccountDeactivationState(
                deactivateFormState = aDeactivateFormState(
                    eraseData = true,
                ),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_deactivate_account_delete_all_messages)
        eventsRecorder.assertSingle(AccountDeactivationEvents.SetEraseData(false))
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `typing text in the password field emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<AccountDeactivationEvents>()
        setAccountDeactivationView(
            state = anAccountDeactivationState(
                deactivateFormState = aDeactivateFormState(
                    password = A_PASSWORD,
                ),
                eventSink = eventsRecorder,
            ),
        )
        onNodeWithTag(TestTags.loginPassword.value).performTextInput("A")
        eventsRecorder.assertSingle(AccountDeactivationEvents.SetPassword("A$A_PASSWORD"))
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setAccountDeactivationView(
    state: AccountDeactivationState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        AccountDeactivationView(
            state = state,
            onBackClick = onBackClick,
        )
    }
}
