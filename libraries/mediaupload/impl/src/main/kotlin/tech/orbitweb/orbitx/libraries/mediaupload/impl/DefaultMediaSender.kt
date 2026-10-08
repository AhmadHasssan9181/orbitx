/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaupload.impl

import android.net.Uri
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import tech.orbitweb.orbitx.libraries.androidutils.hash.hash
import tech.orbitweb.orbitx.libraries.core.extensions.flatMap
import tech.orbitweb.orbitx.libraries.core.extensions.flatMapCatching
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaUploadHandler
import tech.orbitweb.orbitx.libraries.matrix.api.room.CreateTimelineParams
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaOptimizationConfig
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaOptimizationConfigProvider
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaPreProcessor
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaSender
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaSenderFactory
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaSenderRoomFactory
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo
import tech.orbitweb.orbitx.libraries.mediaupload.api.toGalleryItemInfo
import timber.log.Timber
import java.io.File
import java.util.concurrent.ConcurrentHashMap

@ContributesBinding(RoomScope::class)
class DefaultMediaSenderFactory(
    private val preProcessor: MediaPreProcessor,
    private val room: JoinedRoom,
    private val mediaOptimizationConfigProvider: MediaOptimizationConfigProvider,
) : MediaSenderFactory {
    override fun create(
        timelineMode: Timeline.Mode,
    ): MediaSender {
        return DefaultMediaSender(
            preProcessor = preProcessor,
            room = room,
            timelineMode = timelineMode,
            mediaOptimizationConfigProvider = mediaOptimizationConfigProvider,
        )
    }
}

@ContributesBinding(SessionScope::class)
class DefaultMediaSenderRoomFactory(
    private val preProcessor: MediaPreProcessor,
    private val mediaOptimizationConfigProvider: MediaOptimizationConfigProvider,
) : MediaSenderRoomFactory {
    override fun create(
        room: JoinedRoom,
    ): MediaSender {
        return DefaultMediaSender(
            preProcessor = preProcessor,
            room = room,
            timelineMode = Timeline.Mode.Live,
            mediaOptimizationConfigProvider = mediaOptimizationConfigProvider,
        )
    }
}

class DefaultMediaSender(
    private val preProcessor: MediaPreProcessor,
    private val room: JoinedRoom,
    private val timelineMode: Timeline.Mode,
    private val mediaOptimizationConfigProvider: MediaOptimizationConfigProvider,
) : MediaSender {
    private val ongoingUploadJobs = ConcurrentHashMap<Job.Key, MediaUploadHandler>()
    val hasOngoingMediaUploads get() = ongoingUploadJobs.isNotEmpty()

    override suspend fun preProcessMedia(
        uri: Uri,
        mimeType: String,
        mediaOptimizationConfig: MediaOptimizationConfig,
    ): Result<MediaUploadInfo> {
        Timber.d("Pre-processing media | uri: ${mediaId(uri)} | mimeType: $mimeType")
        return preProcessor
            .process(
                uri = uri,
                mimeType = mimeType,
                deleteOriginal = false,
                mediaOptimizationConfig = mediaOptimizationConfig,
            )
    }

    override suspend fun sendPreProcessedMedia(
        mediaUploadInfo: MediaUploadInfo,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
    ): Result<Unit> {
        val mediaLogId = mediaId(mediaUploadInfo.file)
        return getTimeline().flatMap {
            Timber.d("Started sending media $mediaLogId using timeline: ${it.mode}")
            it.sendMedia(
                uploadInfo = mediaUploadInfo,
                caption = caption,
                formattedCaption = formattedCaption,
                inReplyToEventId = inReplyToEventId,
            )
        }
            .handleSendResult(mediaLogId)
    }

    override suspend fun sendMedia(
        uri: Uri,
        mimeType: String,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
        mediaOptimizationConfig: MediaOptimizationConfig,
    ): Result<Unit> {
        return preProcessor
            .process(
                uri = uri,
                mimeType = mimeType,
                deleteOriginal = false,
                mediaOptimizationConfig = mediaOptimizationConfig,
            )
            .flatMapCatching { info ->
                getTimeline().getOrThrow().sendMedia(
                    uploadInfo = info,
                    caption = caption,
                    formattedCaption = formattedCaption,
                    inReplyToEventId = inReplyToEventId,
                )
            }
            .handleSendResult(mediaId(uri))
    }

    override suspend fun sendVoiceMessage(
        uri: Uri,
        mimeType: String,
        waveForm: List<Float>,
        inReplyToEventId: EventId?,
        isViewOnce: Boolean,
    ): Result<Unit> {
        return preProcessor
            .process(
                uri = uri,
                mimeType = mimeType,
                deleteOriginal = true,
                mediaOptimizationConfig = mediaOptimizationConfigProvider.get(),
            )
            .flatMapCatching { info ->
                val uploadFile = if (isViewOnce) {
                    val encrypted = java.io.File(info.file.parentFile, "orbit_vo_viewonce_${info.file.name}.ephemeral")
                    if (tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralCrypto.encryptFile(info.file, encrypted)) {
                        try {
                            info.file.delete()
                        } catch (_: Exception) {
                            }
                        encrypted
                    } else {
                        info.file
                    }
                } else {
                    info.file
                }
                val audioInfo = (info as MediaUploadInfo.Audio).audioInfo
                val newInfo = MediaUploadInfo.VoiceMessage(
                    file = uploadFile,
                    audioInfo = audioInfo,
                    waveform = waveForm,
                )
                getTimeline().getOrThrow().sendMedia(
                    uploadInfo = newInfo,
                    caption = if (isViewOnce) "🔒 View-Once • Voice message" else null,
                    formattedCaption = null,
                    inReplyToEventId = inReplyToEventId,
                )
            }
            .handleSendResult(mediaId(uri))
    }

    override suspend fun sendGallery(
        mediaUploadInfos: List<MediaUploadInfo>,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
    ): Result<Unit> {
        val galleryLogId = "gallery[${mediaUploadInfos.size} items]"
        Timber.d("Sending $galleryLogId")
        return getTimeline().flatMap { timeline ->
            val galleryItems = mediaUploadInfos.map { it.toGalleryItemInfo() }
            timeline.sendGallery(
                items = galleryItems,
                caption = caption,
                formattedCaption = formattedCaption,
                inReplyToEventId = inReplyToEventId,
            )
        }
            .flatMapCatching { uploadHandler ->
                uploadHandler.await()
            }
            .handleSendResult(galleryLogId)
    }

    private fun Result<Unit>.handleSendResult(mediaId: String) = this
        .onFailure { error ->
            val job = ongoingUploadJobs.remove(Job)
            Timber.e(error, "Sending media $mediaId failed. Removing ongoing upload job. Total: ${ongoingUploadJobs.size}")
            if (error !is CancellationException) {
                job?.cancel()
            }
        }
        .onSuccess {
            Timber.d("Sent media $mediaId successfully. Removing ongoing upload job. Total: ${ongoingUploadJobs.size}")
            ongoingUploadJobs.remove(Job)
        }

    private suspend fun Timeline.sendMedia(
        uploadInfo: MediaUploadInfo,
        caption: String?,
        formattedCaption: String?,
        inReplyToEventId: EventId?,
    ): Result<Unit> {
        val handler = when (uploadInfo) {
            is MediaUploadInfo.Image -> {
                sendImage(
                    file = uploadInfo.file,
                    thumbnailFile = uploadInfo.thumbnailFile,
                    imageInfo = uploadInfo.imageInfo,
                    caption = caption,
                    formattedCaption = formattedCaption,
                    inReplyToEventId = inReplyToEventId,
                )
            }
            is MediaUploadInfo.Video -> {
                sendVideo(
                    file = uploadInfo.file,
                    thumbnailFile = uploadInfo.thumbnailFile,
                    videoInfo = uploadInfo.videoInfo,
                    caption = caption,
                    formattedCaption = formattedCaption,
                    inReplyToEventId = inReplyToEventId,
                )
            }
            is MediaUploadInfo.Audio -> {
                sendAudio(
                    file = uploadInfo.file,
                    audioInfo = uploadInfo.audioInfo,
                    caption = caption,
                    formattedCaption = formattedCaption,
                    inReplyToEventId = inReplyToEventId,
                )
            }
            is MediaUploadInfo.VoiceMessage -> {
                sendVoiceMessage(
                    file = uploadInfo.file,
                    audioInfo = uploadInfo.audioInfo,
                    waveform = uploadInfo.waveform,
                    inReplyToEventId = inReplyToEventId,
                    caption = caption,
                    formattedCaption = formattedCaption,
                )
            }
            is MediaUploadInfo.AnyFile -> {
                sendFile(
                    file = uploadInfo.file,
                    fileInfo = uploadInfo.fileInfo,
                    caption = caption,
                    formattedCaption = formattedCaption,
                    inReplyToEventId = inReplyToEventId,
                )
            }
        }

        // We handle the cancellations here manually, so we suppress the warning
        @Suppress("RunCatchingNotAllowed")
        return handler
            .mapCatching { uploadHandler ->
                Timber.d("Added ongoing upload job, total: ${ongoingUploadJobs.size + 1}")
                ongoingUploadJobs[Job] = uploadHandler
                uploadHandler.await()
            }
    }

    private suspend fun getTimeline(): Result<Timeline> {
        return when (timelineMode) {
            is Timeline.Mode.Thread -> {
                room.createTimeline(CreateTimelineParams.Threaded(threadRootEventId = timelineMode.threadRootId))
            }
            else -> Result.success(room.liveTimeline)
        }
    }

    /**
     * Clean up any temporary files or resources used during the media processing.
     */
    override fun cleanUp() = preProcessor.cleanUp()
}

private fun mediaId(uri: Uri?): String = uri?.path.orEmpty().hash()
private fun mediaId(file: File): String = file.path.orEmpty().hash()
