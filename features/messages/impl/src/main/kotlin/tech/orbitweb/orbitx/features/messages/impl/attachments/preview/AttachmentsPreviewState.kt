/*
 * Copyright (c) 2026 Orbit Team / Orbit Commercial Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.messages.impl.attachments.Attachment
import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.AttachmentImageEditorState
import tech.orbitweb.orbitx.features.messages.impl.attachments.video.MediaOptimizationSelectorState
import tech.orbitweb.orbitx.libraries.mediaupload.api.MediaUploadInfo
import tech.orbitweb.orbitx.libraries.textcomposer.model.TextEditorState

data class AttachmentsPreviewState(
    val attachments: ImmutableList<Attachment>,
    val imageEditorState: AttachmentImageEditorState?,
    val canEditImage: Boolean,
    val isApplyingImageEdits: Boolean,
    val displayImageEditError: Boolean,
    val sendActionState: SendActionState,
    val textEditorState: TextEditorState,
    val mediaOptimizationSelectorState: MediaOptimizationSelectorState,
    val displayFileTooLargeError: Boolean,
    val currentIndex: Int,
    val isViewOnce: Boolean,
    val isVideoMuted: Boolean,
    val isTrimmingVideo: Boolean,
    val videoTrimStartMs: Long,
    val videoTrimEndMs: Long,
    val videoDurationMs: Long,
    val eventSink: (AttachmentsPreviewEvent) -> Unit,
) {
    val isGallery: Boolean get() = attachments.size > 1
    val totalCount: Int get() = attachments.size
}

@Immutable
sealed interface SendActionState {
    data object Idle : SendActionState

    @Immutable
    sealed interface Sending : SendActionState {
        data class Processing(val displayProgress: Boolean) : Sending
        data class ReadyToUpload(val mediaInfos: List<MediaUploadInfo>) : Sending
        data class Uploading(val mediaInfos: List<MediaUploadInfo>) : Sending
    }

    data class Failure(val error: Throwable, val mediaInfos: List<MediaUploadInfo>) : SendActionState
    data object Done : SendActionState

    fun mediaUploadInfoList(): List<MediaUploadInfo>? = when (this) {
        is Sending.ReadyToUpload -> mediaInfos
        is Sending.Uploading -> mediaInfos
        is Failure -> mediaInfos
        else -> null
    }
}
