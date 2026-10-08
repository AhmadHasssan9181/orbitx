/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.sessionstorage.test

import tech.orbitweb.orbitx.libraries.cachestore.api.CacheData
import tech.orbitweb.orbitx.libraries.cachestore.api.CacheStore

class InMemoryCacheStore(
    initialData: Map<String, CacheData> = emptyMap(),
) : CacheStore {
    val dataMap = initialData.toMutableMap()

    override suspend fun storeData(key: String, data: CacheData) {
        dataMap[key] = data
    }

    override suspend fun getData(key: String): CacheData? {
        return dataMap[key]
    }

    override suspend fun deleteData(key: String) {
        dataMap.remove(key)
    }

    override suspend fun deleteAll() {
        dataMap.clear()
    }
}
