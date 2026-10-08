/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.roomdirectory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomDirectoryList

class FakeRoomDirectoryList(
    override val state: Flow<RoomDirectoryList.SearchResult> = emptyFlow(),
    val filterLambda: (String?, Int, String?) -> Result<Unit> = { _, _, _ -> Result.success(Unit) },
    val loadMoreLambda: () -> Result<Unit> = { Result.success(Unit) }
) : RoomDirectoryList {
    override suspend fun filter(filter: String?, batchSize: Int, viaServerName: String?): Result<Unit> = filterLambda(filter, batchSize, viaServerName)

    override suspend fun loadMore(): Result<Unit> = loadMoreLambda()
}
