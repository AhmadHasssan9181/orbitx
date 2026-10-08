/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.spaces

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoomList
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask
import java.util.Optional

class FakeSpaceRoomList(
    override val spaceId: RoomId = A_ROOM_ID,
    initialSpaceFlowValue: SpaceRoom? = null,
    initialSpaceRoomsValue: List<SpaceRoom> = emptyList(),
    initialSpaceRoomList: SpaceRoomList.PaginationStatus = SpaceRoomList.PaginationStatus.Loading,
    private val paginateResult: () -> Result<Unit> = { lambdaError() },
    private val resetResult: () -> Result<Unit> = { lambdaError() },
) : SpaceRoomList {
    private val currentSpaceMutableStateFlow: MutableStateFlow<Optional<SpaceRoom>> = MutableStateFlow(Optional.ofNullable(initialSpaceFlowValue))
    override val currentSpaceFlow: StateFlow<Optional<SpaceRoom>> = currentSpaceMutableStateFlow.asStateFlow()

    fun emitCurrentSpace(value: SpaceRoom?) {
        currentSpaceMutableStateFlow.value = Optional.ofNullable(value)
    }

    private val _spaceRoomsFlow = MutableStateFlow<List<SpaceRoom>>(initialSpaceRoomsValue)

    override val spaceRoomsFlow: Flow<List<SpaceRoom>> = _spaceRoomsFlow.asStateFlow()

    fun emitSpaceRooms(value: List<SpaceRoom>) {
        _spaceRoomsFlow.value = value
    }

    private val _paginationStatusFlow: MutableStateFlow<SpaceRoomList.PaginationStatus> = MutableStateFlow(initialSpaceRoomList)
    override val paginationStatusFlow: StateFlow<SpaceRoomList.PaginationStatus> = _paginationStatusFlow.asStateFlow()

    fun emitPaginationStatus(value: SpaceRoomList.PaginationStatus) {
        _paginationStatusFlow.value = value
    }

    override suspend fun paginate(): Result<Unit> = simulateLongTask {
        paginateResult()
    }

    override suspend fun reset(): Result<Unit> = simulateLongTask {
        resetResult()
    }

    override fun destroy() {
        // No op
    }
}
