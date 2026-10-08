/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room.threads

import kotlinx.coroutines.flow.Flow

interface ThreadsListService {
    fun subscribeToItemUpdates(): Flow<List<ThreadListItem>>
    fun subscribeToPaginationUpdates(): Flow<ThreadListPaginationStatus>
    suspend fun paginate(): Result<Unit>
    suspend fun reset(): Result<Unit>
    fun destroy()
}
