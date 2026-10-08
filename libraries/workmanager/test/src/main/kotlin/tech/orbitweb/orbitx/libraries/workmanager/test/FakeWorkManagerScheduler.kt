/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.workmanager.test

import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.workmanager.api.WorkManagerRequestBuilder
import tech.orbitweb.orbitx.libraries.workmanager.api.WorkManagerRequestType
import tech.orbitweb.orbitx.libraries.workmanager.api.WorkManagerScheduler
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeWorkManagerScheduler(
    private val submitLambda: (WorkManagerRequestBuilder) -> Unit = { lambdaError() },
    private val hasPendingWorkLambda: (SessionId, WorkManagerRequestType) -> Boolean = { _, _ -> false },
    private val cancelLambda: (SessionId, WorkManagerRequestType?) -> Unit = { _, _ -> lambdaError() },
) : WorkManagerScheduler {
    override suspend fun submit(workManagerRequestBuilder: WorkManagerRequestBuilder) {
        submitLambda(workManagerRequestBuilder)
    }

    override fun hasPendingWork(sessionId: SessionId, requestType: WorkManagerRequestType): Boolean {
        return hasPendingWorkLambda(sessionId, requestType)
    }

    override fun cancel(sessionId: SessionId, requestType: WorkManagerRequestType?) {
        cancelLambda(sessionId, requestType)
    }
}
