/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl.declineandblock

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.invite.api.InviteData
import tech.orbitweb.orbitx.features.invite.impl.DeclineInvite
import tech.orbitweb.orbitx.features.invite.impl.fake.FakeDeclineInvite
import tech.orbitweb.orbitx.features.invite.test.anInviteData
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarDispatcher
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.lambda.assert
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import tech.orbitweb.orbitx.tests.testutils.test
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DeclineAndBlockPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state`() = runTest {
        val presenter = createDeclineAndBlockPresenter()
        presenter.test {
            awaitItem().also { state ->
                assertThat(state.blockUser).isTrue()
                assertThat(state.reportRoom).isFalse()
                assertThat(state.reportReason).isEmpty()
                assertThat(state.declineAction).isInstanceOf(AsyncAction.Uninitialized::class.java)
                assertThat(state.canDecline).isTrue()
            }
        }
    }

    @Test
    fun `present - update form values`() = runTest {
        val presenter = createDeclineAndBlockPresenter()
        presenter.test {
            awaitItem().also { state ->
                assertThat(state.reportRoom).isFalse()
                assertThat(state.blockUser).isTrue()
                assertThat(state.reportReason).isEmpty()
                assertThat(state.canDecline).isTrue()
                state.eventSink(DeclineAndBlockEvents.ToggleBlockUser)
            }
            awaitItem().also { state ->
                assertThat(state.reportRoom).isFalse()
                assertThat(state.blockUser).isFalse()
                assertThat(state.reportReason).isEmpty()
                assertThat(state.canDecline).isFalse()
                state.eventSink(DeclineAndBlockEvents.ToggleReportRoom)
            }
            awaitItem().also { state ->
                assertThat(state.reportRoom).isTrue()
                assertThat(state.blockUser).isFalse()
                assertThat(state.reportReason).isEmpty()
                assertThat(state.canDecline).isFalse()
                state.eventSink(DeclineAndBlockEvents.UpdateReportReason("Spam"))
            }
            awaitItem().also { state ->
                assertThat(state.reportRoom).isTrue()
                assertThat(state.blockUser).isFalse()
                assertThat(state.reportReason).isEqualTo("Spam")
                assertThat(state.canDecline).isTrue()
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `present - declining invite success flow`() = runTest {
        val declineInviteSuccess = lambdaRecorder<RoomId, Boolean, Boolean, String?, Result<RoomId>> { roomId, _, _, _ -> Result.success(roomId) }
        val presenter = createDeclineAndBlockPresenter(
            declineInvite = FakeDeclineInvite(lambda = declineInviteSuccess)
        )
        presenter.test {
            awaitItem().also { state ->
                state.eventSink(DeclineAndBlockEvents.Decline)
            }
            assertThat(awaitItem().declineAction.isLoading()).isTrue()
            awaitItem().also { state ->
                assertThat(state.declineAction).isInstanceOf(AsyncAction.Success::class.java)
            }
            cancelAndConsumeRemainingEvents()
        }
        assert(declineInviteSuccess)
            .isCalledOnce()
            .with(value(A_ROOM_ID), value(true), value(false), value(""))
    }

    @Test
    fun `present - declining invite error flow`() = runTest {
        val declineInviteFailure = lambdaRecorder<RoomId, Boolean, Boolean, String?, Result<RoomId>> { _, _, _, _ ->
            Result.failure(DeclineInvite.Exception.DeclineInviteFailed)
        }
        val presenter = createDeclineAndBlockPresenter(
            declineInvite = FakeDeclineInvite(lambda = declineInviteFailure)
        )
        presenter.test {
            awaitItem().also { state ->
                state.eventSink(DeclineAndBlockEvents.Decline)
            }
            assertThat(awaitItem().declineAction.isLoading()).isTrue()
            awaitItem().also { state ->
                assertThat(state.declineAction).isInstanceOf(AsyncAction.Failure::class.java)
                state.eventSink(DeclineAndBlockEvents.ClearDeclineAction)
            }
            awaitItem().also { state ->
                assertThat(state.declineAction).isInstanceOf(AsyncAction.Uninitialized::class.java)
            }
            cancelAndConsumeRemainingEvents()
        }
        assert(declineInviteFailure)
            .isCalledOnce()
            .with(value(A_ROOM_ID), value(true), value(false), value(""))
    }

    @Test
    fun `present - block user error flow`() = runTest {
        val declineInviteFailure = lambdaRecorder<RoomId, Boolean, Boolean, String?, Result<RoomId>> { _, _, _, _ ->
            Result.failure(DeclineInvite.Exception.BlockUserFailed)
        }
        val presenter = createDeclineAndBlockPresenter(
            declineInvite = FakeDeclineInvite(lambda = declineInviteFailure)
        )
        presenter.test {
            awaitItem().also { state ->
                state.eventSink(DeclineAndBlockEvents.Decline)
            }
            assertThat(awaitItem().declineAction.isLoading()).isTrue()
            awaitItem().also { state ->
                assertThat(state.declineAction).isInstanceOf(AsyncAction.Uninitialized::class.java)
            }
            cancelAndConsumeRemainingEvents()
        }
        assert(declineInviteFailure)
            .isCalledOnce()
            .with(value(A_ROOM_ID), value(true), value(false), value(""))
    }
}

internal fun createDeclineAndBlockPresenter(
    inviteData: InviteData = anInviteData(),
    declineInvite: DeclineInvite = FakeDeclineInvite(),
    snackbarDispatcher: SnackbarDispatcher = SnackbarDispatcher(),
): DeclineAndBlockPresenter {
    return DeclineAndBlockPresenter(
        inviteData = inviteData,
        declineInvite = declineInvite,
        snackbarDispatcher = snackbarDispatcher,
    )
}
