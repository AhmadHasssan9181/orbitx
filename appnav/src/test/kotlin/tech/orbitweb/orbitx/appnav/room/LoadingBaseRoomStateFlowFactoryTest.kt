/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav.room

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomList
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeBaseRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.test.roomlist.FakeDynamicRoomList
import tech.orbitweb.orbitx.libraries.matrix.test.roomlist.FakeRoomListService
import tech.orbitweb.orbitx.libraries.matrix.ui.room.LoadingRoomState
import tech.orbitweb.orbitx.libraries.matrix.ui.room.LoadingRoomStateFlowFactory

class LoadingBaseRoomStateFlowFactoryTest {
    @Test
    fun `flow should emit only Loaded when we already pass a JoinedRoom`() = runTest {
        val room = FakeJoinedRoom(baseRoom = FakeBaseRoom(sessionId = A_SESSION_ID, roomId = A_ROOM_ID))
        val matrixClient = FakeMatrixClient(A_SESSION_ID)
        val flowFactory = LoadingRoomStateFlowFactory(matrixClient)
        flowFactory
            .create(lifecycleScope = this, roomId = A_ROOM_ID, joinedRoom = room)
            .test {
                assertThat(awaitItem()).isEqualTo(LoadingRoomState.Loaded(room))
                ensureAllEventsConsumed()
            }
    }

    @Test
    fun `flow should emit Loading and then Loaded when there is a room in cache`() = runTest {
        val room = FakeJoinedRoom(baseRoom = FakeBaseRoom(sessionId = A_SESSION_ID, roomId = A_ROOM_ID))
        val matrixClient = FakeMatrixClient(A_SESSION_ID).apply {
            givenGetRoomResult(A_ROOM_ID, room)
        }
        val flowFactory = LoadingRoomStateFlowFactory(matrixClient)
        flowFactory
            .create(lifecycleScope = this, roomId = A_ROOM_ID, joinedRoom = null)
            .test {
                assertThat(awaitItem()).isEqualTo(LoadingRoomState.Loading)
                assertThat(awaitItem()).isEqualTo(LoadingRoomState.Loaded(room))
            }
    }

    @Test
    fun `flow should emit Loading and then Loaded when there is a room in cache after SS is loaded`() = runTest {
        val room = FakeJoinedRoom(baseRoom = FakeBaseRoom(sessionId = A_SESSION_ID, roomId = A_ROOM_ID))
        val roomList = FakeDynamicRoomList()
        val roomListService = FakeRoomListService(allRooms = roomList)
        val matrixClient = FakeMatrixClient(A_SESSION_ID, roomListService = roomListService)
        val flowFactory = LoadingRoomStateFlowFactory(matrixClient)
        flowFactory
            .create(lifecycleScope = this, roomId = A_ROOM_ID, joinedRoom = null)
            .test {
                assertThat(awaitItem()).isEqualTo(LoadingRoomState.Loading)
                matrixClient.givenGetRoomResult(A_ROOM_ID, room)
                roomList.loadingState.emit(RoomList.LoadingState.Loaded(1))
                assertThat(awaitItem()).isEqualTo(LoadingRoomState.Loaded(room))
            }
    }

    @Test
    fun `flow should emit Loading and then Error when there is no room in cache after SS is loaded`() = runTest {
        val roomList = FakeDynamicRoomList()
        val roomListService = FakeRoomListService(allRooms = roomList)
        val matrixClient = FakeMatrixClient(A_SESSION_ID, roomListService = roomListService)
        val flowFactory = LoadingRoomStateFlowFactory(matrixClient)
        flowFactory
            .create(lifecycleScope = this, roomId = A_ROOM_ID, joinedRoom = null)
            .test {
                assertThat(awaitItem()).isEqualTo(LoadingRoomState.Loading)
                roomList.loadingState.emit(RoomList.LoadingState.Loaded(1))
                assertThat(awaitItem()).isEqualTo(LoadingRoomState.Error)
            }
    }
}
