/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.preferences.impl.blockedusers

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.preferences.impl.R
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.ui.components.aMatrixUserList
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class BlockedUserViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invokes back callback`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<BlockedUsersEvents>(expectEvents = false)
        ensureCalledOnce { callback ->
            setBlockedUsersView(
                aBlockedUsersState(
                    eventSink = eventsRecorder
                ),
                onBackClick = callback,
            )
            pressBack()
        }
    }

    @Test
    fun `clicking on a user emits the expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<BlockedUsersEvents>()
        val userList = aMatrixUserList()
        setBlockedUsersView(
            aBlockedUsersState(
                blockedUsers = userList,
                eventSink = eventsRecorder
            ),
        )
        onNodeWithText(userList.first().displayName.orEmpty()).performClick()
        eventsRecorder.assertSingle(BlockedUsersEvents.Unblock(userList.first().userId))
    }

    @Test
    fun `clicking on cancel sends a BlockedUsersEvents`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<BlockedUsersEvents>()
        setBlockedUsersView(
            aBlockedUsersState(
                unblockUserAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder
            ),
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(BlockedUsersEvents.Cancel)
    }

    @Test
    fun `clicking on confirm sends a BlockedUsersEvents`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<BlockedUsersEvents>()
        setBlockedUsersView(
            aBlockedUsersState(
                unblockUserAction = AsyncAction.ConfirmingNoParams,
                eventSink = eventsRecorder
            ),
        )
        clickOn(R.string.screen_blocked_users_unblock_alert_action)
        eventsRecorder.assertSingle(BlockedUsersEvents.ConfirmUnblock)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setBlockedUsersView(
    state: BlockedUsersState,
    onBackClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        BlockedUsersView(
            state = state,
            onBackClick = onBackClick,
        )
    }
}
