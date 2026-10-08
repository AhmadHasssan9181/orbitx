/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.crash

import kotlinx.coroutines.flow.Flow

interface CrashDataStore {
    fun setCrashData(crashData: String)

    suspend fun resetAppHasCrashed()
    fun appHasCrashed(): Flow<Boolean>
    fun crashInfo(): Flow<String>

    suspend fun reset()
}
