/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.knockrequests.impl.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import tech.orbitweb.orbitx.features.knockrequests.impl.R
import tech.orbitweb.orbitx.features.knockrequests.impl.data.aKnockRequestPresentable
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class KnockRequestsListViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invoke the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsListEvents>(expectEvents = false)
        ensureCalledOnce {
            setKnockRequestsListView(
                aKnockRequestsListState(
                    eventSink = eventsRecorder,
                ),
                onBackClick = it
            )
            pressBack()
        }
    }

    @Test
    fun `clicking on accept emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsListEvents>()
        val knockRequest = aKnockRequestPresentable()
        setKnockRequestsListView(
            aKnockRequestsListState(
                knockRequests = AsyncData.Success(persistentListOf(knockRequest)),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_accept)
        eventsRecorder.assertSingle(KnockRequestsListEvents.Accept(knockRequest))
    }

    @Test
    fun `clicking on decline emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsListEvents>()
        val knockRequest = aKnockRequestPresentable()
        setKnockRequestsListView(
            aKnockRequestsListState(
                knockRequests = AsyncData.Success(persistentListOf(knockRequest)),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_decline)
        eventsRecorder.assertSingle(KnockRequestsListEvents.Decline(knockRequest))
    }

    @Test
    fun `clicking on decline and ban emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsListEvents>()
        val knockRequest = aKnockRequestPresentable()
        setKnockRequestsListView(
            aKnockRequestsListState(
                knockRequests = AsyncData.Success(persistentListOf(knockRequest)),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_knock_requests_list_decline_and_ban_action_title)
        eventsRecorder.assertSingle(KnockRequestsListEvents.DeclineAndBan(knockRequest))
    }

    @Test
    fun `clicking on accept all emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsListEvents>()
        val knockRequests = persistentListOf(aKnockRequestPresentable(), aKnockRequestPresentable())
        setKnockRequestsListView(
            aKnockRequestsListState(
                knockRequests = AsyncData.Success(knockRequests),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_knock_requests_list_accept_all_button_title)
        eventsRecorder.assertSingle(KnockRequestsListEvents.AcceptAll)
    }

    @Test
    fun `retry on async view retry emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsListEvents>()
        val knockRequests = persistentListOf(aKnockRequestPresentable(), aKnockRequestPresentable())
        setKnockRequestsListView(
            aKnockRequestsListState(
                knockRequests = AsyncData.Success(knockRequests),
                asyncAction = AsyncAction.Failure(RuntimeException("Failed to accept all")),
                currentAction = KnockRequestsAction.AcceptAll,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_retry)
        eventsRecorder.assertSingle(KnockRequestsListEvents.RetryCurrentAction)
    }

    @Test
    fun `canceling async view emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsListEvents>()
        val knockRequests = persistentListOf(aKnockRequestPresentable(), aKnockRequestPresentable())
        setKnockRequestsListView(
            aKnockRequestsListState(
                knockRequests = AsyncData.Success(knockRequests),
                asyncAction = AsyncAction.Failure(RuntimeException("Failed to accept all")),
                currentAction = KnockRequestsAction.AcceptAll,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(KnockRequestsListEvents.ResetCurrentAction)
    }

    @Test
    fun `confirming async view emit the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<KnockRequestsListEvents>()
        val knockRequests = persistentListOf(aKnockRequestPresentable(), aKnockRequestPresentable())
        setKnockRequestsListView(
            aKnockRequestsListState(
                knockRequests = AsyncData.Success(knockRequests),
                asyncAction = AsyncAction.ConfirmingNoParams,
                currentAction = KnockRequestsAction.AcceptAll,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_knock_requests_list_accept_all_alert_confirm_button_title)
        eventsRecorder.assertSingle(KnockRequestsListEvents.ConfirmCurrentAction)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setKnockRequestsListView(
    state: KnockRequestsListState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        KnockRequestsListView(
            state = state,
            onBackClick = onBackClick,
        )
    }
}
