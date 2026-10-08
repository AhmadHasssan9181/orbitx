/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.tasks

import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeMarkAllRoomsAsRead(
    private val invokeLambda: suspend () -> Result<Unit> = { Result.success(Unit) },
) : MarkAllRoomsAsRead {
    var invokeCallCount = 0
        private set

    override suspend fun invoke(): Result<Unit> = simulateLongTask {
        invokeCallCount++
        invokeLambda()
    }
}
