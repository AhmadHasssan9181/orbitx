/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.eventformatter.impl

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.core.extensions.DEFAULT_SAFE_LENGTH
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.eventformatter.api.RoomLatestEventFormatter
import tech.orbitweb.orbitx.libraries.eventformatter.impl.mode.RenderingMode
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkParser
import tech.orbitweb.orbitx.libraries.matrix.api.roomlist.LatestEventValue
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.AudioMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.CallNotifyContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EmoteMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseMessageLikeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseStateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FileMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.GalleryMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ImageMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LegacyCallInviteContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LiveLocationContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocationMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.NoticeMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.OtherMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileChangeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RedactedContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RoomMembershipContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StickerContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StickerMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.TextMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnableToDecryptContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnknownContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VideoMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VoiceMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.getDisambiguatedDisplayName
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.toPlainText
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.services.toolbox.api.strings.StringProvider

@ContributesBinding(SessionScope::class)
class DefaultRoomLatestEventFormatter(
    private val sp: StringProvider,
    private val roomMembershipContentFormatter: RoomMembershipContentFormatter,
    private val profileChangeContentFormatter: ProfileChangeContentFormatter,
    private val stateContentFormatter: StateContentFormatter,
    private val rtcNotificationContentFormatter: RtcNotificationContentFormatter,
    private val permalinkParser: PermalinkParser,
) : RoomLatestEventFormatter {
    override fun format(
        latestEvent: LatestEventValue.Local,
        isDmRoom: Boolean,
    ): CharSequence? = formatContent(
        content = latestEvent.content,
        isDmRoom = isDmRoom,
        isOutgoing = true,
        senderId = latestEvent.senderId,
        senderDisambiguatedDisplayName = latestEvent.senderProfile.getDisambiguatedDisplayName(latestEvent.senderId)
    )

    override fun format(
        latestEvent: LatestEventValue.Remote,
        isDmRoom: Boolean,
    ): CharSequence? = formatContent(
        content = latestEvent.content,
        isDmRoom = isDmRoom,
        isOutgoing = latestEvent.isOwn,
        senderId = latestEvent.senderId,
        senderDisambiguatedDisplayName = latestEvent.senderProfile.getDisambiguatedDisplayName(latestEvent.senderId)
    )

    private fun formatContent(
        content: EventContent,
        isDmRoom: Boolean,
        isOutgoing: Boolean,
        senderId: UserId,
        senderDisambiguatedDisplayName: String
    ): CharSequence? {
        return when (content) {
            is MessageContent -> content.process(senderDisambiguatedDisplayName, isDmRoom, isOutgoing)
            RedactedContent -> {
                val message = sp.getString(CommonStrings.common_message_removed)
                message.prefixIfNeeded(senderDisambiguatedDisplayName, isDmRoom, isOutgoing)
            }
            is StickerContent -> {
                content.bestDescription.prefixWith(sp.getString(CommonStrings.common_sticker))
                    .prefixIfNeeded(senderDisambiguatedDisplayName, isDmRoom, isOutgoing)
            }
            is UnableToDecryptContent -> {
                val message = sp.getString(CommonStrings.common_waiting_for_decryption_key)
                message.prefixIfNeeded(senderDisambiguatedDisplayName, isDmRoom, isOutgoing)
            }
            is RoomMembershipContent -> {
                roomMembershipContentFormatter.format(content, senderDisambiguatedDisplayName, isOutgoing)
            }
            is ProfileChangeContent -> {
                profileChangeContentFormatter.format(content, senderId, senderDisambiguatedDisplayName, isOutgoing)
            }
            is StateContent -> {
                stateContentFormatter.format(content, senderDisambiguatedDisplayName, isOutgoing, RenderingMode.RoomList)
            }
            is PollContent -> {
                content.question.prefixWith(sp.getString(CommonStrings.common_poll_summary_prefix))
                    .prefixIfNeeded(senderDisambiguatedDisplayName, isDmRoom, isOutgoing)
            }
            is FailedToParseMessageLikeContent, is FailedToParseStateContent, is UnknownContent -> {
                val message = sp.getString(CommonStrings.common_unsupported_event)
                message.prefixIfNeeded(senderDisambiguatedDisplayName, isDmRoom, isOutgoing)
            }
            is LiveLocationContent -> {
                val message = sp.getString(CommonStrings.common_shared_live_location)
                message.prefixIfNeeded(senderDisambiguatedDisplayName, isDmRoom, isOutgoing)
            }
            is LegacyCallInviteContent -> sp.getString(CommonStrings.common_unsupported_call)
            is CallNotifyContent -> rtcNotificationContentFormatter.format(content, isDmRoom)
        }?.take(DEFAULT_SAFE_LENGTH)
    }

    private fun MessageContent.process(
        senderDisambiguatedDisplayName: String,
        isDmRoom: Boolean,
        isOutgoing: Boolean
    ): CharSequence {
        val message = when (val messageType: MessageType = type) {
            // Doesn't need a prefix
            is EmoteMessageType -> {
                return "* $senderDisambiguatedDisplayName ${messageType.body}"
            }
            is TextMessageType -> {
                messageType.toPlainText(permalinkParser)
            }
            is VideoMessageType -> {
                messageType.toPlainText(permalinkParser).prefixWith(sp.getString(CommonStrings.common_video))
            }
            is ImageMessageType -> {
                messageType.toPlainText(permalinkParser).prefixWith(sp.getString(CommonStrings.common_image))
            }
            is StickerMessageType -> {
                messageType.toPlainText(permalinkParser).prefixWith(sp.getString(CommonStrings.common_sticker))
            }
            is LocationMessageType -> {
                sp.getString(CommonStrings.common_shared_location)
            }
            is FileMessageType -> {
                messageType.toPlainText(permalinkParser).prefixWith(sp.getString(CommonStrings.common_file))
            }
            is AudioMessageType -> {
                messageType.toPlainText(permalinkParser).prefixWith(sp.getString(CommonStrings.common_audio))
            }
            is VoiceMessageType -> {
                messageType
                    .toPlainText(permalinkParser, "")
                    .takeIf { it.isNotEmpty() }
                    ?.prefixWith(sp.getString(CommonStrings.common_voice_message))
                    ?: sp.getString(CommonStrings.common_voice_message)
            }
            is OtherMessageType -> {
                messageType.body
            }
            is GalleryMessageType -> {
                messageType.body.prefixWith(sp.getString(CommonStrings.common_gallery))
            }
            is NoticeMessageType -> {
                messageType.body
            }
        }
        return message.prefixIfNeeded(senderDisambiguatedDisplayName, isDmRoom, isOutgoing)
    }

    private fun CharSequence.prefixIfNeeded(
        senderDisambiguatedDisplayName: String,
        isDmRoom: Boolean,
        isOutgoing: Boolean,
    ): CharSequence = if (isDmRoom) {
        this
    } else {
        prefixWith(
            if (isOutgoing) {
                sp.getString(CommonStrings.common_you)
            } else {
                senderDisambiguatedDisplayName
            }
        )
    }
}
