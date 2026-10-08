/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply

import androidx.compose.runtime.Immutable
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkParser
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.InReplyTo
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileDetails
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StickerContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.TextMessageType
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.toPlainText

@Immutable
sealed interface InReplyToDetails {
    data class Ready(
        val eventId: EventId,
        val senderId: UserId,
        val senderProfile: ProfileDetails,
        val eventContent: EventContent?,
        val textContent: String?,
    ) : InReplyToDetails

    data class Loading(val eventId: EventId) : InReplyToDetails
    data class Error(val eventId: EventId, val message: String) : InReplyToDetails
}

fun InReplyToDetails.eventId() = when (this) {
    is InReplyToDetails.Ready -> eventId
    is InReplyToDetails.Loading -> eventId
    is InReplyToDetails.Error -> eventId
}

fun InReplyTo.map(
    permalinkParser: PermalinkParser,
) = when (this) {
    is InReplyTo.Ready -> InReplyToDetails.Ready(
        eventId = eventId,
        senderId = senderId,
        senderProfile = senderProfile,
        eventContent = content,
        textContent = when (content) {
            is MessageContent -> {
                val messageContent = content as MessageContent
                (messageContent.type as? TextMessageType)?.toPlainText(permalinkParser = permalinkParser) ?: messageContent.body
            }
            is StickerContent -> {
                val stickerContent = content as StickerContent
                stickerContent.body
            }
            else -> null
        }
    )
    is InReplyTo.Error -> InReplyToDetails.Error(eventId, message)
    is InReplyTo.NotLoaded -> InReplyToDetails.Loading(eventId)
    is InReplyTo.Pending -> InReplyToDetails.Loading(eventId)
}
