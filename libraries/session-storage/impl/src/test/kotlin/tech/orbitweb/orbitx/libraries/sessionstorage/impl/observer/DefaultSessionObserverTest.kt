/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.sessionstorage.impl.observer

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.sessionstorage.impl.DatabaseSessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.impl.SessionDatabase
import tech.orbitweb.orbitx.libraries.sessionstorage.impl.aDbSessionData
import tech.orbitweb.orbitx.libraries.sessionstorage.impl.toApiModel
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultSessionObserverTest {
    private lateinit var database: SessionDatabase
    private lateinit var databaseSessionStore: DatabaseSessionStore

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        // Initialise in memory SQLite driver
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        SessionDatabase.Schema.create(driver)
        database = SessionDatabase(driver)
        databaseSessionStore = DatabaseSessionStore(
            database = database,
            dispatchers = CoroutineDispatchers(
                io = UnconfinedTestDispatcher(),
                computation = UnconfinedTestDispatcher(),
                main = UnconfinedTestDispatcher(),
            )
        )
    }

    @Test
    fun `adding data invokes onSessionCreated`() = runTest {
        val sessionData = aDbSessionData()
        val sut = createDefaultSessionObserver()
        runCurrent()
        val listener = TestSessionListener()
        sut.addListener(listener)
        databaseSessionStore.addSession(sessionData.toApiModel())
        listener.assertEvents(TestSessionListener.Event.Created(sessionData.userId))
        sut.removeListener(listener)
    }

    @Test
    fun `adding and deleting data invokes onSessionCreated and onSessionDeleted`() = runTest {
        val sessionData = aDbSessionData()
        val sut = createDefaultSessionObserver()
        runCurrent()
        val listener = TestSessionListener()
        sut.addListener(listener)
        databaseSessionStore.addSession(sessionData.toApiModel())
        listener.assertEvents(TestSessionListener.Event.Created(sessionData.userId))
        databaseSessionStore.removeSession(sessionData.userId)
        listener.assertEvents(
            TestSessionListener.Event.Created(sessionData.userId),
            TestSessionListener.Event.Deleted(sessionData.userId, true),
        )
    }

    @Test
    fun `adding and deleting data twice invokes onSessionCreated and onSessionDeleted`() = runTest {
        val sessionData1 = aDbSessionData(userId = "user1")
        val sessionData2 = aDbSessionData(userId = "user2")
        val sut = createDefaultSessionObserver()
        runCurrent()
        val listener = TestSessionListener()
        sut.addListener(listener)
        databaseSessionStore.addSession(sessionData1.toApiModel())
        databaseSessionStore.addSession(sessionData2.toApiModel())
        databaseSessionStore.removeSession(sessionData2.userId)
        databaseSessionStore.removeSession(sessionData1.userId)
        listener.assertEvents(
            TestSessionListener.Event.Created(sessionData1.userId),
            TestSessionListener.Event.Created(sessionData2.userId),
            TestSessionListener.Event.Deleted(sessionData2.userId, wasLastSession = false),
            TestSessionListener.Event.Deleted(sessionData1.userId, wasLastSession = true),
        )
    }

    private fun TestScope.createDefaultSessionObserver(): DefaultSessionObserver {
        return DefaultSessionObserver(
            sessionStore = databaseSessionStore,
            coroutineScope = backgroundScope,
            dispatchers = testCoroutineDispatchers(useUnconfinedTestDispatcher = true),
        )
    }
}
