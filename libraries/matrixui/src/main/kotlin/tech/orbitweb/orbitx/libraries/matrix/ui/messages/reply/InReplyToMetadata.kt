/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.AudioMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.CallNotifyContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseMessageLikeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseStateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FileMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ImageMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LegacyCallInviteContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LiveLocationContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocationMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileChangeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RedactedContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RoomMembershipContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StickerContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnableToDecryptContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnknownContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VideoMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VoiceMessageType
import tech.orbitweb.orbitx.libraries.matrix.ui.components.AttachmentThumbnailInfo
import tech.orbitweb.orbitx.libraries.matrix.ui.components.AttachmentThumbnailType
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply.InReplyToMetadata.Text
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply.InReplyToMetadata.Thumbnail
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Immutable
internal sealed interface InReplyToMetadata {
    data class Thumbnail(
        val attachmentThumbnailInfo: AttachmentThumbnailInfo
    ) : InReplyToMetadata {
        val text: String = attachmentThumbnailInfo.textContent.orEmpty()
    }

    data class Text(
        val text: String
    ) : InReplyToMetadata

    sealed interface Informative : InReplyToMetadata

    data object Redacted : Informative
    data object UnableToDecrypt : Informative
}

/**
 * Computes metadata for the in reply to message.
 *
 * Metadata can be either a thumbnail with a text OR just a text.
 */
@Composable
internal fun InReplyToDetails.Ready.metadata(hideImage: Boolean): InReplyToMetadata? = when (eventContent) {
    is MessageContent -> when (val type = eventContent.type) {
        is ImageMessageType -> Thumbnail(
            AttachmentThumbnailInfo(
                thumbnailSource = (type.info?.thumbnailSource ?: type.source).takeUnless { hideImage },
                textContent = eventContent.body,
                type = AttachmentThumbnailType.Image,
                blurHash = type.info?.blurhash,
            )
        )
        is VideoMessageType -> Thumbnail(
            AttachmentThumbnailInfo(
                thumbnailSource = type.info?.thumbnailSource?.takeUnless { hideImage },
                textContent = eventContent.body,
                type = AttachmentThumbnailType.Video,
                blurHash = type.info?.blurhash,
            )
        )
        is FileMessageType -> Thumbnail(
            AttachmentThumbnailInfo(
                thumbnailSource = type.info?.thumbnailSource?.takeUnless { hideImage },
                textContent = eventContent.body,
                type = AttachmentThumbnailType.File,
            )
        )
        is LocationMessageType -> Thumbnail(
            AttachmentThumbnailInfo(
                textContent = stringResource(CommonStrings.common_shared_location),
                type = AttachmentThumbnailType.Location,
            )
        )
        is AudioMessageType -> Thumbnail(
            AttachmentThumbnailInfo(
                textContent = eventContent.body,
                type = AttachmentThumbnailType.Audio,
            )
        )
        is VoiceMessageType -> Thumbnail(
            AttachmentThumbnailInfo(
                textContent = stringResource(CommonStrings.common_voice_message),
                type = AttachmentThumbnailType.Voice,
            )
        )
        else -> Text(textContent ?: eventContent.body)
    }
    is StickerContent -> Thumbnail(
        AttachmentThumbnailInfo(
            thumbnailSource = eventContent.source.takeUnless { hideImage },
            textContent = eventContent.body,
            type = AttachmentThumbnailType.Image,
            blurHash = eventContent.info.blurhash,
        )
    )
    is PollContent -> Thumbnail(
        AttachmentThumbnailInfo(
            textContent = eventContent.question,
            type = AttachmentThumbnailType.Poll,
        )
    )
    is RedactedContent -> InReplyToMetadata.Redacted
    is UnableToDecryptContent -> InReplyToMetadata.UnableToDecrypt
    is FailedToParseMessageLikeContent,
    is FailedToParseStateContent,
    is ProfileChangeContent,
    is RoomMembershipContent,
    is StateContent,
    UnknownContent,
    is LegacyCallInviteContent,
    is CallNotifyContent,
    is LiveLocationContent,
    null -> null
}
