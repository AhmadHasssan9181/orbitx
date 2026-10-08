/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeFirebaseTroubleshooter(
    private val troubleShootResult: () -> Result<Unit> = { Result.success(Unit) }
) : FirebaseTroubleshooter {
    override suspend fun troubleshoot(): Result<Unit> = simulateLongTask {
        troubleShootResult()
    }
}
