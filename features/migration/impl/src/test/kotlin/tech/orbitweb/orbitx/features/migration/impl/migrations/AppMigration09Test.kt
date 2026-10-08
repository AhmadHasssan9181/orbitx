/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.migration.impl.migrations

import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClientProvider
import tech.orbitweb.orbitx.libraries.sessionstorage.test.InMemorySessionStore
import tech.orbitweb.orbitx.libraries.sessionstorage.test.aSessionData
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder

class AppMigration09Test {
    @Test
    fun `migration on fresh install does nothing`() = runTest {
        val sessionStore = InMemorySessionStore(initialList = listOf(aSessionData()))
        val getClientLambda = lambdaRecorder<SessionId, Result<MatrixClient>> { Result.success(FakeMatrixClient()) }
        val clientProvider = FakeMatrixClientProvider(getClient = getClientLambda)
        val migration = AppMigration09(sessionStore, clientProvider)
        migration.migrate(isFreshInstall = true)

        getClientLambda.assertions().isNeverCalled()
    }

    @Test
    fun `migration on upgrade should invoke the resetWellKnownConfig method`() = runTest {
        val sessionStore = InMemorySessionStore(initialList = listOf(aSessionData()))
        val resetWellKnownLambda = lambdaRecorder<Result<Unit>> { Result.success(Unit) }
        val getClientLambda = lambdaRecorder<SessionId, Result<MatrixClient>> {
            Result.success(FakeMatrixClient(resetWellKnownConfigLambda = resetWellKnownLambda))
        }
        val clientProvider = FakeMatrixClientProvider(getClient = getClientLambda)
        val migration = AppMigration09(sessionStore, clientProvider)
        migration.migrate(isFreshInstall = false)

        getClientLambda.assertions().isCalledOnce()
        resetWellKnownLambda.assertions().isCalledOnce()
    }
}
