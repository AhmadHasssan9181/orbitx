/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.push

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.push.impl.store.DefaultPushDataStore

interface IncrementPushDataStore {
    suspend fun incrementPushCounter()
}

@ContributesBinding(AppScope::class)
class DefaultIncrementPushDataStore(
    private val defaultPushDataStore: DefaultPushDataStore
) : IncrementPushDataStore {
    override suspend fun incrementPushCounter() {
        defaultPushDataStore.incrementPushCounter()
    }
}
