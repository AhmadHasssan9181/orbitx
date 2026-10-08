/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.userprofile.shared.blockuser

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.features.userprofile.api.UserProfileEvents
import tech.orbitweb.orbitx.features.userprofile.api.UserProfileState
import tech.orbitweb.orbitx.features.userprofile.shared.R
import tech.orbitweb.orbitx.features.userprofile.shared.aUserProfileState
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class BlockUserDialogsTest : RobolectricTest() {
    @Test
    fun `confirm block user emit expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setContent {
            BlockUserDialogs(
                state = aUserProfileState(
                    displayConfirmationDialog = UserProfileState.ConfirmationDialog.Block,
                    eventSink = eventsRecorder,
                )
            )
        }
        clickOn(R.string.screen_dm_details_block_alert_action)
        eventsRecorder.assertSingle(UserProfileEvents.BlockUser(false))
    }

    @Test
    fun `cancel block user emit expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setContent {
            BlockUserDialogs(
                state = aUserProfileState(
                    displayConfirmationDialog = UserProfileState.ConfirmationDialog.Block,
                    eventSink = eventsRecorder,
                )
            )
        }
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(UserProfileEvents.ClearConfirmationDialog)
    }

    @Test
    fun `confirm unblock user emit expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setContent {
            BlockUserDialogs(
                state = aUserProfileState(
                    displayConfirmationDialog = UserProfileState.ConfirmationDialog.Unblock,
                    eventSink = eventsRecorder,
                )
            )
        }
        clickOn(R.string.screen_dm_details_unblock_alert_action)
        eventsRecorder.assertSingle(UserProfileEvents.UnblockUser(false))
    }

    @Test
    fun `cancel unblock user emit expected Event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<UserProfileEvents>()
        setContent {
            BlockUserDialogs(
                state = aUserProfileState(
                    displayConfirmationDialog = UserProfileState.ConfirmationDialog.Unblock,
                    eventSink = eventsRecorder,
                )
            )
        }
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(UserProfileEvents.ClearConfirmationDialog)
    }
}
