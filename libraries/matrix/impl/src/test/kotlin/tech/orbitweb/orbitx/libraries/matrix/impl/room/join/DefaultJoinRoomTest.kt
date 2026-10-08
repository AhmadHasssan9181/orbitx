/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.join

import com.google.common.truth.Truth.assertThat
import im.vector.app.features.analytics.plan.JoinedRoom
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.toRoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.impl.analytics.toAnalyticsJoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SERVER_LIST
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeBaseRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomInfo
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value

class DefaultJoinRoomTest {
    @Test
    fun `when using roomId and there is no server names, the classic join room API is used`() = runTest {
        val roomInfo = aRoomInfo()
        val joinRoomLambda = lambdaRecorder { _: RoomId -> Result.success(roomInfo) }
        val joinRoomByIdOrAliasLambda = lambdaRecorder { _: RoomIdOrAlias, _: List<String> -> Result.success(roomInfo) }
        val roomResult = FakeBaseRoom().apply {
            givenRoomInfo(aRoomInfo())
        }
        val aTrigger = JoinedRoom.Trigger.MobilePermalink
        val client: MatrixClient = FakeMatrixClient().also {
            it.joinRoomLambda = joinRoomLambda
            it.joinRoomByIdOrAliasLambda = joinRoomByIdOrAliasLambda
            it.givenGetRoomResult(
                roomId = A_ROOM_ID,
                result = roomResult
            )
        }
        val analyticsService = FakeAnalyticsService()
        val sut = DefaultJoinRoom(
            client = client,
            analyticsService = analyticsService,
        )
        sut.invoke(A_ROOM_ID.toRoomIdOrAlias(), emptyList(), aTrigger)
        joinRoomByIdOrAliasLambda
            .assertions()
            .isNeverCalled()
        joinRoomLambda
            .assertions()
            .isCalledOnce()
            .with(
                value(A_ROOM_ID)
            )
        assertThat(analyticsService.capturedEvents).containsExactly(
            roomResult.toAnalyticsJoinedRoom(aTrigger)
        )
    }

    @Test
    fun `when using roomId and server names are available, joinRoomByIdOrAlias API is used`() = runTest {
        val roomInfo = aRoomInfo()
        val joinRoomLambda = lambdaRecorder { _: RoomId -> Result.success(roomInfo) }
        val joinRoomByIdOrAliasLambda = lambdaRecorder { _: RoomIdOrAlias, _: List<String> -> Result.success(roomInfo) }
        val roomResult = FakeBaseRoom().apply {
            givenRoomInfo(aRoomInfo())
        }
        val aTrigger = JoinedRoom.Trigger.MobilePermalink
        val client: MatrixClient = FakeMatrixClient().also {
            it.joinRoomLambda = joinRoomLambda
            it.joinRoomByIdOrAliasLambda = joinRoomByIdOrAliasLambda
            it.givenGetRoomResult(
                roomId = A_ROOM_ID,
                result = roomResult
            )
        }
        val analyticsService = FakeAnalyticsService()
        val sut = DefaultJoinRoom(
            client = client,
            analyticsService = analyticsService,
        )
        sut.invoke(A_ROOM_ID.toRoomIdOrAlias(), A_SERVER_LIST, aTrigger)
        joinRoomByIdOrAliasLambda
            .assertions()
            .isCalledOnce()
            .with(
                value(A_ROOM_ID.toRoomIdOrAlias()),
                value(A_SERVER_LIST)
            )
        joinRoomLambda
            .assertions()
            .isNeverCalled()
        assertThat(analyticsService.capturedEvents).containsExactly(
            roomResult.toAnalyticsJoinedRoom(aTrigger)
        )
    }

    @Test
    fun `when using roomAlias, joinRoomByIdOrAlias API is used`() = runTest {
        val roomInfo = aRoomInfo()
        val joinRoomLambda = lambdaRecorder { _: RoomId -> Result.success(roomInfo) }
        val joinRoomByIdOrAliasLambda = lambdaRecorder { _: RoomIdOrAlias, _: List<String> -> Result.success(roomInfo) }
        val roomResult = FakeBaseRoom().apply {
            givenRoomInfo(aRoomInfo())
        }
        val aTrigger = JoinedRoom.Trigger.MobilePermalink
        val client: MatrixClient = FakeMatrixClient().also {
            it.joinRoomLambda = joinRoomLambda
            it.joinRoomByIdOrAliasLambda = joinRoomByIdOrAliasLambda
            it.givenGetRoomResult(
                roomId = A_ROOM_ID,
                result = roomResult
            )
        }
        val analyticsService = FakeAnalyticsService()
        val sut = DefaultJoinRoom(
            client = client,
            analyticsService = analyticsService,
        )
        sut.invoke(A_ROOM_ALIAS.toRoomIdOrAlias(), A_SERVER_LIST, aTrigger)
        joinRoomByIdOrAliasLambda
            .assertions()
            .isCalledOnce()
            .with(
                value(A_ROOM_ALIAS.toRoomIdOrAlias()),
                value(emptyList<String>())
            )
        joinRoomLambda
            .assertions()
            .isNeverCalled()
        assertThat(analyticsService.capturedEvents).containsExactly(
            roomResult.toAnalyticsJoinedRoom(aTrigger)
        )
    }
}
