/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import tech.orbitweb.orbitx.features.rageshake.test.logs.FakeLogFilesRemover
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AppMigration03Test {
    @Test
    fun `test migration`() = runTest {
        val logsFileRemover = FakeLogFilesRemover()
        val migration = AppMigration03(migration01 = AppMigration01(logsFileRemover))

        migration.migrate(true)

        logsFileRemover.performLambda.assertions().isCalledOnce()
    }
}
