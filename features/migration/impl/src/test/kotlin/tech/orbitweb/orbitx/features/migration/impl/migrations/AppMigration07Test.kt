/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.rageshake.test.logs.FakeLogFilesRemover
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.File

class AppMigration07Test {
    @Test
    fun `test migration`() = runTest {
        val performLambda = lambdaRecorder<(File) -> Boolean, Unit> { predicate ->
            // Test the predicate
            assertThat(predicate(File("logs-0433.log.gz"))).isTrue()
            assertThat(predicate(File("logs.2024-08-01-20.log.gz"))).isFalse()
        }
        val logsFileRemover = FakeLogFilesRemover(performLambda = performLambda)
        val migration = AppMigration07(logsFileRemover)
        migration.migrate(true)
        performLambda.assertions().isCalledOnce()
    }
}
