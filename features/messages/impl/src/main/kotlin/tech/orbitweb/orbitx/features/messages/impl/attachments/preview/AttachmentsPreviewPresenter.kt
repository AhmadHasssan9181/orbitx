/*
 * Copyright (c) 2026 Orbit Team / Orbit Commercial Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.features.messages.impl.attachments.Attachment
import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.AttachmentImageEditor
import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.AttachmentImageEditorState
import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.AttachmentImageEdits
import tech.orbitweb.orbitx.features.messages.impl.attachments.video.MediaOptimizationSelectorPresenter
import tech.orbitweb.orbitx.features.messages.impl.attachments.video.MediaOptimizationSelectorState
import tech.orbitweb.orbitx.features.messages.impl.attachments.video.VideoCompressionPresetSelector
import tech.orbitweb.orbitx.libraries.androidutils.file.TemporaryUriDeleter
import tech.orbitweb.orbitx.libraries.androidutils.file.safeDelete
import tech.orbitweb.orbitx.libraries.androidutils.hash.hash
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.core.coroutine.CoroutineDispatchers
import tech.orbitweb.orbitx.libraries.core.coroutine.firstInstanceOf
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeVideo
import tech.orbitweb.orbitx.libraries.di.annotations.ApplicationContext
import tech.orbitweb.orbitx.libraries.di.annotations.SessionCoroutineScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkBuilder
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaOptimizationConfig
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaOptimizationConfigProvider
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaSenderFactory
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo
import tech.orbitweb.orbitx.libraries.mediaupload.api.allFiles
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset
import tech.orbitweb.orbitx.libraries.textcomposer.model.TextEditorState
import tech.orbitweb.orbitx.libraries.textcomposer.model.rememberMarkdownTextEditorState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

@AssistedInject
class AttachmentsPreviewPresenter(
    @Assisted private val attachments: ImmutableList<Attachment>,
    @Assisted private val onDoneListener: OnDoneListener,
    @Assisted private val timelineMode: Timeline.Mode,
    @Assisted private val inReplyToEventId: EventId?,
    mediaSenderFactory: MediaSenderFactory,
    private val permalinkBuilder: PermalinkBuilder,
    private val temporaryUriDeleter: TemporaryUriDeleter,
    private val attachmentImageEditor: AttachmentImageEditor,
    private val mediaOptimizationSelectorPresenterFactory: MediaOptimizationSelectorPresenter.Factory,
    private val videoCompressionPresetSelector: VideoCompressionPresetSelector,
    @SessionCoroutineScope private val sessionCoroutineScope: CoroutineScope,
    private val dispatchers: CoroutineDispatchers,
    private val mediaOptimizationConfigProvider: MediaOptimizationConfigProvider,
    @ApplicationContext private val context: Context? = null,
) : Presenter<AttachmentsPreviewState> {
    @AssistedFactory
    interface Factory {
        fun create(
            attachments: ImmutableList<Attachment>,
            timelineMode: Timeline.Mode,
            onDoneListener: OnDoneListener,
            inReplyToEventId: EventId?,
        ): AttachmentsPreviewPresenter
    }

    data class AttachmentAndEdits(
        val attachment: Attachment,
        val edits: AttachmentImageEdits,
    )

    private val mediaSender = mediaSenderFactory.create(timelineMode)

    @Composable
    override fun present(): AttachmentsPreviewState {
        val coroutineScope = rememberCoroutineScope()

        val sendActionState = remember {
            mutableStateOf<SendActionState>(SendActionState.Idle)
        }
        var canEditImage by remember { mutableStateOf(false) }
        var imageEditorState by remember { mutableStateOf<AttachmentImageEditorState?>(null) }
        var isApplyingImageEdits by remember { mutableStateOf(false) }
        var displayImageEditError by remember { mutableStateOf(false) }
        var editedTempFiles by remember { mutableStateOf<Map<Int, File>>(emptyMap()) }
        var isViewOnce by remember { mutableStateOf(false) }
        var isVideoMuted by remember { mutableStateOf(false) }
        var isTrimmingVideo by remember { mutableStateOf(false) }
        var videoTrimStartMs by remember { mutableStateOf(0L) }
        var videoTrimEndMs by remember { mutableStateOf(0L) }
        var videoDurationMs by remember { mutableStateOf(0L) }

        val markdownTextEditorState = rememberMarkdownTextEditorState(initialText = null, initialFocus = false)
        val textEditorState by rememberUpdatedState(
            TextEditorState.Markdown(markdownTextEditorState, isRoomEncrypted = null)
        )

        val ongoingSendAttachmentJob = remember { mutableStateOf<Job?>(null) }

        var currentIndex by remember { mutableIntStateOf(0) }

        var attachmentsAndEdits by remember {
            mutableStateOf(
                attachments.map {
                    AttachmentAndEdits(it, AttachmentImageEdits())
                }
            )
        }

        val editedAttachments by remember {
            derivedStateOf {
                attachmentsAndEdits.map { it.attachment }.toImmutableList()
            }
        }


        var preprocessMediaJob by remember { mutableStateOf<Job?>(null) }

        val mediaOptimizationSelectorPresenters = remember {
            attachments
                .filterIsInstance<Attachment.Media>()
                .mapIndexed { index, attachment ->
                    mediaOptimizationSelectorPresenterFactory.create(
                        index = index,
                        localMedia = attachment.localMedia,
                        sendAsFile = attachment.sendAsFile,
                    )
                }
        }
        val mediaOptimizationSelectorStates by rememberUpdatedState(
            mediaOptimizationSelectorPresenters.map {
                it.present()
            }.toImmutableList()
        )

        val observableSendState = snapshotFlow { sendActionState.value }

        var displayFileTooLargeError by remember { mutableStateOf(false) }

        LaunchedEffect(
            mediaOptimizationSelectorStates,
            imageEditorState,
            isApplyingImageEdits,
            editedAttachments,
        ) {
            if (mediaOptimizationSelectorStates.any { it.displayMediaSelectorViews == true } ||
                imageEditorState != null ||
                isApplyingImageEdits
            ) {
                // If any of the media optimization selectors are displayed, we don't want to pre-process the media yet
                return@LaunchedEffect
            }
            // If the media optimization selector is not displayed, we can pre-process the media
            // to prepare it for sending. This is done to avoid blocking the UI thread when the
            // user clicks on the send button.
            val configs = mediaOptimizationSelectorStates.mapIndexed { index, mediaOptimizationSelectorState ->
                getAutoPreprocessMediaOptimizationConfig(
                    mediaAttachment = editedAttachments[index] as Attachment.Media,
                    mediaOptimizationSelectorState = mediaOptimizationSelectorState,
                )
            }
            preprocessMediaJob?.cancel()
            preprocessMediaJob = coroutineScope.launch(dispatchers.io) {
                preProcessAttachments(
                    attachments = editedAttachments,
                    mediaOptimizationConfigs = configs,
                    displayProgress = false,
                    sendActionState = sendActionState,
                )
            }
        }

        LaunchedEffect(currentIndex) {
            val currentMedia = (attachments.getOrNull(currentIndex) as? Attachment.Media)?.localMedia
            if (currentMedia != null) {
                canEditImage = currentMedia.info.canEditImage() || attachmentImageEditor.canEdit(currentMedia)
            }
        }

        val maxUploadSize = mediaOptimizationSelectorStates.firstNotNullOfOrNull {
            it.maxUploadSize.dataOrNull()
        }
        LaunchedEffect(maxUploadSize) {
            if (maxUploadSize != null) {
                // If file size is not known, we're permissive and allow sending. The SDK will cancel the upload if needed.
                displayFileTooLargeError = attachments.any { attachment ->
                    when (attachment) {
                        is Attachment.Media -> {
                            val isImageFile = attachment.localMedia.info.isImageAttachment()
                            val isVideoFile = attachment.localMedia.info.mimeType.isMimeTypeVideo()
                            if (isImageFile || isVideoFile) {
                                false
                            } else {
                                val fileSize = attachment.localMedia.info.fileSize ?: 0L
                                maxUploadSize < fileSize
                            }
                        }
                    }
                }
            }
        }

        mediaOptimizationSelectorStates.forEach { mediaOptimizationSelectorState ->
            val videoSizeEstimations = mediaOptimizationSelectorState.videoSizeEstimations.dataOrNull()
            LaunchedEffect(videoSizeEstimations) {
                if (videoSizeEstimations != null) {
                    // Check if the video size estimations are too large for the max upload size
                    displayFileTooLargeError = videoSizeEstimations.none { it.canUpload }
                }
            }
        }

        fun handleEvent(event: AttachmentsPreviewEvent) {
            when (event) {
                is AttachmentsPreviewEvent.SendAttachment -> {
                    ongoingSendAttachmentJob.value = coroutineScope.launch {
                        if (preprocessMediaJob?.isActive != true && sendActionState.value !is SendActionState.Sending.ReadyToUpload) {
                            val configs = mediaOptimizationSelectorStates.map {
                                MediaOptimizationConfig(
                                    compressImages = it.isImageOptimizationEnabled
                                        ?: mediaOptimizationConfigProvider.get().compressImages,
                                    videoCompressionPreset = it.selectedVideoPreset
                                        ?: mediaOptimizationConfigProvider.get().videoCompressionPreset,
                                )
                            }
                            preprocessMediaJob = coroutineScope.launch(dispatchers.io) {
                                preProcessAttachments(
                                    attachments = editedAttachments,
                                    mediaOptimizationConfigs = configs,
                                    displayProgress = true,
                                    sendActionState = sendActionState,
                                )
                            }
                        }

                        // If the processing was hidden before, make it visible now
                        if (sendActionState.value is SendActionState.Sending.Processing) {
                            sendActionState.value = SendActionState.Sending.Processing(displayProgress = true)
                        }

                        // Wait until the media is ready to be uploaded
                        val allMediaUploadInfos = observableSendState.firstInstanceOf<SendActionState.Sending.ReadyToUpload>().mediaInfos

                        // Pre-processing is done, send the attachment
                        val rawCaption = markdownTextEditorState.getMessageMarkdown(permalinkBuilder)
                            .takeIf { it.isNotEmpty() }
                        val caption = if (isViewOnce) {
                            if (rawCaption != null) "🔒 View-Once • $rawCaption" else "🔒 View-Once"
                        } else {
                            rawCaption
                        }

                        val editedTempFilesToDelete = editedTempFiles
                        editedTempFiles = emptyMap()

                        val isVideoTrimmed = videoTrimStartMs > 0L || (videoDurationMs > 0L && videoTrimEndMs < videoDurationMs)
                        val processedUploadInfos = if (isVideoTrimmed || isVideoMuted) {
                            allMediaUploadInfos.map { info ->
                                if (info is tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo.Video) {
                                    val processedFile = java.io.File.createTempFile("orbit_proc_", ".mp4", info.file.parentFile)
                                    val success = trimAndProcessVideo(
                                        inputFile = info.file,
                                        outputFile = processedFile,
                                        startMs = if (isVideoTrimmed) videoTrimStartMs else 0L,
                                        endMs = if (isVideoTrimmed) videoTrimEndMs else 0L,
                                        muteAudio = isVideoMuted,
                                    )
                                    if (success) {
                                        info.copy(file = processedFile)
                                    } else {
                                        info
                                    }
                                } else {
                                    info
                                }
                            }
                        } else {
                            allMediaUploadInfos
                        }

                        val uploadInfos = if (isViewOnce) {
                            processedUploadInfos.map { info ->
                                val encryptedFile = java.io.File.createTempFile("orbit_view_once_", ".bin", info.file.parentFile)
                                if (tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralCrypto.encryptFile(info.file, encryptedFile)) {
                                    when (info) {
                                        is tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo.Image -> info.copy(file = encryptedFile, thumbnailFile = null)
                                        is tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo.Video -> info.copy(file = encryptedFile, thumbnailFile = null)
                                        is tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo.Audio -> info.copy(file = encryptedFile)
                                        is tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo.VoiceMessage -> info.copy(file = encryptedFile)
                                        is tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo.AnyFile -> info.copy(file = encryptedFile)
                                    }
                                } else {
                                    info
                                }
                            }
                        } else {
                            processedUploadInfos
                        }

                        // Send the media using the session coroutine scope so it doesn't matter if this screen or the chat one are closed
                        sessionCoroutineScope.launch(dispatchers.io) {
                            sendMedia(
                                mediaUploadInfos = uploadInfos,
                                caption = caption,
                                sendActionState = sendActionState,
                                inReplyToEventId = inReplyToEventId,
                            )

                            // Clean up the pre-processed media after it's been sent
                            mediaSender.cleanUp()
                            editedTempFilesToDelete.values.forEach { it.safeDelete() }
                        }
                    }
                }
                AttachmentsPreviewEvent.CancelAndDismiss -> {
                    displayFileTooLargeError = false
                    displayImageEditError = false
                    isApplyingImageEdits = false

                    // Cancel media preprocessing and sending
                    preprocessMediaJob?.cancel()
                    preprocessMediaJob = null
                    // If we couldn't send the pre-processed media, remove it
                    mediaSender.cleanUp()
                    ongoingSendAttachmentJob.value?.cancel()

                    // Dismiss the screen
                    dismiss(
                        attachments = editedAttachments,
                        sendActionState = sendActionState,
                        editedTempFiles = editedTempFiles,
                    )
                }
                AttachmentsPreviewEvent.CancelAndClearSendState -> {
                    // Cancel media sending
                    ongoingSendAttachmentJob.value?.let {
                        it.cancel()
                        ongoingSendAttachmentJob.value = null
                    }
                    preprocessMediaJob?.cancel()
                    preprocessMediaJob = null

                    val mediaUploadInfoList = sendActionState.value.mediaUploadInfoList()
                    sendActionState.value = if (mediaUploadInfoList != null) {
                        SendActionState.Sending.ReadyToUpload(mediaUploadInfoList)
                    } else {
                        SendActionState.Idle
                    }
                }
                AttachmentsPreviewEvent.OpenImageEditor -> {
                    val currentLocalMedia = (attachments.getOrNull(currentIndex) as? Attachment.Media)?.localMedia ?: return
                    val resolvedCanEditImage = canEditImage || currentLocalMedia.info.canEditImage()
                    if (resolvedCanEditImage) {
                        preprocessMediaJob?.cancel()
                        preprocessMediaJob = null
                        resetPreparedMedia(sendActionState)
                        imageEditorState = AttachmentImageEditorState(
                            localMedia = currentLocalMedia,
                            edits = attachmentsAndEdits.get(currentIndex).edits,
                            previewDebug = false,
                        )
                    }
                }
                AttachmentsPreviewEvent.CloseImageEditor -> {
                    imageEditorState = null
                }
                is AttachmentsPreviewEvent.UpdateImageCropRect -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.copy(cropRect = event.cropRect)
                    )
                }
                AttachmentsPreviewEvent.RotateImageToTheLeft -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.rotateAntiClockwise()
                    )
                }
                AttachmentsPreviewEvent.FlipImageHorizontally -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.flipHorizontally()
                    )
                }
                AttachmentsPreviewEvent.FlipImageVertically -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.flipVertically()
                    )
                }
                AttachmentsPreviewEvent.ResetImageEdits -> {
                    imageEditorState = imageEditorState?.copy(
                        edits = AttachmentImageEdits()
                    )
                }
                AttachmentsPreviewEvent.ApplyImageEdits -> {
                    val pendingState = imageEditorState ?: return
                    if (!pendingState.edits.hasChanges) {
                        editedTempFiles[currentIndex]?.safeDelete()
                        editedTempFiles = editedTempFiles - currentIndex
                        val originalAttachment = attachments[currentIndex]
                        attachmentsAndEdits = attachmentsAndEdits.toMutableList().also {
                            it[currentIndex] = AttachmentAndEdits(
                                originalAttachment,
                                pendingState.edits,
                            )
                        }.toImmutableList()
                        imageEditorState = null
                        resetPreparedMedia(sendActionState)
                        return
                    }
                    isApplyingImageEdits = true
                    displayImageEditError = false
                    coroutineScope.launch {
                        val result = withContext(dispatchers.io) {
                            attachmentImageEditor.exportEdits(
                                localMedia = pendingState.localMedia,
                                edits = pendingState.edits,
                            )
                        }
                        result.fold(
                            onSuccess = { editedMedia ->
                                editedTempFiles[currentIndex]?.safeDelete()
                                editedTempFiles = editedTempFiles + (currentIndex to editedMedia.file)
                                val currentAttachment = Attachment.Media(editedMedia.localMedia)
                                attachmentsAndEdits = attachmentsAndEdits.toMutableList().also {
                                    it[currentIndex] = AttachmentAndEdits(
                                        currentAttachment,
                                        pendingState.edits,
                                    )
                                }.toImmutableList()
                                imageEditorState = null
                                resetPreparedMedia(sendActionState)
                            },
                            onFailure = {
                                Timber.e(it, "Failed to apply image edits")
                                displayImageEditError = true
                            }
                        )
                        isApplyingImageEdits = false
                    }
                }
                AttachmentsPreviewEvent.ClearImageEditError -> {
                    displayImageEditError = false
                }
                is AttachmentsPreviewEvent.SetCurrentCarouselIndex -> {
                    currentIndex = event.index
                }
                AttachmentsPreviewEvent.ToggleViewOnce -> {
                    isViewOnce = !isViewOnce
                }
                is AttachmentsPreviewEvent.SelectImageFilter -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.copy(filter = event.filter)
                    )
                }
                AttachmentsPreviewEvent.ToggleVideoMute -> {
                    isVideoMuted = !isVideoMuted
                }
                AttachmentsPreviewEvent.ToggleVideoTrimMode -> {
                    if (!isTrimmingVideo) {
                        isTrimmingVideo = true
                        val currentAttachment = editedAttachments.getOrNull(currentIndex) as? Attachment.Media
                        if (currentAttachment != null && videoDurationMs == 0L) {
                            coroutineScope.launch(dispatchers.io) {
                                val duration = extractVideoDurationFromUri(context, currentAttachment.localMedia.uri)
                                if (duration > 0L) {
                                    videoDurationMs = duration
                                    videoTrimStartMs = 0L
                                    videoTrimEndMs = duration
                                }
                            }
                        }
                    } else {
                        isTrimmingVideo = false
                    }
                }
                is AttachmentsPreviewEvent.SetVideoTrimRange -> {
                    videoTrimStartMs = event.startMs.coerceAtLeast(0L)
                    videoTrimEndMs = event.endMs.coerceAtLeast(videoTrimStartMs)
                }
                is AttachmentsPreviewEvent.AddDrawStroke -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.copy(
                            drawStrokes = pendingState.edits.drawStrokes + event.stroke
                        )
                    )
                }
                AttachmentsPreviewEvent.UndoLastDrawStroke -> {
                    val pendingState = imageEditorState ?: return
                    if (pendingState.edits.drawStrokes.isNotEmpty()) {
                        imageEditorState = pendingState.copy(
                            edits = pendingState.edits.copy(
                                drawStrokes = pendingState.edits.drawStrokes.dropLast(1)
                            )
                        )
                    }
                }
                AttachmentsPreviewEvent.ClearDrawStrokes -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.copy(drawStrokes = emptyList())
                    )
                }
                is AttachmentsPreviewEvent.AddSticker -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.copy(
                            stickers = pendingState.edits.stickers + event.sticker
                        )
                    )
                }
                is AttachmentsPreviewEvent.UpdateSticker -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.copy(
                            stickers = pendingState.edits.stickers.map {
                                if (it.id == event.sticker.id) event.sticker else it
                            }
                        )
                    )
                }
                is AttachmentsPreviewEvent.RemoveSticker -> {
                    val pendingState = imageEditorState ?: return
                    imageEditorState = pendingState.copy(
                        edits = pendingState.edits.copy(
                            stickers = pendingState.edits.stickers.filterNot { it.id == event.stickerId }
                        )
                    )
                }
            }
        }

        return AttachmentsPreviewState(
            attachments = editedAttachments,
            imageEditorState = imageEditorState,
            canEditImage = canEditImage,
            isApplyingImageEdits = isApplyingImageEdits,
            displayImageEditError = displayImageEditError,
            sendActionState = sendActionState.value,
            textEditorState = textEditorState,
            mediaOptimizationSelectorState = mediaOptimizationSelectorStates[currentIndex],
            displayFileTooLargeError = displayFileTooLargeError,
            currentIndex = currentIndex,
            isViewOnce = isViewOnce,
            isVideoMuted = isVideoMuted,
            isTrimmingVideo = isTrimmingVideo,
            videoTrimStartMs = videoTrimStartMs,
            videoTrimEndMs = videoTrimEndMs,
            videoDurationMs = videoDurationMs,
            eventSink = ::handleEvent,
        )
    }

    private suspend fun getAutoPreprocessMediaOptimizationConfig(
        mediaAttachment: Attachment.Media,
        mediaOptimizationSelectorState: MediaOptimizationSelectorState,
    ): MediaOptimizationConfig {
        return if (mediaAttachment.sendAsFile) {
            // If we're sending the media as a file, we can skip image compression and we should select the highest video compression preset that still fits
            // the upload limit (if the estimations are available)
            val videoCompressionPreset = videoCompressionPresetSelector.selectBestVideoPreset(
                expectedVideoPreset = VideoCompressionPreset.HIGH,
                videoSizeEstimations = mediaOptimizationSelectorState.videoSizeEstimations,
            ).dataOrNull() ?: VideoCompressionPreset.HIGH

            MediaOptimizationConfig(
                compressImages = false,
                videoCompressionPreset = videoCompressionPreset,
            )
        } else {
            MediaOptimizationConfig(
                compressImages = mediaOptimizationSelectorState.isImageOptimizationEnabled
                    ?: mediaOptimizationConfigProvider.get().compressImages,
                videoCompressionPreset = mediaOptimizationSelectorState.selectedVideoPreset
                    ?: mediaOptimizationConfigProvider.get().videoCompressionPreset,
            )
        }
    }

    private suspend fun preProcessAttachments(
        attachments: List<Attachment>,
        mediaOptimizationConfigs: List<MediaOptimizationConfig>,
        displayProgress: Boolean,
        sendActionState: MutableState<SendActionState>,
    ) {
        sendActionState.value = SendActionState.Sending.Processing(displayProgress = displayProgress)
        val mediaUploadInfos = mutableListOf<MediaUploadInfo>()
        attachments.forEachIndexed { index, attachment ->
            when (attachment) {
                is Attachment.Media -> {
                    mediaSender.preProcessMedia(
                        uri = attachment.localMedia.uri,
                        mimeType = attachment.localMedia.info.mimeType,
                        mediaOptimizationConfig = mediaOptimizationConfigs[index],
                    ).fold(
                        onSuccess = { mediaUploadInfo ->
                            Timber.d("Media ${mediaUploadInfo.file.path.orEmpty().hash()} finished processing")
                            mediaUploadInfos.add(mediaUploadInfo)
                        },
                        onFailure = {
                            Timber.e(it, "Failed to pre-process media")
                            if (it is CancellationException) {
                                throw it
                            } else {
                                sendActionState.value = SendActionState.Failure(it, emptyList())
                                return
                            }
                        }
                    )
                }
            }
        }
        sendActionState.value = SendActionState.Sending.ReadyToUpload(mediaUploadInfos)
    }

    private fun dismiss(
        attachments: List<Attachment>,
        sendActionState: MutableState<SendActionState>,
        editedTempFiles: Map<Int, File> = emptyMap(),
    ) {
        for (attachment in attachments) {
            when (attachment) {
                is Attachment.Media -> {
                    temporaryUriDeleter.delete(attachment.localMedia.uri)
                }
            }
        }
        val uploadInfos = (sendActionState.value as? SendActionState.Sending.ReadyToUpload)?.mediaInfos
        uploadInfos?.forEach { cleanUp(it) }
        editedTempFiles.values.forEach { it.safeDelete() }
        sendActionState.value = SendActionState.Done
        onDoneListener()
    }

    private fun cleanUp(
        mediaUploadInfo: MediaUploadInfo,
    ) {
        mediaUploadInfo.allFiles().forEach { file ->
            file.safeDelete()
        }
    }

    private fun resetPreparedMedia(sendActionState: MutableState<SendActionState>) {
        sendActionState.value.mediaUploadInfoList()?.forEach(::cleanUp)
        mediaSender.cleanUp()
        sendActionState.value = SendActionState.Idle
    }

    private suspend fun sendMedia(
        mediaUploadInfos: List<MediaUploadInfo>,
        caption: String?,
        sendActionState: MutableState<SendActionState>,
        inReplyToEventId: EventId?,
    ) = runCatchingExceptions {
        if (mediaUploadInfos.size == 1) {
            sendActionState.value = SendActionState.Sending.Uploading(mediaUploadInfos)
            mediaSender.sendPreProcessedMedia(
                mediaUploadInfo = mediaUploadInfos.first(),
                caption = caption,
                formattedCaption = null,
                inReplyToEventId = inReplyToEventId,
            ).getOrThrow()
        } else {
            mediaSender.sendGallery(
                mediaUploadInfos = mediaUploadInfos,
                caption = caption,
                formattedCaption = null,
                inReplyToEventId = inReplyToEventId,
            ).getOrThrow()
        }
    }.fold(
        onSuccess = {
            mediaUploadInfos.forEach { cleanUp(it) }
            sendActionState.value = SendActionState.Done
            onDoneListener()
        },
        onFailure = { error ->
            Timber.e(error, "Failed to send attachment")
            if (error is CancellationException) {
                throw error
            } else {
                sendActionState.value = SendActionState.Failure(error, mediaUploadInfos)
            }
        }
    )
}

private fun extractVideoDurationFromUri(context: Context?, uri: Uri): Long {
    val retriever = MediaMetadataRetriever()
    return try {
        if (context != null) {
            retriever.setDataSource(context, uri)
        } else {
            val path = uri.path ?: uri.toString().removePrefix("file://")
            retriever.setDataSource(path)
        }
        val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        durationStr?.toLongOrNull() ?: 0L
    } catch (_: Exception) {
        0L
    } finally {
        try { retriever.release() } catch (_: Exception) {}
    }
}

private fun trimAndProcessVideo(
    inputFile: java.io.File,
    outputFile: java.io.File,
    startMs: Long,
    endMs: Long,
    muteAudio: Boolean,
): Boolean {
    var extractor: android.media.MediaExtractor? = null
    var muxer: android.media.MediaMuxer? = null
    return try {
        extractor = android.media.MediaExtractor().apply { setDataSource(inputFile.path) }
        val startUs = (startMs * 1000L).coerceAtLeast(0L)
        val endUs = if (endMs > 0L) (endMs * 1000L) else Long.MAX_VALUE

        muxer = android.media.MediaMuxer(outputFile.path, android.media.MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        val trackMap = mutableMapOf<Int, Int>()
        var videoTrackIndex = -1
        var maxBufferSize = 1024 * 1024

        for (i in 0 until extractor.trackCount) {
            val format = extractor.getTrackFormat(i)
            val mime = format.getString(android.media.MediaFormat.KEY_MIME) ?: ""
            if (mime.startsWith("video/")) {
                videoTrackIndex = i
                val muxerTrack = muxer.addTrack(format)
                trackMap[i] = muxerTrack
                if (format.containsKey(android.media.MediaFormat.KEY_MAX_INPUT_SIZE)) {
                    maxBufferSize = maxOf(maxBufferSize, format.getInteger(android.media.MediaFormat.KEY_MAX_INPUT_SIZE))
                }
            } else if (mime.startsWith("audio/") && !muteAudio) {
                val muxerTrack = muxer.addTrack(format)
                trackMap[i] = muxerTrack
                if (format.containsKey(android.media.MediaFormat.KEY_MAX_INPUT_SIZE)) {
                    maxBufferSize = maxOf(maxBufferSize, format.getInteger(android.media.MediaFormat.KEY_MAX_INPUT_SIZE))
                }
            }
        }

        if (videoTrackIndex < 0 || trackMap.isEmpty()) {
            return false
        }

        trackMap.keys.forEach { extractor.selectTrack(it) }

        if (startUs > 0L) {
            extractor.seekTo(startUs, android.media.MediaExtractor.SEEK_TO_PREVIOUS_SYNC)
        }

        muxer.start()

        val buffer = java.nio.ByteBuffer.allocateDirect(maxBufferSize)
        val bufferInfo = android.media.MediaCodec.BufferInfo()
        val firstPtsMap = mutableMapOf<Int, Long>()
        var reachedEnd = false

        while (!reachedEnd) {
            val trackIndex = extractor.sampleTrackIndex
            if (trackIndex < 0) break
            val muxerTrack = trackMap[trackIndex]
            val sampleTime = extractor.sampleTime

            if (sampleTime > endUs && trackIndex == videoTrackIndex) {
                reachedEnd = true
                break
            }

            if (muxerTrack != null && sampleTime <= endUs) {
                val sampleSize = extractor.readSampleData(buffer, 0)
                if (sampleSize > 0) {
                    val firstPts = firstPtsMap.getOrPut(trackIndex) { sampleTime }
                    bufferInfo.offset = 0
                    bufferInfo.size = sampleSize
                    bufferInfo.presentationTimeUs = (sampleTime - firstPts).coerceAtLeast(0L)
                    bufferInfo.flags = extractor.sampleFlags
                    muxer.writeSampleData(muxerTrack, buffer, bufferInfo)
                }
            }
            extractor.advance()
        }

        muxer.stop()
        true
    } catch (_: Exception) {
        false
    } finally {
        try { muxer?.release() } catch (_: Exception) {}
        try { extractor?.release() } catch (_: Exception) {}
    }
}

private fun stripAudioFromVideo(inputFile: java.io.File, outputFile: java.io.File): Boolean {
    return trimAndProcessVideo(inputFile, outputFile, 0L, 0L, muteAudio = true)
}

