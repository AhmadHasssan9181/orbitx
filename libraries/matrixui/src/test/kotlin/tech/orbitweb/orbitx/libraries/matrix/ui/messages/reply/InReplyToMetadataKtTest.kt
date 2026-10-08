/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.media.AudioInfo
import tech.orbitweb.orbitx.libraries.matrix.api.media.FileInfo
import tech.orbitweb.orbitx.libraries.matrix.api.media.ImageInfo
import tech.orbitweb.orbitx.libraries.matrix.api.media.VideoInfo
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.AudioMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseMessageLikeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseStateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FileMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ImageMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocationMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.OtherState
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileChangeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileDetails
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RedactedContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StickerContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnableToDecryptContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnknownContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VideoMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VoiceMessageType
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.media.aMediaSource
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aMessageContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aPollContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aProfileDetails
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.item.event.aRoomMembershipContent
import tech.orbitweb.orbitx.libraries.matrix.ui.components.A_BLUR_HASH
import tech.orbitweb.orbitx.libraries.matrix.ui.components.AttachmentThumbnailInfo
import tech.orbitweb.orbitx.libraries.matrix.ui.components.AttachmentThumbnailType
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import tech.orbitweb.orbitx.tests.testutils.withConfigurationAndContext
import kotlin.time.Duration.Companion.minutes

class InReplyToMetadataKtTest : RobolectricTest() {
    @Test
    fun `any message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(eventContent = aMessageContent()).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(InReplyToMetadata.Text("textContent"))
            }
        }
    }

    @Test
    fun `an image message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aMessageContent(
                    messageType = ImageMessageType(
                        filename = "filename",
                        caption = null,
                        formattedCaption = null,
                        source = aMediaSource(),
                        info = anImageInfo(),
                    )
                )
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = aMediaSource(),
                            textContent = "body",
                            type = AttachmentThumbnailType.Image,
                            blurHash = A_BLUR_HASH,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `an image message content, no thumbnail`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aMessageContent(
                    messageType = ImageMessageType(
                        filename = "filename",
                        caption = "caption",
                        formattedCaption = null,
                        source = aMediaSource(),
                        info = anImageInfo(),
                    )
                )
            ).metadata(hideImage = true)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = null,
                            textContent = "body",
                            type = AttachmentThumbnailType.Image,
                            blurHash = A_BLUR_HASH,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a sticker message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = StickerContent(
                    filename = "filename",
                    body = "body",
                    info = anImageInfo(),
                    source = aMediaSource(url = "url"),
                    threadInfo = null,
                )
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = aMediaSource(url = "url"),
                            textContent = "body",
                            type = AttachmentThumbnailType.Image,
                            blurHash = A_BLUR_HASH,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a sticker message content, no thumbnail`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = StickerContent(
                    filename = "filename",
                    body = "body",
                    info = anImageInfo(),
                    source = aMediaSource(url = "url"),
                    threadInfo = null,
                )
            ).metadata(hideImage = true)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = null,
                            textContent = "body",
                            type = AttachmentThumbnailType.Image,
                            blurHash = A_BLUR_HASH,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a video message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aMessageContent(
                    messageType = VideoMessageType(
                        filename = "filename",
                        caption = null,
                        formattedCaption = null,
                        source = aMediaSource(),
                        info = aVideoInfo(),
                    )
                )
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = aMediaSource(),
                            textContent = "body",
                            type = AttachmentThumbnailType.Video,
                            blurHash = A_BLUR_HASH,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a video message content, no thumbnail`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aMessageContent(
                    messageType = VideoMessageType(
                        filename = "filename",
                        caption = "caption",
                        formattedCaption = null,
                        source = aMediaSource(),
                        info = aVideoInfo(),
                    )
                )
            ).metadata(hideImage = true)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = null,
                            textContent = "body",
                            type = AttachmentThumbnailType.Video,
                            blurHash = A_BLUR_HASH,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a file message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aMessageContent(
                    messageType = FileMessageType(
                        filename = "filename",
                        caption = "caption",
                        formattedCaption = null,
                        source = aMediaSource(),
                        info = FileInfo(
                            mimetype = null,
                            size = null,
                            thumbnailInfo = null,
                            thumbnailSource = aMediaSource(),
                        ),
                    )
                )
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = aMediaSource(),
                            textContent = "body",
                            type = AttachmentThumbnailType.File,
                            blurHash = null,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a file message content, no thumbnail`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aMessageContent(
                    messageType = FileMessageType(
                        filename = "filename",
                        caption = "caption",
                        formattedCaption = null,
                        source = aMediaSource(),
                        info = FileInfo(
                            mimetype = null,
                            size = null,
                            thumbnailInfo = null,
                            thumbnailSource = aMediaSource(),
                        ),
                    )
                )
            ).metadata(hideImage = true)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = null,
                            textContent = "body",
                            type = AttachmentThumbnailType.File,
                            blurHash = null,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a audio message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aMessageContent(
                    messageType = AudioMessageType(
                        filename = "filename",
                        caption = "caption",
                        formattedCaption = null,
                        source = aMediaSource(),
                        info = AudioInfo(
                            duration = null,
                            size = null,
                            mimetype = null
                        ),
                    )
                )
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            textContent = "body",
                            type = AttachmentThumbnailType.Audio,
                            blurHash = null,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a location message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            withConfigurationAndContext {
                anInReplyToDetailsReady(
                    eventContent = aMessageContent(
                        messageType = LocationMessageType(
                            body = "body",
                            geoUri = "geo:3.0,4.0;u=5.0",
                            description = null,
                            assetType = null
                        )
                    )
                ).metadata(hideImage = false)
            }
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = null,
                            textContent = "Shared location",
                            type = AttachmentThumbnailType.Location,
                            blurHash = null,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a voice message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            withConfigurationAndContext {
                anInReplyToDetailsReady(
                    eventContent = aMessageContent(
                        messageType = VoiceMessageType(
                            filename = "filename",
                            caption = "caption",
                            formattedCaption = null,
                            source = aMediaSource(),
                            info = null,
                            details = null,
                        )
                    )
                ).metadata(hideImage = false)
            }
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = null,
                            textContent = "Voice message",
                            type = AttachmentThumbnailType.Voice,
                            blurHash = null,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `a poll content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aPollContent()
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(
                    InReplyToMetadata.Thumbnail(
                        attachmentThumbnailInfo = AttachmentThumbnailInfo(
                            thumbnailSource = null,
                            textContent = "Do you like polls?",
                            type = AttachmentThumbnailType.Poll,
                            blurHash = null,
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `redacted content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = RedactedContent
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(InReplyToMetadata.Redacted)
            }
        }
    }

    @Test
    fun `unable to decrypt content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = UnableToDecryptContent(
                    data = UnableToDecryptContent.Data.Unknown,
                    threadInfo = null,
                ),
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isEqualTo(InReplyToMetadata.UnableToDecrypt)
            }
        }
    }

    @Test
    fun `failed to parse message content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = FailedToParseMessageLikeContent("", "")
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isNull()
            }
        }
    }

    @Test
    fun `failed to parse state content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = FailedToParseStateContent("", "", "")
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isNull()
            }
        }
    }

    @Test
    fun `profile change content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = ProfileChangeContent("", "", "", "")
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isNull()
            }
        }
    }

    @Test
    fun `room membership content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = aRoomMembershipContent(userId = A_USER_ID)
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isNull()
            }
        }
    }

    @Test
    fun `state content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = StateContent("", OtherState.RoomJoinRules(null))
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isNull()
            }
        }
    }

    @Test
    fun `unknown content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = UnknownContent
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isNull()
            }
        }
    }

    @Test
    fun `null content`() = runTest {
        moleculeFlow(RecompositionMode.Immediate) {
            anInReplyToDetailsReady(
                eventContent = null
            ).metadata(hideImage = false)
        }.test {
            awaitItem().let {
                assertThat(it).isNull()
            }
        }
    }
}

private fun anInReplyToDetailsReady(
    eventId: EventId = AN_EVENT_ID,
    senderId: UserId = A_USER_ID,
    senderProfile: ProfileDetails = aProfileDetails(),
    eventContent: EventContent? = aMessageContent(),
    textContent: String? = "textContent",
) = InReplyToDetails.Ready(
    eventId = eventId,
    senderId = senderId,
    senderProfile = senderProfile,
    eventContent = eventContent,
    textContent = textContent,
)

fun aVideoInfo(): VideoInfo {
    return VideoInfo(
        duration = 1.minutes,
        height = 100,
        width = 100,
        mimetype = "video/mp4",
        size = 1000,
        thumbnailInfo = null,
        thumbnailSource = aMediaSource(),
        blurhash = A_BLUR_HASH,
    )
}

fun anImageInfo(): ImageInfo {
    return ImageInfo(
        height = 100,
        width = 100,
        mimetype = MimeTypes.Jpeg,
        size = 1000,
        thumbnailInfo = null,
        thumbnailSource = aMediaSource(),
        blurhash = A_BLUR_HASH,
    )
}
