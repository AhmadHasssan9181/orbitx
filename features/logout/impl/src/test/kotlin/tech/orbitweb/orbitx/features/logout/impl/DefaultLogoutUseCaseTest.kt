/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package tech.orbitweb.orbitx.features.logout.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClientProvider
import tech.orbitweb.orbitx.libraries.sessionstorage.test.InMemorySessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.test.aSessionData
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value

class DefaultLogoutUseCaseTest {
    @Test
    fun `test logout from one session`() = runTest {
        val logoutLambda1 = lambdaRecorder<Boolean, Boolean, Unit> { _, _ -> }
        val client1 = FakeMatrixClient(A_USER_ID).apply {
            logoutLambda = logoutLambda1
        }
        val sut = DefaultLogoutUseCase(
            sessionStore = InMemorySessionStore(
                initialList = listOf(
                    aSessionData(sessionId = A_USER_ID.value),
                )
            ),
            matrixClientProvider = FakeMatrixClientProvider(
                getClient = { sessionId ->
                    when (sessionId) {
                        A_USER_ID -> Result.success(client1)
                        else -> error("Unexpected sessionId")
                    }
                }
            ),
        )
        sut.logoutAll(ignoreSdkError = true)
        logoutLambda1.assertions().isCalledOnce().with(value(true), value(true))
    }

    @Test
    fun `test logout from several sessions`() = runTest {
        val logoutLambda1 = lambdaRecorder<Boolean, Boolean, Unit> { _, _ -> }
        val logoutLambda2 = lambdaRecorder<Boolean, Boolean, Unit> { _, _ -> }
        val client1 = FakeMatrixClient(A_USER_ID).apply {
            logoutLambda = logoutLambda1
        }
        val client2 = FakeMatrixClient(A_USER_ID_2).apply {
            logoutLambda = logoutLambda2
        }
        val sut = DefaultLogoutUseCase(
            sessionStore = InMemorySessionStore(
                initialList = listOf(
                    aSessionData(sessionId = A_USER_ID.value),
                    aSessionData(sessionId = A_USER_ID_2.value),
                )
            ),
            matrixClientProvider = FakeMatrixClientProvider(
                getClient = { sessionId ->
                    when (sessionId) {
                        A_USER_ID -> Result.success(client1)
                        A_USER_ID_2 -> Result.success(client2)
                        else -> error("Unexpected sessionId")
                    }
                }
            ),
        )
        sut.logoutAll(ignoreSdkError = true)
        logoutLambda1.assertions().isCalledOnce().with(value(true), value(true))
        logoutLambda2.assertions().isCalledOnce().with(value(true), value(true))
    }

    @Test
    fun `test logout session not found is ignored`() = runTest {
        val sut = DefaultLogoutUseCase(
            sessionStore = InMemorySessionStore(
                initialList = listOf(
                    aSessionData(sessionId = A_USER_ID.value),
                )
            ),
            matrixClientProvider = FakeMatrixClientProvider(
                getClient = { sessionId ->
                    when (sessionId) {
                        A_USER_ID -> Result.failure(Exception("Session not found"))
                        else -> error("Unexpected sessionId")
                    }
                }
            ),
        )
        sut.logoutAll(ignoreSdkError = true)
        // No error
    }

    @Test
    fun `test logout no sessions`() = runTest {
        val sut = DefaultLogoutUseCase(
            sessionStore = InMemorySessionStore(
                initialList = emptyList()
            ),
            matrixClientProvider = FakeMatrixClientProvider(
                getClient = { sessionId ->
                    when (sessionId) {
                        else -> error("Unexpected sessionId")
                    }
                }
            ),
        )
        sut.logoutAll(ignoreSdkError = true)
        // No error
    }
}
