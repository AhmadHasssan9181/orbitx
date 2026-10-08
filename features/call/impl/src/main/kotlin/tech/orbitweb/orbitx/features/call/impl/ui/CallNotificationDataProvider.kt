/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.call.impl.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.call.impl.notifications.CallNotificationData
import tech.orbitweb.orbitx.libraries.designsystem.preview.ROOM_NAME
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_BOB
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

open class CallNotificationDataProvider : PreviewParameterProvider<CallNotificationData> {
    override val values: Sequence<CallNotificationData>
        get() = sequenceOf(
            aCallNotificationData(
                audioOnly = false
            ),
            aCallNotificationData(
                audioOnly = true
            ),
        )
}

internal fun aCallNotificationData(
    audioOnly: Boolean
): CallNotificationData {
    return CallNotificationData(
        sessionId = SessionId("@alice:matrix.org"),
        roomId = RoomId("!1234:matrix.org"),
        eventId = EventId("\$asdadadsad:matrix.org"),
        senderId = UserId("@bob:matrix.org"),
        roomName = ROOM_NAME,
        senderName = USER_NAME_BOB,
        avatarUrl = null,
        notificationChannelId = "incoming_call",
        timestamp = 0L,
        textContent = null,
        expirationTimestamp = 1000L,
        audioOnly = audioOnly
    )
}
