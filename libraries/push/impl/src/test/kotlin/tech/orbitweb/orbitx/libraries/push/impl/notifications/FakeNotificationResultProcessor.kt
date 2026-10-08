/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications

import tech.orbitweb.orbitx.libraries.push.impl.db.PushRequest
import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.ResolvedPushEvent
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeNotificationResultProcessor(
    private val emit: (Map<PushRequest, Result<ResolvedPushEvent>>) -> Unit = { lambdaError() },
    private val start: () -> Unit = { lambdaError() },
    private val stop: () -> Unit = { lambdaError() },
) : NotificationResultProcessor {
    override suspend fun emit(results: Map<PushRequest, Result<ResolvedPushEvent>>) {
        return emit.invoke(results)
    }

    override fun start() {
        start.invoke()
    }

    override fun stop() {
        stop.invoke()
    }
}
