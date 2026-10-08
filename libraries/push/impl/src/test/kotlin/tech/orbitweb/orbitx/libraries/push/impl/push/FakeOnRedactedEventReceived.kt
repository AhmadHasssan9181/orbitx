/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.push

import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.ResolvedPushEvent
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeOnRedactedEventReceived(
    private val onRedactedEventsReceivedResult: (List<ResolvedPushEvent.Redaction>) -> Unit = { lambdaError() },
) : OnRedactedEventReceived {
    override suspend fun onRedactedEventsReceived(redactions: List<ResolvedPushEvent.Redaction>) {
        onRedactedEventsReceivedResult(redactions)
    }
}
