/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.event

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.media3.common.MimeTypes
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource
import tech.orbitweb.orbitx.libraries.matrix.ui.components.A_BLUR_HASH

open class TimelineItemImageContentProvider : PreviewParameterProvider<TimelineItemImageContent> {
    override val values: Sequence<TimelineItemImageContent>
        get() = sequenceOf(
            aTimelineItemImageContent(),
            aTimelineItemImageContent(aspectRatio = 1.0f),
            aTimelineItemImageContent(aspectRatio = 1.5f),
            aTimelineItemImageContent(blurhash = null),
        )
}

fun aTimelineItemImageContent(
    aspectRatio: Float? = 0.5f,
    blurhash: String? = A_BLUR_HASH,
    filename: String = "A picture.jpg",
    caption: String? = null,
    width: Int? = null,
    height: Int? = 300,
) = TimelineItemImageContent(
    filename = filename,
    fileSize = 4 * 1024 * 1024L,
    caption = caption,
    formattedCaption = null,
    isEdited = false,
    mediaSource = MediaSource(""),
    thumbnailSource = null,
    mimeType = MimeTypes.IMAGE_JPEG,
    blurhash = blurhash,
    width = width,
    height = height,
    thumbnailWidth = null,
    thumbnailHeight = 150,
    aspectRatio = aspectRatio,
    formattedFileSize = "4MB",
    fileExtension = "jpg"
)
