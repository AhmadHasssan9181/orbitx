/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.roomselect.impl

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.matrix.api.room.CurrentUserMembership
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomList
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomListFilter
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomListService
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.updateVisibleRange
import tech.orbitweb.orbitx.libraries.matrix.ui.model.SelectRoomInfo
import tech.orbitweb.orbitx.libraries.matrix.ui.model.toSelectRoomInfo

private const val PAGE_SIZE = 30

/**
 * DataSource for RoomSummaryDetails that can be filtered by a search query,
 * and which only includes rooms the user has joined.
 */
@AssistedInject
class RoomSelectSearchDataSource(
    @Assisted coroutineScope: CoroutineScope,
    roomListService: RoomListService,
    coroutineDispatchers: CoroutineDispatchers,
) {
    @AssistedFactory
    interface Factory {
        fun create(coroutineScope: CoroutineScope): RoomSelectSearchDataSource
    }

    private val roomList = roomListService.createRoomList(
        pageSize = PAGE_SIZE,
        source = RoomList.Source.All,
        coroutineScope = coroutineScope
    )

    val roomInfoList: Flow<ImmutableList<SelectRoomInfo>> = roomList.summaries
        .map { roomSummaries ->
            roomSummaries
                .filter { it.info.currentUserMembership == CurrentUserMembership.JOINED }
                .distinctBy { it.roomId } // This should be removed once we're sure no duplicate Rooms can be received
                .map { roomSummary -> roomSummary.toSelectRoomInfo() }
                .toImmutableList()
        }
        .flowOn(coroutineDispatchers.computation)

    suspend fun updateVisibleRange(visibleRange: IntRange) {
        roomList.updateVisibleRange(visibleRange)
    }

    suspend fun setSearchQuery(searchQuery: String) = coroutineScope {
        val filter = if (searchQuery.isBlank()) {
            RoomListFilter.all()
        } else {
            RoomListFilter.NormalizedMatchRoomName(searchQuery)
        }
        roomList.updateFilter(filter)
    }
}
