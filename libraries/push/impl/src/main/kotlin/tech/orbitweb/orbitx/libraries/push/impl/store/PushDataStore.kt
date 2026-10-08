/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.store

import tech.orbitweb.orbitx.libraries.push.api.history.PushHistoryItem
import kotlinx.coroutines.flow.Flow

interface PushDataStore {
    val shouldDisplayBatteryOptimizationBannerFlow: Flow<Boolean>
    val pushCounterFlow: Flow<Int>

    /**
     * Get a flow of list of [PushHistoryItem].
     */
    fun getPushHistoryItemsFlow(): Flow<List<PushHistoryItem>>

    /**
     * Reset the push counter to 0, and clear the database.
     */
    suspend fun reset()
}
