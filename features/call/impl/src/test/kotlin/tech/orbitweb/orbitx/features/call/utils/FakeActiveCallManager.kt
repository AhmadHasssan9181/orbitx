/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.utils

import kotlinx.coroutines.flow.MutableStateFlow
import tech.orbitweb.orbitx.features.call.api.CallData
import tech.orbitweb.orbitx.features.call.impl.notifications.CallNotificationData
import tech.orbitweb.orbitx.features.call.impl.utils.ActiveCall
import tech.orbitweb.orbitx.features.call.impl.utils.ActiveCallManager
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeActiveCallManager(
    var registerIncomingCallResult: (CallNotificationData) -> Unit = {},
    var hangUpCallResult: (CallData, CallNotificationData?) -> Unit = { _, _ -> },
    var joinedCallResult: (CallData) -> Unit = {},
) : ActiveCallManager {
    override val activeCall = MutableStateFlow<ActiveCall?>(null)

    override suspend fun registerIncomingCall(notificationData: CallNotificationData) = simulateLongTask {
        registerIncomingCallResult(notificationData)
    }

    override suspend fun hangUpCall(callData: CallData, notificationData: CallNotificationData?) = simulateLongTask {
        hangUpCallResult(callData, notificationData)
    }

    override suspend fun joinedCall(callData: CallData) = simulateLongTask {
        joinedCallResult(callData)
    }

    fun setActiveCall(value: ActiveCall?) {
        this.activeCall.value = value
    }
}
