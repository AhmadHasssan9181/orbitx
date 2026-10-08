/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.messages.impl.crypto.identity

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.anAvatarData
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.identity.IdentityState
import tech.orbitweb.orbitx.libraries.matrix.ui.room.IdentityRoomMember
import tech.orbitweb.orbitx.libraries.matrix.ui.room.RoomMemberIdentityStateChange
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class IdentityChangeStateViewTest : RobolectricTest() {
    @Test
    fun `show and resolve pin violation`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<IdentityChangeEvent>()
        setIdentityChangeStateView(
            state = anIdentityChangeState(
                listOf(
                    RoomMemberIdentityStateChange(
                        identityRoomMember = IdentityRoomMember(UserId("@alice:localhost"), "Alice", anAvatarData()),
                        identityState = IdentityState.PinViolation
                    )
                ),
                eventsRecorder
            ),
        )

        onNodeWithText("identity was reset", substring = true).assertExists("should display pin violation warning")
        onNodeWithText("@alice:localhost", substring = true).assertExists("should display user mxid")
        onNodeWithText("Alice", substring = true).assertExists("should display user displayname")

        clickOn(res = CommonStrings.action_dismiss)
        eventsRecorder.assertSingle(IdentityChangeEvent.PinIdentity(UserId("@alice:localhost")))
    }

    @Test
    fun `show and resolve verification violation`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<IdentityChangeEvent>()
        setIdentityChangeStateView(
            state = anIdentityChangeState(
                listOf(
                    RoomMemberIdentityStateChange(
                        identityRoomMember = IdentityRoomMember(UserId("@alice:localhost"), "Alice", anAvatarData()),
                        identityState = IdentityState.VerificationViolation
                    )
                ),
                eventsRecorder
            ),
        )

        onNodeWithText("identity was reset", substring = true).assertExists("should display verification violation warning")
        onNodeWithText("@alice:localhost", substring = true).assertExists("should display user mxid")
        onNodeWithText("Alice", substring = true).assertExists("should display user displayname")

        clickOn(res = CommonStrings.crypto_identity_change_withdraw_verification_action)
        eventsRecorder.assertSingle(IdentityChangeEvent.WithdrawVerification(UserId("@alice:localhost")))
    }

    @Test
    fun `Should not show any banner if no violations`() = runAndroidComposeUiTest {
        setIdentityChangeStateView(
            state = anIdentityChangeState(
                listOf(
                    RoomMemberIdentityStateChange(
                        identityRoomMember = IdentityRoomMember(UserId("@alice:localhost"), "Alice", anAvatarData()),
                        identityState = IdentityState.Verified
                    ),
                    RoomMemberIdentityStateChange(
                        identityRoomMember = IdentityRoomMember(UserId("@bob:localhost"), "Bob", anAvatarData()),
                        identityState = IdentityState.Pinned
                    )
                ),
            ),
        )

        onNodeWithText("identity was reset", substring = true).assertDoesNotExist()
    }

    private fun AndroidComposeUiTest<ComponentActivity>.setIdentityChangeStateView(
        state: IdentityChangeState,
    ) {
        setContent {
            IdentityChangeStateView(
                state = state,
                onLinkClick = { _, _ -> },
            )
        }
    }
}
