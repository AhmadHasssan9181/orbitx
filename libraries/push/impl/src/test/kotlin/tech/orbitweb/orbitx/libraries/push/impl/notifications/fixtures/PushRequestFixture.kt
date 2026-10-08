/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.notifications.fixtures

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.push.impl.db.PushRequest
import tech.orbitweb.orbitx.libraries.push.impl.push.PushRequestStatus

fun aPushRequest(
    sessionId: SessionId = A_SESSION_ID,
    roomId: RoomId = A_ROOM_ID,
    eventId: EventId = AN_EVENT_ID,
    providerInfo: String = "firebase",
    status: PushRequestStatus = PushRequestStatus.PENDING,
    retries: Int = 0,
) = PushRequest(
    pushDate = System.currentTimeMillis(),
    providerInfo = providerInfo,
    eventId = eventId.value,
    roomId = roomId.value,
    sessionId = sessionId.value,
    status = status.value,
    retries = retries.toLong(),
)
