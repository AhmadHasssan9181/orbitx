/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.pushproviders.api.PushData

/**
 * In this case, the format is:
 * <pre>
 * {
 *     "event_id":"$anEventId",
 *     "room_id":"!aRoomId",
 *     "unread":"1",
 *     "prio":"high",
 *     "cs":"<client_secret>"
 * }
 * </pre>
 * .
 */
data class PushDataFirebase(
    val eventId: String?,
    val roomId: String?,
    val unread: Int?,
    val clientSecret: String?
)

fun PushDataFirebase.toPushData(): PushData? {
    val safeEventId = eventId?.let(::EventId) ?: return null
    val safeRoomId = roomId?.let(::RoomId) ?: return null
    val safeClientSecret = clientSecret ?: return null
    return PushData(
        eventId = safeEventId,
        roomId = safeRoomId,
        unread = unread,
        clientSecret = safeClientSecret,
    )
}
