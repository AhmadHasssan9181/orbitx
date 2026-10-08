/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.sessionstorage.test.InMemorySessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.test.aSessionData
import java.io.File

class AppMigration06Test {
    @Test
    fun `empty cache path should be set to an expected path`() = runTest {
        val sessionStore = InMemorySessionStore(
            initialList = listOf(
                aSessionData(
                    sessionId = A_SESSION_ID.value,
                    sessionPath = "/a/path/to/a/session/AN_ID",
                    cachePath = "",
                )
            )
        )
        val migration = AppMigration06(sessionStore = sessionStore, cacheDirectory = File("/a/path/cache"))
        migration.migrate(true)
        val storedData = sessionStore.getSession(A_SESSION_ID.value)!!
        assertThat(storedData.cachePath).isEqualTo("/a/path/cache/AN_ID")
    }

    @Test
    fun `non empty cache path should not be impacted by the migration`() = runTest {
        val sessionStore = InMemorySessionStore(
            initialList = listOf(
                aSessionData(
                    sessionId = A_SESSION_ID.value,
                    cachePath = "/a/path/existing",
                )
            )
        )
        val migration = AppMigration05(sessionStore = sessionStore, baseDirectory = File("/a/path/cache"))
        migration.migrate(true)
        val storedData = sessionStore.getSession(A_SESSION_ID.value)!!
        assertThat(storedData.cachePath).isEqualTo("/a/path/existing")
    }
}
