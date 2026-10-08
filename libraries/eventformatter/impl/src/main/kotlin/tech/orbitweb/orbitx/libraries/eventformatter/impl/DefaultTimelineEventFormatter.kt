/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.eventformatter.impl

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.core.meta.BuildMeta
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.eventformatter.api.TimelineEventFormatter
import tech.orbitweb.orbitx.libraries.eventformatter.impl.mode.RenderingMode
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.CallNotifyContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseMessageLikeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseStateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LegacyCallInviteContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LiveLocationContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileChangeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RedactedContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RoomMembershipContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StickerContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnableToDecryptContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnknownContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.getDisambiguatedDisplayName
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

@ContributesBinding(SessionScope::class)
class DefaultTimelineEventFormatter(
    private val sp: StringProvider,
    private val buildMeta: BuildMeta,
    private val roomMembershipContentFormatter: RoomMembershipContentFormatter,
    private val profileChangeContentFormatter: ProfileChangeContentFormatter,
    private val stateContentFormatter: StateContentFormatter,
) : TimelineEventFormatter {
    override fun format(event: EventTimelineItem): CharSequence? {
        val isOutgoing = event.isOwn
        val senderDisambiguatedDisplayName = event.senderProfile.getDisambiguatedDisplayName(event.sender)
        return format(event.content, isOutgoing, event.sender, senderDisambiguatedDisplayName)
    }

    override fun format(content: EventContent, isOutgoing: Boolean, sender: UserId, senderDisambiguatedDisplayName: String): CharSequence? {
        return when (content) {
            is RoomMembershipContent -> {
                roomMembershipContentFormatter.format(content, senderDisambiguatedDisplayName, isOutgoing)
            }
            is ProfileChangeContent -> {
                profileChangeContentFormatter.format(content, sender, senderDisambiguatedDisplayName, isOutgoing)
            }
            is StateContent -> {
                stateContentFormatter.format(content, senderDisambiguatedDisplayName, isOutgoing, RenderingMode.Timeline)
            }
            is CallNotifyContent -> {
                sp.getString(CommonStrings.common_call_started)
            }
            RedactedContent,
            is LegacyCallInviteContent,
            is StickerContent,
            is PollContent,
            is UnableToDecryptContent,
            is MessageContent,
            is FailedToParseMessageLikeContent,
            is FailedToParseStateContent,
            is LiveLocationContent,
            is UnknownContent -> {
                if (buildMeta.isDebuggable) {
                    error("You should not use this formatter for this event content: $content")
                }
                sp.getString(CommonStrings.common_unsupported_event)
            }
        }
    }
}
