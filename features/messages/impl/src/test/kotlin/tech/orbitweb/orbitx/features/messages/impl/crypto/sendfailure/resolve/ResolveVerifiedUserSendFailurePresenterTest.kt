/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.resolve

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.VerifiedUserSendFailure
import tech.orbitweb.orbitx.features.messages.impl.crypto.sendfailure.VerifiedUserSendFailureFactory
import tech.orbitweb.orbitx.features.messages.impl.fixtures.aMessageEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocalEventSendState
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_TRANSACTION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeBaseRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.test

class ResolveVerifiedUserSendFailurePresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state`() = runTest {
        val presenter = createResolveVerifiedUserSendFailurePresenter()
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
        }
    }

    @Test
    fun `present - remote message scenario`() = runTest {
        val presenter = createResolveVerifiedUserSendFailurePresenter()
        presenter.test {
            val sentMessage = aMessageEvent()
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(sentMessage))
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - sent message scenario`() = runTest {
        val presenter = createResolveVerifiedUserSendFailurePresenter()
        presenter.test {
            val sentMessage = aMessageEvent(
                sendState = LocalEventSendState.Sent(AN_EVENT_ID)
            )
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(sentMessage))
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - unknown failed message scenario`() = runTest {
        val presenter = createResolveVerifiedUserSendFailurePresenter()
        presenter.test {
            val failedMessage = aMessageEvent(
                sendState = LocalEventSendState.Failed.Unknown("")
            )
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(failedMessage))
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - verified user unsigned device failure dismiss scenario`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
            userDisplayNameResult = { userId ->
                Result.success(userId.value)
            },
        )
        )
        val presenter = createResolveVerifiedUserSendFailurePresenter(room)
        presenter.test {
            val failedMessage = aVerifiedUserHasUnsignedDeviceFailedMessage()
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(failedMessage))
            skipItems(1)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.UnsignedDevice.FromYou)
                state.eventSink(ResolveVerifiedUserSendFailureEvent.Dismiss)
            }
            skipItems(1)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            }
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - verified user unsigned device failure retry scenario`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
            userDisplayNameResult = { userId ->
                Result.success(userId.value)
            },
        )
        )
        val presenter = createResolveVerifiedUserSendFailurePresenter(room)
        presenter.test {
            val failedMessage = aVerifiedUserHasUnsignedDeviceFailedMessage()
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(failedMessage))

            skipItems(1)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.UnsignedDevice.FromYou)
                state.eventSink(ResolveVerifiedUserSendFailureEvent.Retry)
            }
            awaitItem().also { state ->
                assertThat(state.retryAction).isEqualTo(AsyncAction.Loading)
            }
            skipItems(2)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
                assertThat(state.retryAction).isEqualTo(AsyncAction.Success(Unit))
            }
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - verified user unsigned device failure resolve and resend scenario`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
            userDisplayNameResult = { userId ->
                Result.success(userId.value)
            },
        ),
            ignoreDeviceTrustAndResendResult = { _, _ ->
                Result.success(Unit)
            },
        )
        val presenter = createResolveVerifiedUserSendFailurePresenter(room)
        presenter.test {
            val failedMessage = aVerifiedUserHasUnsignedDeviceFailedMessage()
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(failedMessage))

            skipItems(1)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.UnsignedDevice.FromYou)
                state.eventSink(ResolveVerifiedUserSendFailureEvent.ResolveAndResend)
            }
            awaitItem().also { state ->
                assertThat(state.resolveAction).isEqualTo(AsyncAction.Loading)
            }
            // This should move to the next user
            skipItems(2)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.UnsignedDevice.FromOther(A_USER_ID_2.value))
                assertThat(state.resolveAction).isEqualTo(AsyncAction.Success(Unit))
                state.eventSink(ResolveVerifiedUserSendFailureEvent.ResolveAndResend)
            }

            skipItems(3)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            }
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - verified user unsigned device failure resolve and resend scenario with error`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
            userDisplayNameResult = { userId ->
                Result.success(userId.value)
            },
        ),
            ignoreDeviceTrustAndResendResult = { _, _ ->
                Result.failure(Exception())
            },
        )
        val presenter = createResolveVerifiedUserSendFailurePresenter(room)
        presenter.test {
            val failedMessage = aVerifiedUserHasUnsignedDeviceFailedMessage()
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(failedMessage))

            skipItems(1)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.UnsignedDevice.FromYou)
                state.eventSink(ResolveVerifiedUserSendFailureEvent.ResolveAndResend)
            }
            awaitItem().also { state ->
                assertThat(state.resolveAction).isEqualTo(AsyncAction.Loading)
            }
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.UnsignedDevice.FromYou)
                assertThat(state.resolveAction).isInstanceOf(AsyncAction.Failure::class.java)
            }
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - verified user changed identity failure retry scenario`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
            userDisplayNameResult = { userId ->
                Result.success(userId.value)
            },
        )
        )
        val presenter = createResolveVerifiedUserSendFailurePresenter(room)
        presenter.test {
            val failedMessage = aVerifiedUserChangedIdentityMessage()
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(failedMessage))

            skipItems(1)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.ChangedIdentity(A_USER_ID.value))
                state.eventSink(ResolveVerifiedUserSendFailureEvent.Retry)
            }
            awaitItem().also { state ->
                assertThat(state.retryAction).isEqualTo(AsyncAction.Loading)
            }
            skipItems(2)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
                assertThat(state.retryAction).isEqualTo(AsyncAction.Success(Unit))
            }
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - verified user changed identity failure resolve and resend scenario`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
            userDisplayNameResult = { userId ->
                Result.success(userId.value)
            },
        ),
            withdrawVerificationAndResendResult = { _, _ ->
                Result.success(Unit)
            },
        )
        val presenter = createResolveVerifiedUserSendFailurePresenter(room)
        presenter.test {
            val failedMessage = aVerifiedUserChangedIdentityMessage()
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(failedMessage))

            skipItems(1)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.ChangedIdentity(A_USER_ID.value))
                state.eventSink(ResolveVerifiedUserSendFailureEvent.ResolveAndResend)
            }
            awaitItem().also { state ->
                assertThat(state.resolveAction).isEqualTo(AsyncAction.Loading)
            }
            // This should move to the next user
            skipItems(2)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.ChangedIdentity(A_USER_ID_2.value))
                assertThat(state.resolveAction).isEqualTo(AsyncAction.Success(Unit))
                state.eventSink(ResolveVerifiedUserSendFailureEvent.ResolveAndResend)
            }

            skipItems(3)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            }
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `present - verified user changed identity failure resolve and resend scenario with error`() = runTest {
        val room = FakeJoinedRoom(
            baseRoom = FakeBaseRoom(
            userDisplayNameResult = { userId ->
                Result.success(userId.value)
            },
        ),
            withdrawVerificationAndResendResult = { _, _ ->
                Result.failure(Exception())
            },
        )
        val presenter = createResolveVerifiedUserSendFailurePresenter(room)
        presenter.test {
            val failedMessage = aVerifiedUserChangedIdentityMessage()
            val initialState = awaitItem()
            assertThat(initialState.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.None)
            initialState.eventSink(ResolveVerifiedUserSendFailureEvent.ComputeForMessage(failedMessage))

            skipItems(1)
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.ChangedIdentity(A_USER_ID.value))
                state.eventSink(ResolveVerifiedUserSendFailureEvent.ResolveAndResend)
            }
            awaitItem().also { state ->
                assertThat(state.resolveAction).isEqualTo(AsyncAction.Loading)
            }
            awaitItem().also { state ->
                assertThat(state.verifiedUserSendFailure).isEqualTo(VerifiedUserSendFailure.ChangedIdentity(A_USER_ID.value))
                assertThat(state.resolveAction).isInstanceOf(AsyncAction.Failure::class.java)
            }
            ensureAllEventsConsumed()
        }
    }

    private fun aVerifiedUserHasUnsignedDeviceFailedMessage(): TimelineItem.Event {
        return aMessageEvent(
            transactionId = A_TRANSACTION_ID,
            sendState = LocalEventSendState.Failed.VerifiedUserHasUnsignedDevice(
                mapOf(
                    A_USER_ID to emptyList(),
                    A_USER_ID_2 to emptyList()
                )
            )
        )
    }

    private fun aVerifiedUserChangedIdentityMessage(): TimelineItem.Event {
        return aMessageEvent(
            transactionId = A_TRANSACTION_ID,
            sendState = LocalEventSendState.Failed.VerifiedUserChangedIdentity(
                listOf(A_USER_ID, A_USER_ID_2)
            )
        )
    }

    private fun createResolveVerifiedUserSendFailurePresenter(
        room: FakeJoinedRoom = FakeJoinedRoom(),
    ): ResolveVerifiedUserSendFailurePresenter {
        return ResolveVerifiedUserSendFailurePresenter(
            room = room,
            verifiedUserSendFailureFactory = VerifiedUserSendFailureFactory(room),
        )
    }
}
