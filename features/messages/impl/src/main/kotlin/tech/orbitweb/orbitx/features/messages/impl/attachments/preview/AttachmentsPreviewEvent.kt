/*
 * Copyright (c) 2026 Orbit Team / Orbit Commercial Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.preview

import tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.NormalizedCropRect

sealed interface AttachmentsPreviewEvent {
    data object SendAttachment : AttachmentsPreviewEvent
    data object CancelAndDismiss : AttachmentsPreviewEvent
    data object CancelAndClearSendState : AttachmentsPreviewEvent
    data object OpenImageEditor : AttachmentsPreviewEvent
    data object CloseImageEditor : AttachmentsPreviewEvent
    data object RotateImageToTheLeft : AttachmentsPreviewEvent
    data object FlipImageHorizontally : AttachmentsPreviewEvent
    data object FlipImageVertically : AttachmentsPreviewEvent
    data object ApplyImageEdits : AttachmentsPreviewEvent
    data object ResetImageEdits : AttachmentsPreviewEvent
    data class UpdateImageCropRect(val cropRect: NormalizedCropRect) : AttachmentsPreviewEvent
    data object ClearImageEditError : AttachmentsPreviewEvent
    data class SetCurrentCarouselIndex(val index: Int) : AttachmentsPreviewEvent
    data object ToggleViewOnce : AttachmentsPreviewEvent
    data class SelectImageFilter(val filter: tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.ImageFilter) : AttachmentsPreviewEvent
    data object ToggleVideoMute : AttachmentsPreviewEvent
    data class AddDrawStroke(val stroke: tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.DrawStroke) : AttachmentsPreviewEvent
    data object UndoLastDrawStroke : AttachmentsPreviewEvent
    data object ClearDrawStrokes : AttachmentsPreviewEvent
    data class AddSticker(val sticker: tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.StickerItem) : AttachmentsPreviewEvent
    data class UpdateSticker(val sticker: tech.orbitweb.orbitx.features.messages.impl.attachments.preview.imageeditor.StickerItem) : AttachmentsPreviewEvent
    data class RemoveSticker(val stickerId: String) : AttachmentsPreviewEvent
}
