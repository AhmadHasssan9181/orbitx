/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.viewer

import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeAudio
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeImage
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeVideo
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaInfo
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaViewerEntryPoint
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.datasource.MediaGalleryDataSource
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.GroupedMediaItems
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.MediaItem
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf

class SingleMediaGalleryDataSource(
    private val data: GroupedMediaItems,
) : MediaGalleryDataSource {
    override fun start(coroutineScope: CoroutineScope) = Unit
    override fun groupedMediaItemsFlow() = flowOf(AsyncData.Success(data))
    override fun getLastData(): AsyncData<GroupedMediaItems> = AsyncData.Success(data)

    override val isReady: Boolean = true

    override suspend fun loadMore(direction: Timeline.PaginationDirection) = Unit
    override suspend fun deleteItem(eventId: EventId) = Unit

    companion object {
        fun createFrom(params: MediaViewerEntryPoint.Params.Avatar) = SingleMediaGalleryDataSource(
            data = GroupedMediaItems(
                // Always use imageAndVideoItems, in Single mode, this is the data that will be used
                imageAndVideoItems = persistentListOf(
                    MediaItem.Image(
                        id = UniqueId("dummy"),
                        eventId = null,
                        mediaInfo = MediaInfo(
                            filename = params.avatarInfo.filename,
                            caption = null,
                            formattedCaption = null,
                            mimeType = MimeTypes.Images,
                            fileSize = null,
                            formattedFileSize = "",
                            fileExtension = "",
                            senderId = null,
                            senderName = null,
                            senderAvatar = null,
                            dateSent = null,
                            dateSentFull = null,
                            waveform = null,
                            duration = null,
                        ),
                        mediaSource = params.mediaSource,
                        thumbnailSource = params.thumbnailSource,
                    )
                ),
                fileItems = persistentListOf(),
            )
        )
    }
}

fun MediaViewerEntryPoint.Params.RoomMedia.toMediaItem() = when {
    mediaInfo.mimeType.isMimeTypeImage() -> {
        MediaItem.Image(
            id = UniqueId("dummy"),
            eventId = eventId,
            mediaInfo = mediaInfo,
            mediaSource = mediaSource,
            thumbnailSource = thumbnailSource,
        )
    }
    mediaInfo.mimeType.isMimeTypeVideo() -> {
        MediaItem.Video(
            id = UniqueId("dummy"),
            eventId = eventId,
            mediaInfo = mediaInfo,
            mediaSource = mediaSource,
            thumbnailSource = thumbnailSource,
        )
    }
    mediaInfo.mimeType.isMimeTypeAudio() -> {
        if (mediaInfo.waveform == null) {
            MediaItem.Audio(
                id = UniqueId("dummy"),
                eventId = eventId,
                mediaInfo = mediaInfo,
                mediaSource = mediaSource,
            )
        } else {
            MediaItem.Voice(
                id = UniqueId("dummy"),
                eventId = eventId,
                mediaInfo = mediaInfo,
                mediaSource = mediaSource,
            )
        }
    }
    else -> {
        MediaItem.File(
            id = UniqueId("dummy"),
            eventId = eventId,
            mediaInfo = mediaInfo,
            mediaSource = mediaSource,
        )
    }
}
