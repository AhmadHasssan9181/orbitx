/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.sessionstorage.test.InMemorySessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.test.aSessionData
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.File

class AppMigration05Test {
    @Test
    fun `empty session path should be set to an expected path`() = runTest {
        val sessionStore = InMemorySessionStore(
            initialList = listOf(
                aSessionData(
                    sessionId = A_SESSION_ID.value,
                    sessionPath = "",
                )
            )
        )
        val migration = AppMigration05(sessionStore = sessionStore, baseDirectory = File("/a/path"))
        migration.migrate(true)
        val storedData = sessionStore.getSession(A_SESSION_ID.value)!!
        assertThat(storedData.sessionPath).isEqualTo("/a/path/${A_SESSION_ID.value.replace(':', '_')}")
    }

    @Test
    fun `non empty session path should not be impacted by the migration`() = runTest {
        val sessionStore = InMemorySessionStore(
            initialList = listOf(
                aSessionData(
                    sessionId = A_SESSION_ID.value,
                    sessionPath = "/a/path/existing",
                )
            )
        )
        val migration = AppMigration05(sessionStore = sessionStore, baseDirectory = File("/a/path"))
        migration.migrate(true)
        val storedData = sessionStore.getSession(A_SESSION_ID.value)!!
        assertThat(storedData.sessionPath).isEqualTo("/a/path/existing")
    }
}
