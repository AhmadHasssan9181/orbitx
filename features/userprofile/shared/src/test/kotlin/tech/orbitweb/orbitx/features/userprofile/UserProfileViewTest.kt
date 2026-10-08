/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.userprofile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.userprofile.api.UserProfileEvents
import tech.orbitweb.orbitx.features.userprofile.api.UserProfileState
import tech.orbitweb.orbitx.features.userprofile.api.UserProfileVerificationState
import tech.orbitweb.orbitx.features.userprofile.shared.R
import tech.orbitweb.orbitx.features.userprofile.shared.UserProfileView
import tech.orbitweb.orbitx.features.userprofile.shared.aUserProfileState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.test.AN_AVATAR_URL
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithTwoParams
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithTwoParams
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test
import org.robolectric.annotation.Config

class UserProfileViewTest : RobolectricTest() {
    @Test
    fun `on back button click - the expected callback is called`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setUserProfileView(
                goBack = callback,
            )
            pressBack()
        }
    }

    @Test
    fun `on avatar clicked - the expected callback is called`() = runAndroidComposeUiTest {
        ensureCalledOnceWithTwoParams(A_USER_NAME, AN_AVATAR_URL) { callback ->
            setUserProfileView(
                state = aUserProfileState(userName = A_USER_NAME, avatarUrl = AN_AVATAR_URL),
                openAvatarPreview = callback,
            )
            onNode(hasTestTag(TestTags.memberDetailAvatar.value)).performClick()
        }
    }

    @Test
    fun `on avatar clicked with no avatar - nothing happens`() = runAndroidComposeUiTest {
        val callback = EnsureNeverCalledWithTwoParams<String, String>()
        setUserProfileView(
            state = aUserProfileState(userName = A_USER_NAME, avatarUrl = null),
            openAvatarPreview = callback,
        )
        onNode(hasTestTag(TestTags.memberDetailAvatar.value)).performClick()
    }

    @Test
    fun `on Share clicked - the expected callback is called`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setUserProfileView(
                onShareUser = callback,
            )
            clickOn(CommonStrings.action_share)
        }
    }

    @Test
    fun `on Message clicked - the StartDm event is emitted`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setUserProfileView(
            state = aUserProfileState(
                dmRoomId = A_ROOM_ID,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_message)
        eventsRecorder.assertSingle(UserProfileEvents.StartDM)
    }

    @Test
    fun `on Call clicked - the expected callback is called`() = runAndroidComposeUiTest {
        ensureCalledOnceWithTwoParams(A_ROOM_ID, CallIntent.AUDIO) { callback ->
            setUserProfileView(
                state = aUserProfileState(
                    dmRoomId = A_ROOM_ID,
                    canCall = true,
                ),
                onStartCall = callback,
            )
            clickOn(CommonStrings.action_call)
        }
    }

    @Test
    fun `on Video Call clicked - the expected callback is called`() = runAndroidComposeUiTest {
        ensureCalledOnceWithTwoParams(A_ROOM_ID, CallIntent.VIDEO) { callback ->
            setUserProfileView(
                state = aUserProfileState(
                    dmRoomId = A_ROOM_ID,
                    canCall = true,
                ),
                onStartCall = callback,
            )
            clickOn(CommonStrings.common_video)
        }
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `on Block user clicked - a BlockUser event is emitted with needsConfirmation`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setUserProfileView(
            state = aUserProfileState(
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_dm_details_block_user)
        eventsRecorder.assertSingle(UserProfileEvents.BlockUser(needsConfirmation = true))
    }

    @Test
    fun `on confirming block user - a BlockUser event is emitted without needsConfirmation`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setUserProfileView(
            state = aUserProfileState(
                displayConfirmationDialog = UserProfileState.ConfirmationDialog.Block,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_dm_details_block_alert_action)
        eventsRecorder.assertSingle(UserProfileEvents.BlockUser(needsConfirmation = false))
    }

    @Test
    fun `on canceling blocking a user - a ClearConfirmationDialog event is emitted`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setUserProfileView(
            state = aUserProfileState(
                displayConfirmationDialog = UserProfileState.ConfirmationDialog.Block,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(UserProfileEvents.ClearConfirmationDialog)
    }

    @Config(qualifiers = "h1024dp")
    @Test
    fun `on Unblock user clicked - an UnblockUser event is emitted with needsConfirmation`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setUserProfileView(
            state = aUserProfileState(
                isBlocked = AsyncData.Success(true),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_dm_details_unblock_user)
        eventsRecorder.assertSingle(UserProfileEvents.UnblockUser(needsConfirmation = true))
    }

    @Test
    fun `on confirming Unblock user - an UnblockUser event is emitted without needsConfirmation`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setUserProfileView(
            state = aUserProfileState(
                isBlocked = AsyncData.Success(true),
                displayConfirmationDialog = UserProfileState.ConfirmationDialog.Unblock,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(R.string.screen_dm_details_unblock_alert_action)
        eventsRecorder.assertSingle(UserProfileEvents.UnblockUser(needsConfirmation = false))
    }

    @Test
    fun `on canceling unblocking a user - a ClearConfirmationDialog event is emitted`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setUserProfileView(
            state = aUserProfileState(
                isBlocked = AsyncData.Success(true),
                displayConfirmationDialog = UserProfileState.ConfirmationDialog.Unblock,
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(UserProfileEvents.ClearConfirmationDialog)
    }

    @Test
    fun `on verify user clicked - the right callback is called`() = runAndroidComposeUiTest {
        ensureCalledOnceWithParam(A_USER_ID) { callback ->
            setUserProfileView(
                state = aUserProfileState(userId = A_USER_ID, verificationState = UserProfileVerificationState.UNVERIFIED),
                onVerifyClick = callback,
            )
            clickOn(CommonStrings.common_verify_user)
        }
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setUserProfileView(
    state: UserProfileState = aUserProfileState(
        eventSink = EventsRecorder(expectEvents = false),
    ),
    onShareUser: () -> Unit = EnsureNeverCalled(),
    onDmStarted: (RoomId) -> Unit = EnsureNeverCalledWithParam(),
    onStartCall: (RoomId, CallIntent) -> Unit = EnsureNeverCalledWithTwoParams(),
    onVerifyClick: (UserId) -> Unit = EnsureNeverCalledWithParam(),
    goBack: () -> Unit = EnsureNeverCalled(),
    openAvatarPreview: (String, String) -> Unit = EnsureNeverCalledWithTwoParams(),
) {
    setContent {
        UserProfileView(
            state = state,
            onShareUser = onShareUser,
            onOpenDm = onDmStarted,
            onStartCall = onStartCall,
            goBack = goBack,
            openAvatarPreview = openAvatarPreview,
            onVerifyClick = onVerifyClick,
        )
    }
}
