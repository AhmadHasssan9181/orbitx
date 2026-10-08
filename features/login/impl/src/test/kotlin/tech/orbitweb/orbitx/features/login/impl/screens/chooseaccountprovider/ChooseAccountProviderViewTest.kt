/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.login.impl.screens.chooseaccountprovider

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import org.robolectric.annotation.Config
import tech.orbitweb.orbitx.features.login.impl.accountprovider.anAccountProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.auth.OAuthDetails
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EXCEPTION
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class ChooseAccountProviderViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invokes the expected callback`() = runAndroidComposeUiTest {
        val eventSink = EventsRecorder<ChooseAccountProviderEvents>(expectEvents = false)
        ensureCalledOnce {
            setChooseAccountProviderView(
                state = aChooseAccountProviderState(
                    eventSink = eventSink,
                ),
                onBackClick = it,
            )
            pressBack()
        }
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `selecting an account provider emits the the expected event`() = runAndroidComposeUiTest {
        val eventSink = EventsRecorder<ChooseAccountProviderEvents>()
        setChooseAccountProviderView(
            state = aChooseAccountProviderState(
                accountProviders = listOf(
                    ChooseAccountProviderPresenterTest.accountProvider1,
                    ChooseAccountProviderPresenterTest.accountProvider2,
                ),
                selectedAccountProvider = anAccountProvider(),
                eventSink = eventSink,
            ),
        )
        onNodeWithText(ChooseAccountProviderPresenterTest.accountProvider1.title).performClick()
        eventSink.assertSingle(ChooseAccountProviderEvents.SelectAccountProvider(ChooseAccountProviderPresenterTest.accountProvider1))
    }

    @Test
    fun `when error is displayed - closing the dialog emits the expected event`() = runAndroidComposeUiTest {
        val eventSink = EventsRecorder<ChooseAccountProviderEvents>()
        setChooseAccountProviderView(
            state = aChooseAccountProviderState(
                loginMode = AsyncData.Failure(AN_EXCEPTION),
                eventSink = eventSink,
            ),
        )
        clickOn(CommonStrings.action_ok)
        eventSink.assertSingle(ChooseAccountProviderEvents.ClearError)
    }

    private fun AndroidComposeUiTest<ComponentActivity>.setChooseAccountProviderView(
        state: ChooseAccountProviderState,
        onBackClick: () -> Unit = EnsureNeverCalled(),
        onOAuthDetails: (OAuthDetails) -> Unit = EnsureNeverCalledWithParam(),
        onNeedLoginPassword: () -> Unit = EnsureNeverCalled(),
        onLearnMoreClick: () -> Unit = EnsureNeverCalled(),
        onCreateAccountContinue: (url: String) -> Unit = EnsureNeverCalledWithParam(),
    ) {
        setContent {
            ChooseAccountProviderView(
                state = state,
                onBackClick = onBackClick,
                onOAuthDetails = onOAuthDetails,
                onNeedLoginPassword = onNeedLoginPassword,
                onLearnMoreClick = onLearnMoreClick,
                onCreateAccountContinue = onCreateAccountContinue,
            )
        }
    }
}
