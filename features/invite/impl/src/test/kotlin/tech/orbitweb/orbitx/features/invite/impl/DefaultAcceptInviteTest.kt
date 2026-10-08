/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl

import com.google.common.truth.Truth.assertThat
import im.vector.app.features.analytics.plan.JoinedRoom
import tech.orbitweb.orbitx.features.invite.test.InMemorySeenInvitesStore
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.toRoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.room.join.FakeJoinRoom
import tech.orbitweb.orbitx.libraries.push.test.notifications.FakeNotificationCleaner
import tech.orbitweb.orbitx.tests.testutils.lambda.any
import tech.orbitweb.orbitx.tests.testutils.lambda.assert
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultAcceptInviteTest {
    private val roomId = A_ROOM_ID
    private val client = FakeMatrixClient()
    private val seenInvitesStore = InMemorySeenInvitesStore(initialRoomIds = setOf(roomId))

    private val clearMembershipNotificationForRoomLambda =
        lambdaRecorder<SessionId, RoomId, Unit> { _, _ -> }
    private val notificationCleaner =
        FakeNotificationCleaner(clearMembershipNotificationForRoomLambda = clearMembershipNotificationForRoomLambda)

    @Test
    fun `accept invite success scenario`() = runTest {
        val joinRoomLambda =
            lambdaRecorder<RoomIdOrAlias, List<String>, JoinedRoom.Trigger, Result<Unit>> { _, _, _ ->
                Result.success(Unit)
            }

        val acceptInvite = DefaultAcceptInvite(
            client = client,
            notificationCleaner = notificationCleaner,
            joinRoom = FakeJoinRoom(lambda = joinRoomLambda),
            seenInvitesStore = seenInvitesStore
        )

        val result = acceptInvite(roomId)

        assertThat(result.isSuccess).isTrue()

        assert(joinRoomLambda)
            .isCalledOnce()
            .with(value(roomId.toRoomIdOrAlias()), any(), any())

        assert(clearMembershipNotificationForRoomLambda)
            .isCalledOnce()
            .with(value(client.sessionId), value(roomId))

        assertThat(seenInvitesStore.seenRoomIds().first()).isEmpty()
    }

    @Test
    fun `accept invite failure scenario`() = runTest {
        val joinRoomLambda =
            lambdaRecorder<RoomIdOrAlias, List<String>, JoinedRoom.Trigger, Result<Unit>> { _, _, _ ->
                Result.failure(RuntimeException("Join room failed"))
            }

        val acceptInvite = DefaultAcceptInvite(
            client = client,
            notificationCleaner = notificationCleaner,
            joinRoom = FakeJoinRoom(lambda = joinRoomLambda),
            seenInvitesStore = seenInvitesStore
        )

        val result = acceptInvite(roomId)

        assertThat(result.isFailure).isTrue()

        assert(joinRoomLambda)
            .isCalledOnce()
            .with(value(roomId.toRoomIdOrAlias()), any(), any())

        assert(clearMembershipNotificationForRoomLambda).isNeverCalled()

        assertThat(seenInvitesStore.seenRoomIds().first()).containsExactly(roomId)
    }
}
