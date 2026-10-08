/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SendHandle
import tech.orbitweb.orbitx.libraries.matrix.api.core.TransactionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.EventThreadInfo
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.TimelineItemDebugInfo

data class EventTimelineItem(
    val eventId: EventId?,
    val transactionId: TransactionId?,
    val isEditable: Boolean,
    val canBeRepliedTo: Boolean,
    val isOwn: Boolean,
    val isRemote: Boolean,
    val localSendState: LocalEventSendState?,
    val reactions: ImmutableList<EventReaction>,
    val receipts: ImmutableList<Receipt>,
    val sender: UserId,
    val senderProfile: ProfileDetails,
    val timestamp: Long,
    val content: EventContent,
    val origin: TimelineItemEventOrigin?,
    val timelineItemDebugInfoProvider: TimelineItemDebugInfoProvider,
    val messageShieldProvider: MessageShieldProvider,
    val sendHandleProvider: SendHandleProvider,
    /**
     * If the keys to this message were forwarded by another user via history sharing (MSC4268), the ID of that user.
     * If this is set, then [messageShieldProvider] will also return [MessageShield.AuthenticityNotGuaranteed].
     */
    val forwarder: UserId?,
    /** If [forwarder] is set, the profile of the forwarding user, if it was cached at the time this `EventTimelineItem` was created. */
    val forwarderProfile: ProfileDetails?,
) {
    fun inReplyTo(): InReplyTo? {
        return (content as? MessageContent)?.inReplyTo
    }

    fun threadInfo(): EventThreadInfo? = when (content) {
        is MessageContent -> content.threadInfo
        is PollContent -> content.threadInfo
        is StickerContent -> content.threadInfo
        is UnableToDecryptContent -> content.threadInfo
        else -> null
    }

    fun hasNotLoadedInReplyTo(): Boolean {
        val details = inReplyTo()
        return details is InReplyTo.NotLoaded
    }
}

fun interface TimelineItemDebugInfoProvider {
    operator fun invoke(): TimelineItemDebugInfo
}

fun interface MessageShieldProvider {
    operator fun invoke(strict: Boolean): MessageShield?
}

fun interface SendHandleProvider {
    operator fun invoke(): SendHandle?
}
