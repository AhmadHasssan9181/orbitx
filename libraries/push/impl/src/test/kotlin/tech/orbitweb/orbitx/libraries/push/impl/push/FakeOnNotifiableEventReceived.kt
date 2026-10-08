/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.push

import tech.orbitweb.orbitx.libraries.push.impl.notifications.model.NotifiableEvent
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeOnNotifiableEventReceived(
    private val onNotifiableEventsReceivedResult: (List<NotifiableEvent>) -> Unit = { lambdaError() },
) : OnNotifiableEventReceived {
    override fun onNotifiableEventsReceived(notifiableEvents: List<NotifiableEvent>) {
        onNotifiableEventsReceivedResult(notifiableEvents)
    }
}
