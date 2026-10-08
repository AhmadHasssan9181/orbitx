/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class AppMigration04Test : RobolectricTest() {
    @Test
    fun `test migration`() = runTest {
        val context = InstrumentationRegistry.getInstrumentation().context

        // Create fake temporary file at the path to be deleted
        val file = context.getDatabasePath(AppMigration04.NOTIFICATION_FILE_NAME)
        file.parentFile?.mkdirs()
        file.createNewFile()
        assertThat(file.exists()).isTrue()

        val migration = AppMigration04(context)

        migration.migrate(true)

        // Check that the file has been deleted
        assertThat(file.exists()).isFalse()
    }
}
