/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.event

import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource

data class TimelineItemStickerContent(
    override val filename: String,
    override val fileSize: Long?,
    override val caption: String?,
    override val formattedCaption: CharSequence?,
    override val isEdited: Boolean,
    override val mediaSource: MediaSource,
    val thumbnailSource: MediaSource?,
    override val formattedFileSize: String,
    override val fileExtension: String,
    override val mimeType: String,
    val blurhash: String?,
    val width: Int?,
    val height: Int?,
    val aspectRatio: Float?
) : TimelineItemEventContentWithAttachment {
    override val type: String = "TimelineItemStickerContent"

    /* Stickers are supposed to be small images so
       we allow using the mediaSource (unless the url is empty) */
    val preferredMediaSource = if (mediaSource.safeUrl.isEmpty()) thumbnailSource else mediaSource
}
