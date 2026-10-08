/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.timeline.reply

import org.matrix.rustcomponents.sdk.EmbeddedEventDetails
import org.matrix.rustcomponents.sdk.InReplyToDetails
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.InReplyTo
import tech.orbitweb.orbitx.libraries.matrix.impl.timeline.item.event.TimelineEventContentMapper
import tech.orbitweb.orbitx.libraries.matrix.impl.timeline.item.event.map

class InReplyToMapper(
    private val timelineEventContentMapper: TimelineEventContentMapper,
) {
    fun map(inReplyToDetails: InReplyToDetails): InReplyTo {
        val inReplyToId = EventId(inReplyToDetails.eventId())
        return when (val event = inReplyToDetails.event()) {
            is EmbeddedEventDetails.Ready -> {
                InReplyTo.Ready(
                    eventId = inReplyToId,
                    content = timelineEventContentMapper.map(event.content),
                    senderId = UserId(event.sender),
                    senderProfile = event.senderProfile.map(),
                )
            }
            is EmbeddedEventDetails.Error -> InReplyTo.Error(
                eventId = inReplyToId,
                message = event.message,
            )
            EmbeddedEventDetails.Pending -> InReplyTo.Pending(
                eventId = inReplyToId,
            )
            is EmbeddedEventDetails.Unavailable -> InReplyTo.NotLoaded(
                eventId = inReplyToId
            )
        }
    }
}
