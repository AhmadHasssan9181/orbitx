/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.room

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.LatestEventValue
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileDetails
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aMessageContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aProfileDetails

fun aRemoteLatestEvent(
    content: EventContent = aMessageContent(),
    timestamp: Long = 0L,
    isOwn: Boolean = false,
    senderId: UserId = A_USER_ID,
    senderProfile: ProfileDetails = aProfileDetails(),
): LatestEventValue.Remote {
    return LatestEventValue.Remote(
        timestamp = timestamp,
        content = content,
        senderId = senderId,
        senderProfile = senderProfile,
        isOwn = isOwn,
    )
}

fun aLocalLatestEvent(
    content: EventContent = aMessageContent(),
    timestamp: Long = 0L,
    isSending: Boolean = false,
    senderId: UserId = A_USER_ID,
    senderProfile: ProfileDetails = aProfileDetails(),
): LatestEventValue.Local {
    return LatestEventValue.Local(
        timestamp = timestamp,
        content = content,
        senderId = senderId,
        senderProfile = senderProfile,
        isSending = isSending,
    )
}
