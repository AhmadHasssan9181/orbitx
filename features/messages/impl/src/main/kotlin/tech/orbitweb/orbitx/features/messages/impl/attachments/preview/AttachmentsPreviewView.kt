/*
 * Copyright (c) 2026 Orbit Team / Orbit Commercial Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.element.android.wysiwyg.display.TextDisplay
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.messages.impl.R
import tech.orbitweb.orbitx.features.messages.impl.attachments.Attachment
import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.error.sendAttachmentError
import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.AttachmentImageEditorView
import tech.orbitweb.orbitx.features.messages.impl.attachments.video.MediaOptimizationSelectorEvent
import tech.orbitweb.orbitx.features.messages.impl.attachments.video.MediaOptimizationSelectorState
import tech.orbitweb.orbitx.features.messages.impl.attachments.video.VideoUploadEstimation
import tech.orbitweb.orbitx.libraries.core.bool.orFalse
import tech.orbitweb.orbitx.libraries.core.mimetype.MimeTypes.isMimeTypeVideo
import tech.orbitweb.orbitx.libraries.designsystem.components.ProgressDialog
import tech.orbitweb.orbitx.libraries.designsystem.components.ProgressDialogType
import tech.orbitweb.orbitx.libraries.designsystem.components.button.BackButton
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.AlertDialog
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.ListDialog
import tech.orbitweb.orbitx.libraries.designsystem.components.dialogs.RetryDialog
import tech.orbitweb.orbitx.libraries.designsystem.components.list.ListItemContent
import tech.orbitweb.orbitx.libraries.designsystem.modifiers.niceClickable
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreviewDark
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconButton
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.ListItem
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Scaffold
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Surface
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Switch
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.TopAppBar
import tech.orbitweb.orbitx.libraries.designsystem.theme.floatingDateBadgeBackground
import tech.orbitweb.orbitx.libraries.mediaviewer.api.local.LocalMediaRenderer
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset
import tech.orbitweb.orbitx.libraries.textcomposer.TextComposer
import tech.orbitweb.orbitx.libraries.textcomposer.model.MessageComposerMode
import tech.orbitweb.orbitx.libraries.textcomposer.model.VoiceMessageState
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.libraries.ui.utils.formatter.rememberFileSizeFormatter
import kotlin.time.Duration.Companion.milliseconds

/**
 * Ref: https://www.figma.com/design/zftpgS6LjiczobJZ1GUNpt/Updates-to-Media---File-Upload?node-id=51-3514
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentsPreviewView(
    state: AttachmentsPreviewState,
    localMediaRenderer: LocalMediaRenderer,
    modifier: Modifier = Modifier,
) {
    val canShowEditAction = when (state.sendActionState) {
        is SendActionState.Sending.Uploading -> false
        is SendActionState.Sending.Processing -> !state.sendActionState.displayProgress
        SendActionState.Done -> false
        else -> true
    }

    fun postSendAttachment() {
        state.eventSink(AttachmentsPreviewEvent.SendAttachment)
    }

    fun postCancel() {
        state.eventSink(AttachmentsPreviewEvent.CancelAndDismiss)
    }

    fun postClearSendState() {
        state.eventSink(AttachmentsPreviewEvent.CancelAndClearSendState)
    }

    fun postOpenImageEditor() {
        state.eventSink(AttachmentsPreviewEvent.OpenImageEditor)
    }

    fun postCloseImageEditor() {
        state.eventSink(AttachmentsPreviewEvent.CloseImageEditor)
    }

    fun postResetImageEditor() {
        state.eventSink(AttachmentsPreviewEvent.ResetImageEdits)
    }

    fun postApplyImageEdits() {
        state.eventSink(AttachmentsPreviewEvent.ApplyImageEdits)
    }

    BackHandler(enabled = state.sendActionState !is SendActionState.Sending.Uploading && state.sendActionState !is SendActionState.Done) {
        if (state.imageEditorState != null) {
            postCloseImageEditor()
        } else {
            postCancel()
        }
    }

    if (state.imageEditorState != null) {
        AttachmentImageEditorView(
            state = state.imageEditorState,
            onCropRectChange = { cropRect ->
                state.eventSink(AttachmentsPreviewEvent.UpdateImageCropRect(cropRect))
            },
            onRotateClick = { state.eventSink(AttachmentsPreviewEvent.RotateImageToTheLeft) },
            onFlipHorizontallyClick = { state.eventSink(AttachmentsPreviewEvent.FlipImageHorizontally) },
            onFlipVerticallyClick = { state.eventSink(AttachmentsPreviewEvent.FlipImageVertically) },
            onCancelClick = ::postCloseImageEditor,
            onResetClick = ::postResetImageEditor,
            onDoneClick = ::postApplyImageEdits,
            onFilterChange = { filter ->
                state.eventSink(AttachmentsPreviewEvent.SelectImageFilter(filter))
            },
            onAddDrawStroke = { stroke ->
                state.eventSink(AttachmentsPreviewEvent.AddDrawStroke(stroke))
            },
            onUndoDrawStroke = {
                state.eventSink(AttachmentsPreviewEvent.UndoLastDrawStroke)
            },
            onClearDrawStrokes = {
                state.eventSink(AttachmentsPreviewEvent.ClearDrawStrokes)
            },
            onAddSticker = { sticker ->
                state.eventSink(AttachmentsPreviewEvent.AddSticker(sticker))
            },
            onUpdateSticker = { sticker ->
                state.eventSink(AttachmentsPreviewEvent.UpdateSticker(sticker))
            },
            onRemoveSticker = { stickerId ->
                state.eventSink(AttachmentsPreviewEvent.RemoveSticker(stickerId))
            },
            modifier = modifier,
        )
    } else {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        BackButton(
                            onClick = ::postCancel,
                        )
                    },
                    title = {
                        Text(
                            modifier = Modifier.semantics {
                                heading()
                            },
                            text = stringResource(R.string.screen_media_upload_preview_title),
                        )
                    },
                    actions = {
                        val currentAttachment = state.attachments.getOrNull(state.currentIndex) as? Attachment.Media
                        val isCurrentVideo = currentAttachment?.localMedia?.info?.mimeType?.startsWith("video/") == true
                        if (isCurrentVideo) {
                            IconButton(
                                onClick = { state.eventSink(AttachmentsPreviewEvent.ToggleVideoTrimMode) }
                            ) {
                                Icon(
                                    imageVector = CompoundIcons.Crop(),
                                    contentDescription = "Trim video",
                                    tint = if (state.isTrimmingVideo) ElementTheme.colors.iconPrimary else ElementTheme.colors.iconSecondary,
                                )
                            }
                            IconButton(
                                onClick = { state.eventSink(AttachmentsPreviewEvent.ToggleVideoMute) }
                            ) {
                                Icon(
                                    imageVector = if (state.isVideoMuted) CompoundIcons.VolumeOffSolid() else CompoundIcons.VolumeOnSolid(),
                                    contentDescription = if (state.isVideoMuted) "Unmute video" else "Mute video",
                                    tint = if (state.isVideoMuted) ElementTheme.colors.iconPrimary else ElementTheme.colors.iconSecondary,
                                )
                            }
                        }
                        if (state.canEditImage && canShowEditAction) {
                            IconButton(
                                onClick = ::postOpenImageEditor,
                            ) {
                                Icon(
                                    imageVector = CompoundIcons.Crop(),
                                    contentDescription = stringResource(CommonStrings.action_edit),
                                )
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            AttachmentPreviewContent(
                modifier = Modifier.padding(paddingValues),
                state = state,
                localMediaRenderer = localMediaRenderer,
                onSendClick = ::postSendAttachment,
            )
        }
    }
    AttachmentSendStateView(
        sendActionState = state.sendActionState,
        isApplyingImageEdits = state.isApplyingImageEdits,
        displayImageEditError = state.displayImageEditError,
        onDismissImageEditError = { state.eventSink(AttachmentsPreviewEvent.ClearImageEditError) },
        onDismissClick = ::postClearSendState,
        onRetryClick = ::postSendAttachment
    )
}

@Composable
private fun AttachmentSendStateView(
    sendActionState: SendActionState,
    isApplyingImageEdits: Boolean,
    displayImageEditError: Boolean,
    onDismissImageEditError: () -> Unit,
    onDismissClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    when {
        isApplyingImageEdits -> {
            ProgressDialog(
                type = ProgressDialogType.Indeterminate,
                text = stringResource(CommonStrings.common_preparing),
                showCancelButton = false,
                onDismissRequest = {},
            )
        }
        displayImageEditError -> {
            AlertDialog(
                title = stringResource(CommonStrings.common_error),
                content = stringResource(CommonStrings.common_something_went_wrong_message),
                onDismiss = onDismissImageEditError,
            )
        }
        else -> when (sendActionState) {
            is SendActionState.Sending.Processing -> {
                if (sendActionState.displayProgress) {
                    ProgressDialog(
                        type = ProgressDialogType.Indeterminate,
                        text = stringResource(CommonStrings.common_preparing),
                        showCancelButton = true,
                        onDismissRequest = onDismissClick,
                    )
                }
            }
            is SendActionState.Sending.Uploading -> {
                ProgressDialog(
                    type = ProgressDialogType.Indeterminate,
                    text = stringResource(id = CommonStrings.common_sending),
                    showCancelButton = true,
                    onDismissRequest = onDismissClick,
                )
            }
            is SendActionState.Failure -> {
                RetryDialog(
                    content = stringResource(sendAttachmentError(sendActionState.error)),
                    onDismiss = onDismissClick,
                    onRetry = onRetryClick
                )
            }
            else -> Unit
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun AttachmentPreviewContent(
    state: AttachmentsPreviewState,
    localMediaRenderer: LocalMediaRenderer,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding(),
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            if (state.isGallery) {
                val pagerState = rememberPagerState(
                    initialPage = state.currentIndex,
                    pageCount = { state.attachments.size },
                )
                var isPillVisible by remember { mutableStateOf(true) }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    beyondViewportPageCount = 1,
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    pageSpacing = 10.dp,
                ) { page ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        val attachment = state.attachments[page]
                        when (attachment) {
                            is Attachment.Media -> {
                                localMediaRenderer.Render(attachment.localMedia)
                            }
                        }
                    }
                }

                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.isScrollInProgress }
                        .collectLatest { isScrolling ->
                            if (isScrolling) {
                                isPillVisible = true
                            } else {
                                delay(2000.milliseconds)
                                isPillVisible = false
                            }
                        }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = isPillVisible,
                    enter = fadeIn(animationSpec = tween(150)),
                    exit = fadeOut(animationSpec = tween(300)),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp),
                ) {
                    GalleryCarouselPill(
                        currentIndex = pagerState.currentPage + 1,
                        totalCount = state.totalCount,
                    )
                }

                LaunchedEffect(pagerState.currentPage) {
                    state.eventSink(AttachmentsPreviewEvent.SetCurrentCarouselIndex(pagerState.currentPage))
                }
            } else {
                val firstAttachment = state.attachments.first()
                when (firstAttachment) {
                    is Attachment.Media -> {
                        localMediaRenderer.Render(firstAttachment.localMedia)
                    }
                }
            }
        }
        val mediaInfo = (state.attachments[state.currentIndex] as? Attachment.Media)?.localMedia?.info
        if (mediaInfo?.isImageAttachment() == true) {
            ImageOptimizationSelector(state.mediaOptimizationSelectorState)
        } else if (mediaInfo?.mimeType?.isMimeTypeVideo() == true) {
            val showTrimBar = state.isTrimmingVideo || isVideoTrimmed(state)
            if (showTrimBar) {
                VideoTrimBar(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                )
            }
            VideoPresetSelector(state = state.mediaOptimizationSelectorState)
        }

        ViewOnceSelector(
            isViewOnce = state.isViewOnce,
            onToggle = { state.eventSink(AttachmentsPreviewEvent.ToggleViewOnce) },
        )

        val sizeFormatter = rememberFileSizeFormatter()
        if (state.displayFileTooLargeError) {
            val maxFileUploadSize = state.mediaOptimizationSelectorState.maxUploadSize.dataOrNull()
            if (maxFileUploadSize != null) {
                val content = stringResource(CommonStrings.dialog_file_too_large_to_upload_subtitle, sizeFormatter.format(maxFileUploadSize, true))
                AlertDialog(
                    title = stringResource(CommonStrings.dialog_file_too_large_to_upload_title),
                    content = content,
                    onDismiss = { state.eventSink(AttachmentsPreviewEvent.CancelAndDismiss) },
                )
            }
        }

        AttachmentsPreviewBottomActions(
            state = state,
            onSendClick = onSendClick,
            modifier = Modifier
                .fillMaxWidth()
                .background(ElementTheme.colors.bgCanvasDefault)
                .height(IntrinsicSize.Min)
                .imePadding(),
        )
    }
}

@Composable
private fun ImageOptimizationSelector(state: MediaOptimizationSelectorState) {
    if (state.displayMediaSelectorViews == true) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .niceClickable {
                    state.isImageOptimizationEnabled?.let { value ->
                        state.eventSink(MediaOptimizationSelectorEvent.SelectImageOptimization(!value))
                    }
                }
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.screen_media_upload_preview_optimize_image_quality_title),
                style = ElementTheme.typography.fontBodyLgRegular,
            )
            Switch(
                modifier = Modifier.height(32.dp),
                checked = state.isImageOptimizationEnabled.orFalse(),
                onCheckedChange = { value -> state.eventSink(MediaOptimizationSelectorEvent.SelectImageOptimization(value)) },
            )
        }
    }
}

@Composable
private fun VideoPresetSelector(
    state: MediaOptimizationSelectorState,
) {
    val videoPresets = state.videoSizeEstimations.dataOrNull()
    var selectedPreset by remember(state.selectedVideoPreset) { mutableStateOf(state.selectedVideoPreset) }

    val displayDialog = state.displayVideoPresetSelectorDialog

    val sizeFormatter = rememberFileSizeFormatter()

    if (state.displayMediaSelectorViews == true && videoPresets != null && state.selectedVideoPreset != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .niceClickable { state.eventSink(MediaOptimizationSelectorEvent.OpenVideoPresetSelectorDialog) }
        ) {
            val estimation = videoPresets.find { it.preset == selectedPreset }
            val estimationMb = estimation?.sizeInBytes?.let { sizeFormatter.format(it, true) }
            val title = buildString {
                append(state.selectedVideoPreset.title())
                if (estimationMb != null) {
                    append(" ($estimationMb)")
                }
            }
            Text(text = title, style = ElementTheme.typography.fontBodyLgMedium)
            Text(
                text = stringResource(R.string.screen_media_upload_preview_change_video_quality_prompt),
                style = ElementTheme.typography.fontBodyLgMedium,
                color = ElementTheme.colors.textSecondary,
            )
        }
    }

    if (displayDialog) {
        VideoQualitySelectorDialog(
            selectedPreset = selectedPreset ?: VideoCompressionPreset.STANDARD,
            videoSizeEstimations = videoPresets ?: persistentListOf(),
            maxFileUploadSize = state.maxUploadSize.dataOrNull(),
            onSubmit = { preset ->
                selectedPreset = preset
                state.eventSink(MediaOptimizationSelectorEvent.SelectVideoPreset(preset))
            },
            onDismiss = { state.eventSink(MediaOptimizationSelectorEvent.DismissVideoPresetSelectorDialog) }
        )
    }
}

@Composable
private fun ViewOnceSelector(
    isViewOnce: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .niceClickable(onClick = onToggle)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(
                    if (isViewOnce) {
                        Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)))
                    } else {
                        SolidColor(Color(0x22FFFFFF))
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.material3.Text(
                text = "1",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "One-time media (View once)",
                style = ElementTheme.typography.fontBodyLgMedium,
                color = if (isViewOnce) Color(0xFFA78BFA) else ElementTheme.colors.textPrimary,
            )
            Text(
                text = "Media self-destructs after viewing",
                style = ElementTheme.typography.fontBodySmRegular,
                color = ElementTheme.colors.textSecondary,
            )
        }
        Switch(
            checked = isViewOnce,
            onCheckedChange = { onToggle() },
        )
    }
}

@Composable
private fun VideoQualitySelectorDialog(
    selectedPreset: VideoCompressionPreset,
    videoSizeEstimations: ImmutableList<VideoUploadEstimation>,
    maxFileUploadSize: Long?,
    onSubmit: (VideoCompressionPreset) -> Unit,
    onDismiss: () -> Unit,
) {
    val sizeFormatter = rememberFileSizeFormatter()

    var localSelectedPreset by remember(selectedPreset) { mutableStateOf(selectedPreset) }
    val subtitlePartNoFileSize = stringResource(CommonStrings.dialog_video_quality_selector_subtitle_no_file_size)
    val subtitlePartWithFileSize = stringResource(CommonStrings.dialog_video_quality_selector_subtitle_file_size)
    val subtitle = remember(maxFileUploadSize) {
        buildString {
            append(subtitlePartNoFileSize)
            if (maxFileUploadSize != null) {
                append(String.format(subtitlePartWithFileSize, sizeFormatter.format(maxFileUploadSize, true)))
            }
        }
    }
    ListDialog(
        title = stringResource(CommonStrings.dialog_video_quality_selector_title),
        subtitle = subtitle,
        onSubmit = { onSubmit(localSelectedPreset) },
        onDismissRequest = onDismiss,
        applyPaddingToContents = false,
    ) {
        for (videoEstimation in videoSizeEstimations) {
            val preset = videoEstimation.preset
            val isSelected = preset == localSelectedPreset
            item(
                key = preset,
                contentType = preset,
            ) {
                val estimationMb = sizeFormatter.format(videoEstimation.sizeInBytes, true)
                val title = "${preset.title()} ($estimationMb)"
                ListItem(
                    headlineContent = {
                        Text(
                            text = title,
                            style = ElementTheme.typography.fontBodyLgMedium,
                        )
                    },
                    supportingContent = {
                        Text(
                            text = preset.subtitle(),
                            style = ElementTheme.typography.fontBodyMdRegular,
                            color = ElementTheme.colors.textSecondary,
                        )
                    },
                    leadingContent = ListItemContent.RadioButton(
                        selected = isSelected,
                    ),
                    onClick = {
                        localSelectedPreset = preset
                    },
                    enabled = videoEstimation.canUpload,
                )
            }
        }
    }
}

@Composable
private fun AttachmentsPreviewBottomActions(
    state: AttachmentsPreviewState,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextComposer(
        modifier = modifier,
        state = state.textEditorState,
        voiceMessageState = VoiceMessageState.Idle,
        composerMode = MessageComposerMode.Attachment,
        onRequestFocus = {},
        onSendMessage = onSendClick,
        showTextFormatting = false,
        onResetComposerMode = {},
        onAddAttachment = {},
        onDismissTextFormatting = {},
        onVoiceRecorderEvent = {},
        onVoicePlayerEvent = {},
        onSendVoiceMessage = {},
        onDeleteVoiceMessage = {},
        onReceiveSuggestion = {},
        resolveMentionDisplay = { _, _ -> TextDisplay.Plain },
        resolveAtRoomMentionDisplay = { TextDisplay.Plain },
        onError = {},
        onTyping = {},
        onSelectRichContent = {},
    )
}

// Only preview in dark, dark theme is forced on the Node.
@Preview
@Composable
internal fun AttachmentsPreviewViewPreview(@PreviewParameter(AttachmentsPreviewStateProvider::class) state: AttachmentsPreviewState) = ElementPreviewDark {
    AttachmentsPreviewView(
        state = state,
        localMediaRenderer = SampleMediaRenderer(),
    )
}

@Preview
@Composable
internal fun AttachmentsPreviewGalleryViewPreview() = ElementPreviewDark {
    AttachmentsPreviewView(
        state = anAttachmentsPreviewGalleryState(),
        localMediaRenderer = SampleMediaRenderer(),
    )
}

@PreviewsDayNight
@Composable
internal fun VideoQualitySelectorDialogPreview() {
    ElementPreview {
        VideoQualitySelectorDialog(
            selectedPreset = VideoCompressionPreset.STANDARD,
            videoSizeEstimations = persistentListOf(
                VideoUploadEstimation(VideoCompressionPreset.HIGH, 2_000_000, canUpload = false),
                VideoUploadEstimation(VideoCompressionPreset.STANDARD, 1_000_000, canUpload = true),
                VideoUploadEstimation(VideoCompressionPreset.LOW, 500_000, canUpload = true)
            ),
            maxFileUploadSize = 1_500_000,
            onSubmit = {},
            onDismiss = {},
        )
    }
}

@Composable
fun VideoCompressionPreset.title(): String {
    return stringResource(
        when (this) {
            VideoCompressionPreset.STANDARD -> CommonStrings.common_video_quality_standard
            VideoCompressionPreset.HIGH -> CommonStrings.common_video_quality_high
            VideoCompressionPreset.LOW -> CommonStrings.common_video_quality_low
        }
    )
}

@Composable
fun VideoCompressionPreset.subtitle(): String {
    return stringResource(
        when (this) {
            VideoCompressionPreset.STANDARD -> CommonStrings.common_video_quality_standard_description
            VideoCompressionPreset.HIGH -> CommonStrings.common_video_quality_high_description
            VideoCompressionPreset.LOW -> CommonStrings.common_video_quality_low_description
        }
    )
}

@Composable
internal fun GalleryCarouselPill(
    currentIndex: Int,
    totalCount: Int,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = ElementTheme.colors.floatingDateBadgeBackground,
        shadowElevation = 4.dp,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.screen_media_upload_preview_item_count, currentIndex, totalCount),
            style = ElementTheme.typography.fontBodyMdMedium,
            color = ElementTheme.colors.textPrimary,
        )
    }
}

@Composable
private fun VideoTrimBar(
    state: AttachmentsPreviewState,
    modifier: Modifier = Modifier,
) {
    val durationMs = state.videoDurationMs.coerceAtLeast(1000L)
    val startMs = state.videoTrimStartMs.coerceIn(0L, durationMs)
    val endMs = state.videoTrimEndMs.takeIf { it > 0L }?.coerceIn(startMs, durationMs) ?: durationMs

    val selectedDurationSec = ((endMs - startMs) / 1000L).coerceAtLeast(1L)
    val totalDurationSec = (durationMs / 1000L).coerceAtLeast(1L)

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, ElementTheme.colors.borderInteractiveSecondary, RoundedCornerShape(16.dp)),
        color = ElementTheme.colors.bgSubtleSecondary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = CompoundIcons.Crop(),
                        contentDescription = null,
                        tint = ElementTheme.colors.iconPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Trim Video",
                        style = ElementTheme.typography.fontBodyMdMedium,
                        color = ElementTheme.colors.textPrimary,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF8B5CF6).copy(alpha = 0.2f),
                    ) {
                        Text(
                            text = "${selectedDurationSec}s / ${totalDurationSec}s",
                            style = ElementTheme.typography.fontBodyXsRegular,
                            color = Color(0xFFC4B5FD),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (startMs > 0L || endMs < durationMs) {
                        Text(
                            text = "Reset",
                            style = ElementTheme.typography.fontBodySmMedium,
                            color = ElementTheme.colors.textActionAccent,
                            modifier = Modifier
                                .niceClickable {
                                    state.eventSink(AttachmentsPreviewEvent.SetVideoTrimRange(0L, durationMs))
                                }
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = CompoundIcons.Close(),
                        contentDescription = "Close trim",
                        tint = ElementTheme.colors.iconSecondary,
                        modifier = Modifier
                            .size(18.dp)
                            .niceClickable {
                                state.eventSink(AttachmentsPreviewEvent.ToggleVideoTrimMode)
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatVideoTrimTimestamp(startMs),
                    style = ElementTheme.typography.fontBodySmRegular,
                    color = ElementTheme.colors.textSecondary,
                )
                Text(
                    text = formatVideoTrimTimestamp(endMs),
                    style = ElementTheme.typography.fontBodySmRegular,
                    color = ElementTheme.colors.textSecondary,
                )
            }

            androidx.compose.material3.RangeSlider(
                value = startMs.toFloat()..endMs.toFloat(),
                onValueChange = { range ->
                    state.eventSink(
                        AttachmentsPreviewEvent.SetVideoTrimRange(
                            startMs = range.start.toLong(),
                            endMs = range.endInclusive.toLong(),
                        )
                    )
                },
                valueRange = 0f..durationMs.toFloat(),
                colors = androidx.compose.material3.SliderDefaults.colors(
                    thumbColor = ElementTheme.colors.iconPrimary,
                    activeTrackColor = Color(0xFF8B5CF6),
                    inactiveTrackColor = ElementTheme.colors.borderInteractiveSecondary,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private fun isVideoTrimmed(state: AttachmentsPreviewState): Boolean {
    val duration = state.videoDurationMs
    return state.videoTrimStartMs > 0L || duration > 0L && state.videoTrimEndMs < duration
}

private fun formatVideoTrimTimestamp(ms: Long): String {
    val totalSeconds = (ms / 1000L).coerceAtLeast(0L)
    val minutes = totalSeconds / 60L
    val seconds = totalSeconds % 60L
    return String.format(java.util.Locale.ROOT, "%02d:%02d", minutes, seconds)
}
