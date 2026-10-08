/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.roomlist

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileDetails

sealed interface LatestEventValue {
    data object None : LatestEventValue
    data class Remote(
        val timestamp: Long,
        val content: EventContent,
        val senderId: UserId,
        val senderProfile: ProfileDetails,
        val isOwn: Boolean,
    ) : LatestEventValue

    data class Local(
        val timestamp: Long,
        val content: EventContent,
        val senderId: UserId,
        val senderProfile: ProfileDetails,
        val isSending: Boolean,
    ) : LatestEventValue

    data class RoomInvite(
        val timestamp: Long,
        val inviterId: UserId?,
        val invitedProfile: ProfileDetails,
    ) : LatestEventValue
}
