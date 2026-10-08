/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.datasource

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.libraries.androidutils.filesize.FileSizeFormatter
import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatter
import tech.orbitweb.orbitx.libraries.dateformatter.api.DateFormatterMode
import tech.orbitweb.orbitx.libraries.dateformatter.api.toHumanReadableDuration
import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
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
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LiveLocationContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocationMessageType
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageContent
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
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.getAvatarUrl
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.getDisambiguatedDisplayName
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.util.FileExtensionExtractor
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.MediaItem
import timber.log.Timber

@Inject
class EventItemFactory(
    private val fileSizeFormatter: FileSizeFormatter,
    private val fileExtensionExtractor: FileExtensionExtractor,
    private val dateFormatter: DateFormatter,
) {
    fun create(
        currentTimelineItem: MatrixTimelineItem.Event,
    ): List<MediaItem.Event> {
        val event = currentTimelineItem.event
        val dateSent = dateFormatter.format(
            currentTimelineItem.event.timestamp,
            mode = DateFormatterMode.Day,
        )
        val dateSentFull = dateFormatter.format(
            timestamp = currentTimelineItem.event.timestamp,
            mode = DateFormatterMode.Full,
        )
        return when (val content = event.content) {
            is CallNotifyContent,
            is FailedToParseMessageLikeContent,
            is FailedToParseStateContent,
            LegacyCallInviteContent,
            is PollContent,
            is ProfileChangeContent,
            RedactedContent,
            is RoomMembershipContent,
            is StateContent,
            is StickerContent,
            is UnableToDecryptContent,
            is LiveLocationContent,
            UnknownContent -> {
                Timber.w("Should not happen: ${content.javaClass.simpleName}")
                emptyList()
            }
            is MessageContent -> {
                when (val type = content.type) {
                    is EmoteMessageType,
                    is NoticeMessageType,
                    is OtherMessageType,
                    is LocationMessageType,
                    is TextMessageType -> {
                        Timber.w("Should not happen: ${content.type}")
                        emptyList()
                    }
                    is GalleryMessageType -> {
                        val baseId = currentTimelineItem.uniqueId.value
                        type.items.mapIndexedNotNull { index, galleryItem ->
                            val id = UniqueId("${baseId}_$index")
                            when (galleryItem) {
                                is GalleryItemType.Image -> {
                                    val c = galleryItem.content
                                    MediaItem.Image(
                                        id = id,
                                        eventId = currentTimelineItem.eventId,
                                        mediaInfo = createMediaInfo(
                                            filename = c.filename,
                                            fileSize = c.info?.size,
                                            caption = c.caption,
                                            mimeType = c.info?.mimetype.orEmpty(),
                                            fileExtension = c.filename,
                                            event = event,
                                            dateSent = dateSent,
                                            dateSentFull = dateSentFull,
                                        ),
                                        mediaSource = c.source,
                                        thumbnailSource = c.info?.thumbnailSource,
                                    )
                                }
                                is GalleryItemType.Video -> {
                                    val c = galleryItem.content
                                    MediaItem.Video(
                                        id = id,
                                        eventId = currentTimelineItem.eventId,
                                        mediaInfo = createMediaInfo(
                                            filename = c.filename,
                                            fileSize = c.info?.size,
                                            caption = c.caption,
                                            mimeType = c.info?.mimetype.orEmpty(),
                                            fileExtension = c.filename,
                                            event = event,
                                            dateSent = dateSent,
                                            dateSentFull = dateSentFull,
                                            duration = c.info?.duration?.inWholeMilliseconds?.toHumanReadableDuration(),
                                        ),
                                        mediaSource = c.source,
                                        thumbnailSource = c.info?.thumbnailSource,
                                    )
                                }
                                is GalleryItemType.Audio -> {
                                    val c = galleryItem.content
                                    MediaItem.Audio(
                                        id = id,
                                        eventId = currentTimelineItem.eventId,
                                        mediaInfo = createMediaInfo(
                                            filename = c.filename,
                                            fileSize = c.info?.size,
                                            caption = c.caption,
                                            mimeType = c.info?.mimetype.orEmpty(),
                                            fileExtension = c.filename,
                                            event = event,
                                            dateSent = dateSent,
                                            dateSentFull = dateSentFull,
                                        ),
                                        mediaSource = c.source,
                                    )
                                }
                                is GalleryItemType.File -> {
                                    val c = galleryItem.content
                                    MediaItem.File(
                                        id = id,
                                        eventId = currentTimelineItem.eventId,
                                        mediaInfo = createMediaInfo(
                                            filename = c.filename,
                                            fileSize = c.info?.size,
                                            caption = c.caption,
                                            mimeType = c.info?.mimetype.orEmpty(),
                                            fileExtension = c.filename,
                                            event = event,
                                            dateSent = dateSent,
                                            dateSentFull = dateSentFull,
                                        ),
                                        mediaSource = c.source,
                                    )
                                }
                                is GalleryItemType.Other -> null
                            }
                        }
                    }
                    is AudioMessageType -> listOf(MediaItem.Audio(
                        id = currentTimelineItem.uniqueId,
                        eventId = currentTimelineItem.eventId,
                        mediaInfo = MediaInfo(
                            filename = type.filename,
                            fileSize = type.info?.size,
                            caption = type.caption,
                            formattedCaption = type.formattedCaption?.body,
                            mimeType = type.info?.mimetype.orEmpty(),
                            formattedFileSize = type.info?.size?.let { fileSizeFormatter.format(it) }.orEmpty(),
                            fileExtension = fileExtensionExtractor.extractFromName(type.filename),
                            senderId = event.sender,
                            senderName = event.senderProfile.getDisambiguatedDisplayName(event.sender),
                            senderAvatar = event.senderProfile.getAvatarUrl(),
                            dateSent = dateSent,
                            dateSentFull = dateSentFull,
                            waveform = null,
                            duration = null,
                        ),
                        mediaSource = type.source,
                    ))
                    is FileMessageType -> listOf(MediaItem.File(
                        id = currentTimelineItem.uniqueId,
                        eventId = currentTimelineItem.eventId,
                        mediaInfo = MediaInfo(
                            filename = type.filename,
                            fileSize = type.info?.size,
                            caption = type.caption,
                            formattedCaption = type.formattedCaption?.body,
                            mimeType = type.info?.mimetype.orEmpty(),
                            formattedFileSize = type.info?.size?.let { fileSizeFormatter.format(it) }.orEmpty(),
                            fileExtension = fileExtensionExtractor.extractFromName(type.filename),
                            senderId = event.sender,
                            senderName = event.senderProfile.getDisambiguatedDisplayName(event.sender),
                            senderAvatar = event.senderProfile.getAvatarUrl(),
                            dateSent = dateSent,
                            dateSentFull = dateSentFull,
                            waveform = null,
                            duration = null,
                        ),
                        mediaSource = type.source,
                        // TODO We may want to add a thumbnailSource and set it to type.info?.thumbnailSource
                    ))
                    is ImageMessageType -> listOf(MediaItem.Image(
                        id = currentTimelineItem.uniqueId,
                        eventId = currentTimelineItem.eventId,
                        mediaInfo = MediaInfo(
                            filename = type.filename,
                            fileSize = type.info?.size,
                            caption = type.caption,
                            formattedCaption = type.formattedCaption?.body,
                            mimeType = type.info?.mimetype.orEmpty(),
                            formattedFileSize = type.info?.size?.let { fileSizeFormatter.format(it) }.orEmpty(),
                            fileExtension = fileExtensionExtractor.extractFromName(type.filename),
                            senderId = event.sender,
                            senderName = event.senderProfile.getDisambiguatedDisplayName(event.sender),
                            senderAvatar = event.senderProfile.getAvatarUrl(),
                            dateSent = dateSent,
                            dateSentFull = dateSentFull,
                            waveform = null,
                            duration = null,
                        ),
                        mediaSource = type.source,
                        thumbnailSource = type.info?.thumbnailSource,
                    ))
                    is StickerMessageType -> listOf(MediaItem.Image(
                        id = currentTimelineItem.uniqueId,
                        eventId = currentTimelineItem.eventId,
                        mediaInfo = MediaInfo(
                            filename = type.filename,
                            fileSize = type.info?.size,
                            caption = type.caption,
                            formattedCaption = type.formattedCaption?.body,
                            mimeType = type.info?.mimetype.orEmpty(),
                            formattedFileSize = type.info?.size?.let { fileSizeFormatter.format(it) }.orEmpty(),
                            fileExtension = fileExtensionExtractor.extractFromName(type.filename),
                            senderId = event.sender,
                            senderName = event.senderProfile.getDisambiguatedDisplayName(event.sender),
                            senderAvatar = event.senderProfile.getAvatarUrl(),
                            dateSent = dateSent,
                            dateSentFull = dateSentFull,
                            waveform = null,
                            duration = null,
                        ),
                        mediaSource = type.source,
                        thumbnailSource = type.info?.thumbnailSource,
                    ))
                    is VideoMessageType -> listOf(MediaItem.Video(
                        id = currentTimelineItem.uniqueId,
                        eventId = currentTimelineItem.eventId,
                        mediaInfo = MediaInfo(
                            filename = type.filename,
                            fileSize = type.info?.size,
                            caption = type.caption,
                            formattedCaption = type.formattedCaption?.body,
                            mimeType = type.info?.mimetype.orEmpty(),
                            formattedFileSize = type.info?.size?.let { fileSizeFormatter.format(it) }.orEmpty(),
                            fileExtension = fileExtensionExtractor.extractFromName(type.filename),
                            senderId = event.sender,
                            senderName = event.senderProfile.getDisambiguatedDisplayName(event.sender),
                            senderAvatar = event.senderProfile.getAvatarUrl(),
                            dateSent = dateSent,
                            dateSentFull = dateSentFull,
                            waveform = null,
                            duration = type.info?.duration?.inWholeMilliseconds?.toHumanReadableDuration(),
                        ),
                        mediaSource = type.source,
                        thumbnailSource = type.info?.thumbnailSource,
                    ))
                    is VoiceMessageType -> listOf(MediaItem.Voice(
                        id = currentTimelineItem.uniqueId,
                        eventId = currentTimelineItem.eventId,
                        mediaInfo = MediaInfo(
                            filename = type.filename,
                            fileSize = type.info?.size,
                            caption = type.caption,
                            formattedCaption = type.formattedCaption?.body,
                            mimeType = type.info?.mimetype.orEmpty(),
                            formattedFileSize = type.info?.size?.let { fileSizeFormatter.format(it) }.orEmpty(),
                            fileExtension = fileExtensionExtractor.extractFromName(type.filename),
                            senderId = event.sender,
                            senderName = event.senderProfile.getDisambiguatedDisplayName(event.sender),
                            senderAvatar = event.senderProfile.getAvatarUrl(),
                            dateSent = dateSent,
                            dateSentFull = dateSentFull,
                            waveform = type.details?.waveform.orEmpty(),
                            duration = type.info?.duration?.inWholeMilliseconds?.toHumanReadableDuration(),
                        ),
                        mediaSource = type.source,
                    ))
                }
            }
        }
    }

    private fun createMediaInfo(
        filename: String,
        fileSize: Long?,
        caption: String?,
        mimeType: String,
        fileExtension: String,
        event: tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventTimelineItem,
        dateSent: String,
        dateSentFull: String,
        waveform: List<Float>? = null,
        duration: String? = null,
    ) = MediaInfo(
        filename = filename,
        fileSize = fileSize,
        caption = caption,
        mimeType = mimeType,
        formattedFileSize = fileSize?.let { fileSizeFormatter.format(it) }.orEmpty(),
        fileExtension = fileExtensionExtractor.extractFromName(fileExtension),
        senderId = event.sender,
        senderName = event.senderProfile.getDisambiguatedDisplayName(event.sender),
        senderAvatar = event.senderProfile.getAvatarUrl(),
        dateSent = dateSent,
        dateSentFull = dateSentFull,
        waveform = waveform,
        duration = duration,
    )
}
