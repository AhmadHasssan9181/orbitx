/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_SENDER
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.matrix.api.poll.PollKind
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.EventThreadInfo
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.AudioMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EmoteMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FileMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ImageMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocationMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.NoticeMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileDetails
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RedactedContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StickerMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.TextMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnableToDecryptContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VideoMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VoiceMessageType
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

open class InReplyToDetailsProvider : PreviewParameterProvider<InReplyToDetails> {
    override val values: Sequence<InReplyToDetails>
        get() = sequenceOf(
            aMessageContent(
                body = "Message which are being replied.",
                type = TextMessageType("Message which are being replied.", null)
            ),
            aMessageContent(
                body = "Message which are being replied, and which was long enough to be displayed on two lines (only!).",
                type = TextMessageType("Message which are being replied, and which was long enough to be displayed on two lines (only!).", null)
            ),
            aMessageContent(
                body = "Video",
                type = VideoMessageType("Video", null, null, MediaSource("url"), null),
            ),
            aMessageContent(
                body = "Audio",
                type = AudioMessageType("Audio", null, null, MediaSource("url"), null),
            ),
            aMessageContent(
                body = "Voice",
                type = VoiceMessageType("Voice", null, null, MediaSource("url"), null, null),
            ),
            aMessageContent(
                body = "Image",
                type = ImageMessageType("Image", null, null, MediaSource("url"), null),
            ),
            aMessageContent(
                body = "Sticker",
                type = StickerMessageType("Image", null, null, MediaSource("url"), null),
            ),
            aMessageContent(
                body = "File",
                type = FileMessageType("File", null, null, MediaSource("url"), null),
            ),
            aMessageContent(
                body = "Location",
                type = LocationMessageType("Location", "geo:1,2", null, assetType = null),
            ),
            aMessageContent(
                body = "Notice",
                type = NoticeMessageType("Notice", null),
            ),
            aMessageContent(
                body = "Emote",
                type = EmoteMessageType("Emote", null),
            ),
            PollContent(
                question = "Poll which are being replied.",
                kind = PollKind.Disclosed,
                maxSelections = 1u,
                answers = persistentListOf(),
                votes = persistentMapOf(),
                endTime = null,
                isEdited = false,
                threadInfo = null,
            ),
        ).map {
            aInReplyToDetails(
                eventContent = it,
            )
        }
}

class InReplyToDetailsDisambiguatedProvider : InReplyToDetailsProvider() {
    override val values: Sequence<InReplyToDetails>
        get() = sequenceOf(
            aMessageContent(
                body = "Message which are being replied.",
                type = TextMessageType("Message which are being replied.", null)
            ),
        ).map {
            aInReplyToDetails(
                displayNameAmbiguous = true,
                eventContent = it,
            )
        }
}

class InReplyToDetailsInformativeProvider : InReplyToDetailsProvider() {
    override val values: Sequence<InReplyToDetails>
        get() = sequenceOf(
            RedactedContent,
            UnableToDecryptContent(data = UnableToDecryptContent.Data.Unknown, threadInfo = null),
        ).map {
            aInReplyToDetails(
                eventContent = it,
            )
        }
}

class InReplyToDetailsOtherProvider : InReplyToDetailsProvider() {
    override val values: Sequence<InReplyToDetails>
        get() = sequenceOf(
            InReplyToDetails.Loading(eventId = EventId("\$anEventId")),
            InReplyToDetails.Error(eventId = EventId("\$anEventId"), message = "An error message."),
        )
}

private fun aMessageContent(
    body: String,
    type: MessageType,
    threadInfo: EventThreadInfo? = null,
) = MessageContent(
    body = body,
    inReplyTo = null,
    isEdited = false,
    threadInfo = threadInfo,
    type = type,
)

private fun aInReplyToDetails(
    eventContent: EventContent,
    displayNameAmbiguous: Boolean = false,
) = InReplyToDetails.Ready(
    eventId = EventId("\$event"),
    eventContent = eventContent,
    senderId = UserId("@Sender:domain"),
    senderProfile = aProfileDetailsReady(
        displayNameAmbiguous = displayNameAmbiguous,
    ),
    textContent = (eventContent as? MessageContent)?.body.orEmpty(),
)

fun aProfileDetailsReady(
    displayName: String? = USER_NAME_SENDER,
    displayNameAmbiguous: Boolean = false,
    avatarUrl: String? = null,
) = ProfileDetails.Ready(
    displayName = displayName,
    displayNameAmbiguous = displayNameAmbiguous,
    avatarUrl = avatarUrl,
)
