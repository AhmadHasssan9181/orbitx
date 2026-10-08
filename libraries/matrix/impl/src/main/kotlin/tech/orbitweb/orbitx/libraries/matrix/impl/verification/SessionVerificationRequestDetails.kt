/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.verification

import tech.orbitweb.orbitx.libraries.matrix.api.core.DeviceId
import tech.orbitweb.orbitx.libraries.matrix.api.core.FlowId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.verification.SessionVerificationRequestDetails
import tech.orbitweb.orbitx.libraries.matrix.api.verification.VerificationRequest
import tech.orbitweb.orbitx.libraries.matrix.impl.mapper.map
import org.matrix.rustcomponents.sdk.SessionVerificationRequestDetails as RustSessionVerificationRequestDetails

fun RustSessionVerificationRequestDetails.map() = SessionVerificationRequestDetails(
    senderProfile = senderProfile.map(),
    flowId = FlowId(flowId),
    deviceId = DeviceId(deviceId),
    deviceDisplayName = deviceDisplayName,
    firstSeenTimestamp = firstSeenTimestamp.toLong(),
)

fun RustSessionVerificationRequestDetails.toVerificationRequest(currentUserId: UserId): VerificationRequest.Incoming {
    val details = map()
    return if (currentUserId == details.senderProfile.userId) {
        VerificationRequest.Incoming.OtherSession(details)
    } else {
        VerificationRequest.Incoming.User(details)
    }
}
