/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.roomlist

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.DynamicRoomList
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomList
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomListService

class FakeRoomListService(
    private val subscribeToVisibleRoomsLambda: (List<RoomId>) -> Unit = {},
    private val createRoomListLambda: (pageSize: Int) -> DynamicRoomList = { pageSize -> FakeDynamicRoomList(pageSize = pageSize) },
    override val allRooms: RoomList = createRoomListLambda(Int.MAX_VALUE),
    private val isInitialSyncLambda: () -> Boolean = { true },
) : RoomListService {
    private val roomListStateFlow = MutableStateFlow<RoomListService.State>(RoomListService.State.Idle)
    private val syncIndicatorStateFlow = MutableStateFlow<RoomListService.SyncIndicator>(RoomListService.SyncIndicator.Hide)

    override val isInitialSyncDone: Boolean
        get() = isInitialSyncLambda()

    suspend fun postState(state: RoomListService.State) {
        roomListStateFlow.emit(state)
    }

    suspend fun postSyncIndicator(value: RoomListService.SyncIndicator) {
        syncIndicatorStateFlow.emit(value)
    }

    override fun createRoomList(
        pageSize: Int,
        source: RoomList.Source,
        coroutineScope: CoroutineScope,
    ) = createRoomListLambda(pageSize)

    override suspend fun subscribeToVisibleRooms(roomIds: List<RoomId>) {
        subscribeToVisibleRoomsLambda(roomIds)
    }

    override val state: StateFlow<RoomListService.State> = roomListStateFlow

    override val syncIndicator: StateFlow<RoomListService.SyncIndicator> = syncIndicatorStateFlow
}
