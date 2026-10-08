/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.test.push

import tech.orbitweb.orbitx.libraries.push.api.push.FetchPushForegroundServiceManager

class FakeFetchPushForegroundServiceManager(
    private val lock: () -> Boolean = { true },
    private val unlock: () -> Boolean = { true },
) : FetchPushForegroundServiceManager {
    override fun start(): Boolean {
        return lock.invoke()
    }

    override suspend fun stop(): Boolean {
        return unlock.invoke()
    }
}
