/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.datasource

import com.google.common.truth.Truth.assertThat
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.junit.Test
import tech.orbitweb.orbitx.libraries.androidutils.filesize.FakeFileSizeFormatter
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.dateformatter.test.FakeDateFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
import tech.orbitweb.orbitx.libraries.matrix.api.media.AudioDetails
import tech.orbitweb.orbitx.libraries.matrix.api.media.AudioInfo
import tech.orbitweb.orbitx.libraries.matrix.api.media.FileInfo
import tech.orbitweb.orbitx.libraries.matrix.api.media.ImageInfo
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.matrix.api.media.VideoInfo
import tech.orbitweb.orbitx.libraries.matrix.api.notification.CallIntent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.MatrixTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.AudioMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.CallNotifyContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EmoteMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseMessageLikeContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FailedToParseStateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.FileMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.GalleryItemType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.GalleryMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ImageMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LegacyCallInviteContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocationMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.NoticeMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.OtherMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.OtherState
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RedactedContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StateContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.StickerMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.TextMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnableToDecryptContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.UnknownContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VideoMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.VoiceMessageType
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_UNIQUE_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aMessageContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aPollContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aProfileChangeMessageContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aStickerContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.anEventTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.item.event.aRoomMembershipContent
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.MediaItem
import tech.orbitweb.orbitx.libraries.mediaviewer.test.util.FileExtensionExtractorWithoutValidation
import kotlin.time.Duration.Companion.seconds

@Suppress("LargeClass")
class EventItemFactoryTest {
    @Test
    fun `create check all null cases`() {
        val factory = createEventItemFactory()
        val contents = listOf(
            CallNotifyContent(callIntent = CallIntent.VIDEO, emptyList()),
            FailedToParseMessageLikeContent("", ""),
            FailedToParseStateContent("", "", ""),
            LegacyCallInviteContent,
            aPollContent(),
            aProfileChangeMessageContent(),
            RedactedContent,
            aRoomMembershipContent(
                userId = A_USER_ID,
            ),
            StateContent("", OtherState.RoomCreate),
            aStickerContent(
                info = ImageInfo(
                    width = null,
                    height = null,
                    mimetype = null,
                    size = null,
                    thumbnailInfo = null,
                    thumbnailSource = null,
                    blurhash = null,
                ),
                mediaSource = MediaSource("")
            ),
            UnableToDecryptContent(data = UnableToDecryptContent.Data.Unknown, threadInfo = null),
            UnknownContent,
        )
        contents.forEach {
            val result = factory.create(
                MatrixTimelineItem.Event(
                    uniqueId = A_UNIQUE_ID,
                    event = anEventTimelineItem(
                        content = it
                    )
                )
            )
            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `create MessageContent check all null cases`() {
        val factory = createEventItemFactory()
        val messageTypes = listOf(
            EmoteMessageType("", null),
            NoticeMessageType("", null),
            OtherMessageType("", ""),
            LocationMessageType("", "", null, null),
            TextMessageType("", null)
        )
        messageTypes.forEach {
            val result = factory.create(
                MatrixTimelineItem.Event(
                    uniqueId = A_UNIQUE_ID,
                    event = anEventTimelineItem(
                        content = aMessageContent(
                            messageType = it
                        )
                    )
                )
            )
            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `create for FileMessageType`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = FileMessageType(
                            filename = "filename.apk",
                            caption = "caption",
                            formattedCaption = null,
                            source = MediaSource(""),
                            info = FileInfo(
                                mimetype = MimeTypes.Apk,
                                size = 123L,
                                thumbnailInfo = null,
                                thumbnailSource = null,
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.File(
                id = A_UNIQUE_ID,
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Apk,
                    filename = "filename.apk",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "apk",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = null,
                ),
                mediaSource = MediaSource(""),
            )
        )
    }

    @Test
    fun `create for ImageMessageType`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = ImageMessageType(
                            filename = "filename.jpg",
                            caption = "caption",
                            formattedCaption = null,
                            source = MediaSource(""),
                            info = ImageInfo(
                                mimetype = MimeTypes.Jpeg,
                                size = 123L,
                                thumbnailInfo = null,
                                thumbnailSource = null,
                                height = 1L,
                                width = 2L,
                                blurhash = null,
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.Image(
                id = A_UNIQUE_ID,
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Jpeg,
                    filename = "filename.jpg",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "jpg",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = null,
                ),
                mediaSource = MediaSource(""),
                thumbnailSource = null,
            )
        )
    }

    @Test
    fun `create for AudioMessageType`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = AudioMessageType(
                            filename = "filename.mp3",
                            caption = "caption",
                            formattedCaption = null,
                            source = MediaSource(""),
                            info = AudioInfo(
                                mimetype = MimeTypes.Mp3,
                                size = 123L,
                                duration = 456.seconds,
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.Audio(
                id = A_UNIQUE_ID,
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Mp3,
                    filename = "filename.mp3",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "mp3",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = null,
                ),
                mediaSource = MediaSource(""),
            )
        )
    }

    @Test
    fun `create for VideoMessageType`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = VideoMessageType(
                            filename = "filename.mp4",
                            caption = "caption",
                            formattedCaption = null,
                            source = MediaSource(""),
                            info = VideoInfo(
                                mimetype = MimeTypes.Mp4,
                                size = 123L,
                                thumbnailInfo = null,
                                duration = 123.seconds,
                                height = 1L,
                                width = 2L,
                                thumbnailSource = null,
                                blurhash = null
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.Video(
                id = A_UNIQUE_ID,
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Mp4,
                    filename = "filename.mp4",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "mp4",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = "2:03",
                ),
                mediaSource = MediaSource(""),
                thumbnailSource = null,
            )
        )
    }

    @Test
    fun `create for VoiceMessageType`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = VoiceMessageType(
                            filename = "filename.ogg",
                            caption = "caption",
                            formattedCaption = null,
                            source = MediaSource(""),
                            info = AudioInfo(
                                mimetype = MimeTypes.Ogg,
                                size = 123L,
                                duration = 456.seconds,
                            ),
                            details = AudioDetails(
                                duration = 456.seconds,
                                waveform = persistentListOf(1f, 2f),
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.Voice(
                id = A_UNIQUE_ID,
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Ogg,
                    filename = "filename.ogg",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "ogg",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = listOf(1f, 2f).toImmutableList(),
                    duration = "7:36",
                ),
                mediaSource = MediaSource(""),
            )
        )
    }

    @Test
    fun `create for GalleryMessageType with image item`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = GalleryMessageType(
                            body = "Gallery body",
                            formatted = null,
                            items = listOf(
                                GalleryItemType.Image(
                                    content = ImageMessageType(
                                        filename = "image.jpg",
                                        caption = "caption",
                                        formattedCaption = null,
                                        source = MediaSource("image_url"),
                                        info = ImageInfo(
                                            mimetype = MimeTypes.Jpeg,
                                            size = 123L,
                                            thumbnailInfo = null,
                                            thumbnailSource = MediaSource("thumbnail_url"),
                                            height = 1L,
                                            width = 2L,
                                            blurhash = null,
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.Image(
                id = UniqueId("aUniqueId_0"),
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Jpeg,
                    filename = "image.jpg",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "jpg",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = null,
                ),
                mediaSource = MediaSource("image_url"),
                thumbnailSource = MediaSource("thumbnail_url"),
            )
        )
    }

    @Test
    fun `create for GalleryMessageType with video item`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = GalleryMessageType(
                            body = "Gallery body",
                            formatted = null,
                            items = listOf(
                                GalleryItemType.Video(
                                    content = VideoMessageType(
                                        filename = "video.mp4",
                                        caption = "caption",
                                        formattedCaption = null,
                                        source = MediaSource("video_url"),
                                        info = VideoInfo(
                                            mimetype = MimeTypes.Mp4,
                                            size = 123L,
                                            thumbnailInfo = null,
                                            duration = 123.seconds,
                                            height = 1L,
                                            width = 2L,
                                            thumbnailSource = MediaSource("thumbnail_url"),
                                            blurhash = null,
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.Video(
                id = UniqueId("aUniqueId_0"),
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Mp4,
                    filename = "video.mp4",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "mp4",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = "2:03",
                ),
                mediaSource = MediaSource("video_url"),
                thumbnailSource = MediaSource("thumbnail_url"),
            )
        )
    }

    @Test
    fun `create for GalleryMessageType with audio item`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = GalleryMessageType(
                            body = "Gallery body",
                            formatted = null,
                            items = listOf(
                                GalleryItemType.Audio(
                                    content = AudioMessageType(
                                        filename = "audio.mp3",
                                        caption = "caption",
                                        formattedCaption = null,
                                        source = MediaSource("audio_url"),
                                        info = AudioInfo(
                                            mimetype = MimeTypes.Mp3,
                                            size = 123L,
                                            duration = 456.seconds,
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.Audio(
                id = UniqueId("aUniqueId_0"),
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Mp3,
                    filename = "audio.mp3",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "mp3",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = null,
                ),
                mediaSource = MediaSource("audio_url"),
            )
        )
    }

    @Test
    fun `create for GalleryMessageType with file item`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = GalleryMessageType(
                            body = "Gallery body",
                            formatted = null,
                            items = listOf(
                                GalleryItemType.File(
                                    content = FileMessageType(
                                        filename = "document.pdf",
                                        caption = "caption",
                                        formattedCaption = null,
                                        source = MediaSource("file_url"),
                                        info = FileInfo(
                                            mimetype = MimeTypes.Pdf,
                                            size = 456L,
                                            thumbnailInfo = null,
                                            thumbnailSource = null,
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.File(
                id = UniqueId("aUniqueId_0"),
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Pdf,
                    filename = "document.pdf",
                    fileSize = 456L,
                    caption = "caption",
                    formattedFileSize = "456 Bytes",
                    fileExtension = "pdf",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = null,
                ),
                mediaSource = MediaSource("file_url"),
            )
        )
    }

    @Test
    fun `create for GalleryMessageType with Other item returns empty list`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = GalleryMessageType(
                            body = "Gallery body",
                            formatted = null,
                            items = listOf(
                                GalleryItemType.Other(itemType = "unknown_type", body = "Some body")
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).isEmpty()
    }

    @Test
    fun `create for GalleryMessageType with multiple items produces indexed IDs`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = GalleryMessageType(
                            body = "Gallery body",
                            formatted = null,
                            items = listOf(
                                GalleryItemType.Image(
                                    content = ImageMessageType(
                                        filename = "image.jpg",
                                        caption = null,
                                        formattedCaption = null,
                                        source = MediaSource("image_url"),
                                        info = ImageInfo(
                                            mimetype = MimeTypes.Jpeg,
                                            size = 123L,
                                            thumbnailInfo = null,
                                            thumbnailSource = null,
                                            height = 1L,
                                            width = 2L,
                                            blurhash = null,
                                        )
                                    )
                                ),
                                GalleryItemType.Video(
                                    content = VideoMessageType(
                                        filename = "video.mp4",
                                        caption = null,
                                        formattedCaption = null,
                                        source = MediaSource("video_url"),
                                        info = VideoInfo(
                                            mimetype = MimeTypes.Mp4,
                                            size = 456L,
                                            thumbnailInfo = null,
                                            duration = null,
                                            height = 1L,
                                            width = 2L,
                                            thumbnailSource = null,
                                            blurhash = null,
                                        )
                                    )
                                ),
                                GalleryItemType.Other(itemType = "unknown_type", body = "ignored"),
                            )
                        )
                    )
                )
            )
        )
        assertThat(result).hasSize(2)
        assertThat(result[0]).isInstanceOf(MediaItem.Image::class.java)
        assertThat((result[0] as MediaItem.Image).id).isEqualTo(UniqueId("aUniqueId_0"))
        assertThat(result[1]).isInstanceOf(MediaItem.Video::class.java)
        assertThat((result[1] as MediaItem.Video).id).isEqualTo(UniqueId("aUniqueId_1"))
    }

    @Test
    fun `create for StickerMessageType`() {
        val factory = createEventItemFactory()
        val result = factory.create(
            MatrixTimelineItem.Event(
                uniqueId = A_UNIQUE_ID,
                event = anEventTimelineItem(
                    content = aMessageContent(
                        messageType = StickerMessageType(
                            filename = "filename.gif",
                            caption = "caption",
                            formattedCaption = null,
                            source = MediaSource(""),
                            info = ImageInfo(
                                mimetype = MimeTypes.Gif,
                                size = 123L,
                                thumbnailInfo = null,
                                thumbnailSource = null,
                                height = 1L,
                                width = 2L,
                                blurhash = null,
                            ),
                        ),
                    )
                )
            )
        )
        assertThat(result).containsExactly(
            MediaItem.Image(
                id = A_UNIQUE_ID,
                eventId = AN_EVENT_ID,
                mediaInfo = MediaInfo(
                    mimeType = MimeTypes.Gif,
                    filename = "filename.gif",
                    fileSize = 123L,
                    caption = "caption",
                    formattedFileSize = "123 Bytes",
                    fileExtension = "gif",
                    senderId = A_USER_ID,
                    senderName = "alice",
                    senderAvatar = null,
                    dateSent = "0 Day false",
                    dateSentFull = "0 Full false",
                    waveform = null,
                    duration = null,
                ),
                mediaSource = MediaSource(""),
                thumbnailSource = null,
            )
        )
    }
}

private fun createEventItemFactory() = EventItemFactory(
    fileSizeFormatter = FakeFileSizeFormatter(),
    fileExtensionExtractor = FileExtensionExtractorWithoutValidation(),
    dateFormatter = FakeDateFormatter(),
)
