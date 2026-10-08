/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.test

import tech.orbitweb.orbitx.features.call.api.CallData
import tech.orbitweb.orbitx.features.call.api.ElementCallEntryPoint
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeElementCallEntryPoint(
    var startCallResult: (CallData) -> Unit = { lambdaError() },
    var handleIncomingCallResult: (
        CallData,
        EventId,
        UserId,
        String?,
        String?,
        String?,
        String,
        String?,
    ) -> Unit = { _, _, _, _, _, _, _, _ -> lambdaError() }
) : ElementCallEntryPoint {
    override fun startCall(callData: CallData) {
        startCallResult(callData)
    }

    override suspend fun handleIncomingCall(
        callData: CallData,
        eventId: EventId,
        senderId: UserId,
        roomName: String?,
        senderName: String?,
        avatarUrl: String?,
        timestamp: Long,
        expirationTimestamp: Long,
        notificationChannelId: String,
        textContent: String?,
    ) {
        handleIncomingCallResult(
            callData,
            eventId,
            senderId,
            roomName,
            senderName,
            avatarUrl,
            notificationChannelId,
            textContent,
        )
    }
}
