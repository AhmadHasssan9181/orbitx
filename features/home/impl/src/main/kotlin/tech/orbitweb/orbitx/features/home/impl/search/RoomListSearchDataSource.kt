/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.search

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
import tech.orbitweb.orbitx.features.home.impl.datasource.RoomListRoomSummaryFactory
import tech.orbitweb.orbitx.features.home.impl.model.RoomListRoomSummary
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomList
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomListFilter
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.RoomListService
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.updateVisibleRange

private const val PAGE_SIZE = 30

@AssistedInject
class RoomListSearchDataSource(
    @Assisted coroutineScope: CoroutineScope,
    roomListService: RoomListService,
    coroutineDispatchers: CoroutineDispatchers,
    private val roomSummaryFactory: RoomListRoomSummaryFactory,
) {
    @AssistedFactory
    interface Factory {
        fun create(coroutineScope: CoroutineScope): RoomListSearchDataSource
    }

    private val roomList = roomListService.createRoomList(
        pageSize = PAGE_SIZE,
        source = RoomList.Source.All,
        coroutineScope = coroutineScope
    )

    val roomSummaries: Flow<ImmutableList<RoomListRoomSummary>> = roomList.summaries
        .map { roomSummaries ->
            roomSummaries
                .map(roomSummaryFactory::create)
                .toImmutableList()
        }
        .flowOn(coroutineDispatchers.computation)

    suspend fun updateVisibleRange(visibleRange: IntRange) {
        roomList.updateVisibleRange(visibleRange)
    }

    suspend fun setSearchQuery(searchQuery: String) = coroutineScope {
        val filter = if (searchQuery.isBlank()) {
            RoomListFilter.None
        } else {
            RoomListFilter.NormalizedMatchRoomName(searchQuery)
        }
        roomList.updateFilter(filter)
    }
}
