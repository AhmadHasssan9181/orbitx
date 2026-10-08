/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.roommembermoderation.impl

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.roommembermoderation.api.ModerationAction
import tech.orbitweb.orbitx.features.roommembermoderation.api.ModerationActionState
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationEvents
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithTwoParams
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithTwoParams
import tech.orbitweb.orbitx.tests.testutils.pressTag
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import tech.orbitweb.orbitx.tests.testutils.setSafeContent
import org.junit.Test

class RoomMemberModerationViewTest : RobolectricTest() {
    @Test
    fun `clicking on display profile action calls onSelectAction`() = runAndroidComposeUiTest {
        val user = anAlice()
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>(expectEvents = false)
        ensureCalledOnceWithTwoParams<ModerationAction, MatrixUser>(ModerationAction.DisplayProfile, user) { callback ->
            setRoomMemberModerationView(
                aRoomMembersModerationState(
                    selectedUser = user,
                    actions = listOf(
                        ModerationActionState(action = ModerationAction.DisplayProfile, isEnabled = true),
                    ),
                    eventSink = eventsRecorder
                ),
                onSelectAction = callback
            )
            clickOn(R.string.screen_bottom_sheet_manage_room_member_member_user_info)
            // Gives time for bottomsheet to hide
            mainClock.advanceTimeBy(1_000)
        }
    }

    @Test
    fun `clicking on kick user action calls onSelectAction`() = runAndroidComposeUiTest {
        val user = anAlice()
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>(expectEvents = false)
        ensureCalledOnceWithTwoParams<ModerationAction, MatrixUser>(ModerationAction.KickUser, user) { callback ->
            setRoomMemberModerationView(
                aRoomMembersModerationState(
                    selectedUser = user,
                    actions = listOf(
                        ModerationActionState(action = ModerationAction.KickUser, isEnabled = true),
                    ),
                    eventSink = eventsRecorder
                ),
                onSelectAction = callback
            )
            clickOn(R.string.screen_bottom_sheet_manage_room_member_remove)
            // Gives time for bottomsheet to hide
            mainClock.advanceTimeBy(1_000)
        }
    }

    @Test
    fun `clicking on ban user action calls onSelectAction`() = runAndroidComposeUiTest {
        val user = anAlice()
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>(expectEvents = false)
        ensureCalledOnceWithTwoParams<ModerationAction, MatrixUser>(ModerationAction.BanUser, user) { callback ->
            setRoomMemberModerationView(
                aRoomMembersModerationState(
                    selectedUser = user,
                    actions = listOf(
                        ModerationActionState(action = ModerationAction.BanUser, isEnabled = true),
                    ),
                    eventSink = eventsRecorder
                ),
                onSelectAction = callback
            )
            clickOn(R.string.screen_bottom_sheet_manage_room_member_ban)
            // Gives time for bottomsheet to hide
            mainClock.advanceTimeBy(1_000)
        }
    }

    @Test
    fun `clicking on unban user action calls onSelectAction`() = runAndroidComposeUiTest {
        val user = anAlice()
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>(expectEvents = false)
        ensureCalledOnceWithTwoParams<ModerationAction, MatrixUser>(ModerationAction.UnbanUser, user) { callback ->
            setRoomMemberModerationView(
                aRoomMembersModerationState(
                    selectedUser = user,
                    actions = listOf(
                        ModerationActionState(action = ModerationAction.UnbanUser, isEnabled = true),
                    ),
                    eventSink = eventsRecorder
                ),
                onSelectAction = callback
            )
            clickOn(R.string.screen_bottom_sheet_manage_room_member_unban)
            // Gives time for bottomsheet to hide
            mainClock.advanceTimeBy(1_000)
        }
    }

    @Test
    fun `clicking submit on kick confirmation dialog sends DoKickUser event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>()
        setRoomMemberModerationView(
            aRoomMembersModerationState(
                selectedUser = anAlice(),
                kickUserAsyncAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder
            ),
        )
        pressTag(TestTags.dialogPositive.value)
        eventsRecorder.assertSingle(InternalRoomMemberModerationEvents.DoKickUser(reason = ""))
    }

    @Test
    fun `clicking dismiss on kick confirmation dialog sends Reset event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>()
        setRoomMemberModerationView(
            aRoomMembersModerationState(
                selectedUser = anAlice(),
                kickUserAsyncAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder
            ),
        )
        pressTag(TestTags.dialogNegative.value)
        eventsRecorder.assertSingle(InternalRoomMemberModerationEvents.Reset)
    }

    @Test
    fun `clicking submit on ban confirmation dialog sends DoBanUser event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>()
        setRoomMemberModerationView(
            aRoomMembersModerationState(
                selectedUser = anAlice(),
                banUserAsyncAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder
            ),
        )
        pressTag(TestTags.dialogPositive.value)
        eventsRecorder.assertSingle(InternalRoomMemberModerationEvents.DoBanUser(reason = ""))
    }

    @Test
    fun `clicking dismiss on ban confirmation dialog sends Reset event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>()
        setRoomMemberModerationView(
            aRoomMembersModerationState(
                selectedUser = anAlice(),
                banUserAsyncAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder
            ),
        )
        pressTag(TestTags.dialogNegative.value)
        eventsRecorder.assertSingle(InternalRoomMemberModerationEvents.Reset)
    }

    @Test
    fun `clicking confirm on unban confirmation dialog sends DoUnbanUser event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>()
        setRoomMemberModerationView(
            aRoomMembersModerationState(
                selectedUser = anAlice(),
                unbanUserAsyncAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder
            ),
        )
        pressTag(TestTags.dialogPositive.value)
        eventsRecorder.assertSingle(InternalRoomMemberModerationEvents.DoUnbanUser(""))
    }

    @Test
    fun `clicking dismiss on unban confirmation dialog sends Reset event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>()
        setRoomMemberModerationView(
            aRoomMembersModerationState(
                selectedUser = anAlice(),
                unbanUserAsyncAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder
            ),
        )
        pressTag(TestTags.dialogNegative.value)
        eventsRecorder.assertSingle(InternalRoomMemberModerationEvents.Reset)
    }

    @Test
    fun `disabled actions are not clickable`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<RoomMemberModerationEvents>(expectEvents = false)
        setRoomMemberModerationView(
            aRoomMembersModerationState(
                selectedUser = anAlice(),
                actions = listOf(
                    ModerationActionState(action = ModerationAction.KickUser, isEnabled = false),
                ),
                eventSink = eventsRecorder
            ),
        )
        clickOn(R.string.screen_bottom_sheet_manage_room_member_remove)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setRoomMemberModerationView(
    state: InternalRoomMemberModerationState,
    onSelectAction: (ModerationAction, MatrixUser) -> Unit = EnsureNeverCalledWithTwoParams(),
    onAvatarClick: ((MatrixUser) -> Unit)? = EnsureNeverCalledWithParam(),
) {
    setSafeContent {
        RoomMemberModerationView(
            state = state,
            onSelectAction = onSelectAction,
            onAvatarClick = onAvatarClick,
        )
    }
}
