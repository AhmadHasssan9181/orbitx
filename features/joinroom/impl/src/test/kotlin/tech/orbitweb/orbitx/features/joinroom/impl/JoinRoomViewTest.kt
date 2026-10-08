/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.joinroom.impl

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.features.invite.api.InviteData
import tech.orbitweb.orbitx.features.invite.test.anInviteData
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomMember
import tech.orbitweb.orbitx.libraries.matrix.ui.model.toInviteSender
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class JoinRoomViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invoke the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>(expectEvents = false)
        ensureCalledOnce {
            setJoinRoomView(
                aJoinRoomState(
                    eventSink = eventsRecorder,
                ),
                onBackClick = it
            )
            pressBack()
        }
    }

    @Test
    fun `clicking on Join room on CanJoin room emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.CanJoin),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_join_room_join_action)
        eventsRecorder.assertSingle(JoinRoomEvents.JoinRoom)
    }

    @Test
    fun `clicking on Knock room on CanKnock room emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.CanKnock),
                knockMessage = "Knock knock",
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_join_room_knock_action)
        eventsRecorder.assertSingle(JoinRoomEvents.KnockRoom)
    }

    @Test
    fun `clicking on closing Knock error emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.CanKnock),
                knockAction = AsyncAction.Failure(Exception("Error")),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_ok)
        eventsRecorder.assertSingle(JoinRoomEvents.ClearActionStates)
    }

    @Test
    fun `clicking on cancel knock request emit the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.IsKnocked),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_join_room_cancel_knock_action)
        eventsRecorder.assertSingle(JoinRoomEvents.CancelKnock(true))
    }

    @Test
    fun `clicking on closing Cancel Knock error emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.IsKnocked),
                cancelKnockAction = AsyncAction.Failure(Exception("Error")),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_ok)
        eventsRecorder.assertSingle(JoinRoomEvents.ClearActionStates)
    }

    @Test
    fun `clicking on closing Join error emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.CanKnock),
                joinAction = AsyncAction.Failure(Exception("Error")),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_ok)
        eventsRecorder.assertSingle(JoinRoomEvents.ClearActionStates)
    }

    @Test
    fun `when joining room is successful, the expected callback is invoked`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>(expectEvents = false)
        ensureCalledOnce {
            setJoinRoomView(
                aJoinRoomState(
                    joinAction = AsyncAction.Success(Unit),
                    eventSink = eventsRecorder,
                ),
                onJoinSuccess = it
            )
        }
    }

    @Test
    fun `clicking on Accept when JoinAuthorisationStatus is IsInvited emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        val inviteData = anInviteData()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.IsInvited(inviteData, null)),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_accept)
        eventsRecorder.assertSingle(JoinRoomEvents.AcceptInvite(inviteData))
    }

    @Test
    fun `clicking on Decline when JoinAuthorisationStatus is IsInvited emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        val inviteData = anInviteData()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.IsInvited(inviteData, null)),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_decline)
        eventsRecorder.assertSingle(JoinRoomEvents.DeclineInvite(inviteData, false))
    }

    @Test
    fun `clicking on Decline and block when JoinAuthorisationStatus is IsInvited and can report room, the expected callback is invoked`() {
        runAndroidComposeUiTest {
            val eventsRecorder = EventsRecorder<JoinRoomEvents>(expectEvents = false)
            val inviteData = anInviteData()
            val joinRoomState = aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.IsInvited(inviteData, aRoomMember().toInviteSender())),
                canReportRoom = true,
                eventSink = eventsRecorder,
            )
            ensureCalledOnceWithParam(inviteData) {
                setJoinRoomView(
                    state = joinRoomState,
                    onDeclineInviteAndBlockUser = it,
                )
                clickOn(R.string.screen_join_room_decline_and_block_button_title)
            }
        }
    }

    @Test
    fun `clicking on Decline and block when JoinAuthorisationStatus is IsInvited and cant report room, emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        val inviteData = anInviteData()
        val joinRoomState = aJoinRoomState(
            contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.IsInvited(inviteData, aRoomMember().toInviteSender())),
            canReportRoom = false,
            eventSink = eventsRecorder,
        )
        setJoinRoomView(state = joinRoomState)
        clickOn(R.string.screen_join_room_decline_and_block_button_title)
        eventsRecorder.assertSingle(JoinRoomEvents.DeclineInvite(inviteData, true))
    }

    @Test
    fun `clicking on Retry when an error occurs emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aFailureContentState(),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_retry)
        eventsRecorder.assertSingle(JoinRoomEvents.RetryFetchingContent)
    }

    @Test
    fun `clicking on ok when user is unauthorized the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>(expectEvents = false)
        ensureCalledOnce {
            setJoinRoomView(
                aJoinRoomState(
                    contentState = aLoadedContentState(),
                    joinAction = AsyncAction.Failure(JoinRoom.Failures.UnauthorizedJoin),
                    eventSink = eventsRecorder,
                ),
                onBackClick = it
            )
            clickOn(CommonStrings.action_ok)
        }
    }

    @Test
    fun `clicking on forget when user is banned invokes the expected callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<JoinRoomEvents>()
        setJoinRoomView(
            aJoinRoomState(
                contentState = aLoadedContentState(joinAuthorisationStatus = JoinAuthorisationStatus.IsBanned(null, null)),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_join_room_forget_action)
        eventsRecorder.assertSingle(JoinRoomEvents.ForgetRoom)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setJoinRoomView(
    state: JoinRoomState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
    onJoinSuccess: () -> Unit = EnsureNeverCalled(),
    onKnockSuccess: () -> Unit = EnsureNeverCalled(),
    onCancelKnockSuccess: () -> Unit = EnsureNeverCalled(),
    onForgetSuccess: () -> Unit = EnsureNeverCalled(),
    onDeclineInviteAndBlockUser: (InviteData) -> Unit = EnsureNeverCalledWithParam(),
) {
    setContent {
        JoinRoomView(
            state = state,
            onBackClick = onBackClick,
            onJoinSuccess = onJoinSuccess,
            onKnockSuccess = onKnockSuccess,
            onForgetSuccess = onForgetSuccess,
            onCancelKnockSuccess = onCancelKnockSuccess,
            onDeclineInviteAndBlockUser = onDeclineInviteAndBlockUser,
        )
    }
}
