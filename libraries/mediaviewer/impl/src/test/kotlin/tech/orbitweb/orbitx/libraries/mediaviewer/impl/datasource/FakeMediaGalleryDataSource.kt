/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.datasource

import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.GroupedMediaItems
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeMediaGalleryDataSource(
    initialData: AsyncData<GroupedMediaItems> = AsyncData.Uninitialized,
    private val isReadyResult: () -> Boolean = { true },
    private val startLambda: () -> Unit = { lambdaError() },
    private val loadMoreLambda: (Timeline.PaginationDirection) -> Unit = { lambdaError() },
    private val deleteItemLambda: (EventId) -> Unit = { lambdaError() },
) : MediaGalleryDataSource {
    override fun start(coroutineScope: CoroutineScope) = startLambda()

    private val groupedMediaItemsFlow = MutableStateFlow(initialData)

    override val isReady: Boolean get() = isReadyResult()

    override fun groupedMediaItemsFlow(): Flow<AsyncData<GroupedMediaItems>> {
        return groupedMediaItemsFlow
    }

    suspend fun emitGroupedMediaItems(groupedMediaItems: AsyncData<GroupedMediaItems>) {
        groupedMediaItemsFlow.emit(groupedMediaItems)
    }

    override fun getLastData(): AsyncData<GroupedMediaItems> {
        return groupedMediaItemsFlow.replayCache.firstOrNull() ?: AsyncData.Uninitialized
    }

    override suspend fun loadMore(direction: Timeline.PaginationDirection) {
        loadMoreLambda(direction)
    }

    override suspend fun deleteItem(eventId: EventId) {
        deleteItemLambda(eventId)
    }
}
