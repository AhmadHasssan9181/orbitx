/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.startchat.impl.root

import androidx.compose.runtime.MutableState
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.invitepeople.test.FakeStartDMAction
import tech.orbitweb.orbitx.features.startchat.api.ConfirmingStartDmWithMatrixUser
import tech.orbitweb.orbitx.features.startchat.api.StartDMAction
import tech.orbitweb.orbitx.features.startchat.impl.userlist.FakeUserListPresenter
import tech.orbitweb.orbitx.features.startchat.impl.userlist.FakeUserListPresenterFactory
import tech.orbitweb.orbitx.features.startchat.impl.userlist.UserListDataStore
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EXCEPTION
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import tech.orbitweb.orbitx.libraries.usersearch.test.FakeUserRepository
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.lambda.any
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import tech.orbitweb.orbitx.tests.testutils.test
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class StartChatPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - start DM action failure scenario`() = runTest {
        val startDMFailureResult = AsyncAction.Failure(AN_EXCEPTION)
        val executeResult = lambdaRecorder<MatrixUser, Boolean, MutableState<AsyncAction<RoomId>>, Unit> { _, _, actionState ->
            actionState.value = startDMFailureResult
        }
        val startDMAction = FakeStartDMAction(executeResult = executeResult)
        val presenter = createStartChatPresenter(startDMAction)
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.startDmAction).isInstanceOf(AsyncAction.Uninitialized::class.java)
            assertThat(initialState.applicationName).isEqualTo(aBuildMeta().applicationName)
            assertThat(initialState.userListState.selectedUsers).isEmpty()
            assertThat(initialState.userListState.isSearchActive).isFalse()
            assertThat(initialState.userListState.isMultiSelectionEnabled).isFalse()
            val matrixUser = MatrixUser(UserId("@name:domain"))
            initialState.eventSink(StartChatEvents.StartDM(matrixUser))
            awaitItem().also { state ->
                assertThat(state.startDmAction).isEqualTo(startDMFailureResult)
                executeResult.assertions().isCalledOnce().with(
                    value(matrixUser),
                    value(false),
                    any(),
                )
                state.eventSink(StartChatEvents.CancelStartDM)
            }
            awaitItem().also { state ->
                assertThat(state.startDmAction.isUninitialized()).isTrue()
            }
        }
    }

    @Test
    fun `present - start DM action success scenario`() = runTest {
        val startDMSuccessResult = AsyncAction.Success(A_ROOM_ID)
        val executeResult = lambdaRecorder<MatrixUser, Boolean, MutableState<AsyncAction<RoomId>>, Unit> { _, _, actionState ->
            actionState.value = startDMSuccessResult
        }
        val startDMAction = FakeStartDMAction(executeResult = executeResult)
        val presenter = createStartChatPresenter(startDMAction)
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.startDmAction).isInstanceOf(AsyncAction.Uninitialized::class.java)
            assertThat(initialState.applicationName).isEqualTo(aBuildMeta().applicationName)
            assertThat(initialState.userListState.selectedUsers).isEmpty()
            assertThat(initialState.userListState.isSearchActive).isFalse()
            assertThat(initialState.userListState.isMultiSelectionEnabled).isFalse()
            val matrixUser = MatrixUser(UserId("@name:domain"))
            initialState.eventSink(StartChatEvents.StartDM(matrixUser))
            awaitItem().also { state ->
                assertThat(state.startDmAction).isEqualTo(startDMSuccessResult)
                executeResult.assertions().isCalledOnce().with(
                    value(matrixUser),
                    value(false),
                    any(),
                )
            }
        }
    }

    @Test
    fun `present - start DM action confirmation scenario - cancel`() = runTest {
        val matrixUser = MatrixUser(UserId("@name:domain"))
        val startDMConfirmationResult = ConfirmingStartDmWithMatrixUser(matrixUser, isUserIdentityUnknown = false)
        val executeResult = lambdaRecorder<MatrixUser, Boolean, MutableState<AsyncAction<RoomId>>, Unit> { _, _, actionState ->
            actionState.value = startDMConfirmationResult
        }
        val startDMAction = FakeStartDMAction(executeResult = executeResult)
        val presenter = createStartChatPresenter(startDMAction)
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.startDmAction).isInstanceOf(AsyncAction.Uninitialized::class.java)
            initialState.eventSink(StartChatEvents.StartDM(matrixUser))
            val confirmingState = awaitItem()
            assertThat(confirmingState.startDmAction).isEqualTo(startDMConfirmationResult)
            executeResult.assertions().isCalledOnce().with(
                value(matrixUser),
                value(false),
                any(),
            )
            // Cancelling should not create the DM
            confirmingState.eventSink(StartChatEvents.CancelStartDM)
            val finalState = awaitItem()
            assertThat(finalState.startDmAction.isUninitialized()).isTrue()
            executeResult.assertions().isCalledExactly(1)
        }
    }

    @Test
    fun `present - start DM action confirmation scenario - confirm`() = runTest {
        val matrixUser = MatrixUser(UserId("@name:domain"))
        val startDMConfirmationResult = ConfirmingStartDmWithMatrixUser(matrixUser, isUserIdentityUnknown = false)
        val executeResult = lambdaRecorder<MatrixUser, Boolean, MutableState<AsyncAction<RoomId>>, Unit> { _, _, actionState ->
            actionState.value = startDMConfirmationResult
        }
        val startDMAction = FakeStartDMAction(executeResult = executeResult)
        val presenter = createStartChatPresenter(startDMAction)
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.startDmAction).isInstanceOf(AsyncAction.Uninitialized::class.java)
            initialState.eventSink(StartChatEvents.StartDM(matrixUser))
            val confirmingState = awaitItem()
            assertThat(confirmingState.startDmAction).isEqualTo(startDMConfirmationResult)
            executeResult.assertions().isCalledOnce().with(
                value(matrixUser),
                value(false),
                any(),
            )
            // Start DM again should invoke the action with createIfDmDoesNotExist = true
            confirmingState.eventSink(StartChatEvents.StartDM(matrixUser))
            executeResult.assertions().isCalledExactly(2).withSequence(
                listOf(value(matrixUser), value(false), any()),
                listOf(value(matrixUser), value(true), any()),
            )
        }
    }
}

internal fun createStartChatPresenter(
    startDMAction: StartDMAction = FakeStartDMAction(),
): StartChatPresenter {
    return StartChatPresenter(
        presenterFactory = FakeUserListPresenterFactory(FakeUserListPresenter()),
        userRepository = FakeUserRepository(),
        userListDataStore = UserListDataStore(),
        startDMAction = startDMAction,
        buildMeta = aBuildMeta(),
    )
}
