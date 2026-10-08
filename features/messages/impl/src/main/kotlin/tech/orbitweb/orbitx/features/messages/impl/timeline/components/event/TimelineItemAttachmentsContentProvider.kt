/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemAttachmentsContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemAttachmentsContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.anAttachmentItem
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaSource

class TimelineItemAttachmentsContentProvider : PreviewParameterProvider<TimelineItemAttachmentsContent> {
    override val values: Sequence<TimelineItemAttachmentsContent>
        get() = sequenceOf(
            aTimelineItemAttachmentsContent(
                body = "Files",
                caption = null,
                attachments = listOf(
                    anAttachmentItem(
                        filename = "document.pdf",
                        mimeType = "application/pdf",
                        fileSize = null,
                        formattedFileSize = "2.5 MB",
                        fileExtension = "PDF",
                    ),
                    anAttachmentItem(
                        filename = "spreadsheet.xlsx",
                        mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        fileSize = null,
                        formattedFileSize = "450 KB",
                        fileExtension = "XLSX",
                    ),
                ),
            ),
            aTimelineItemAttachmentsContent(
                body = "Files",
                caption = "Files mixed with media",
                attachments = listOf(
                    anAttachmentItem(
                        filename = "report.pdf",
                        mimeType = "application/pdf",
                        fileSize = null,
                        formattedFileSize = "3.2 MB",
                        fileExtension = "PDF",
                    ),
                    anAttachmentItem(
                        filename = "notes.txt",
                        mimeType = "text/plain",
                        fileSize = null,
                        formattedFileSize = "12 KB",
                        fileExtension = "TXT",
                    ),
                    anAttachmentItem(
                        filename = "photo.jpg",
                        mimeType = "image/jpeg",
                        thumbnailSource = MediaSource(url = "thumb", json = ""),
                        fileSize = null,
                        formattedFileSize = "1.2 MB",
                        fileExtension = "JPG",
                    ),
                    anAttachmentItem(
                        filename = "video.mp4",
                        mimeType = "video/mp4",
                        thumbnailSource = MediaSource(url = "thumb", json = ""),
                        fileSize = null,
                        formattedFileSize = "15 MB",
                        fileExtension = "MP4",
                    ),
                ),
            ),
        )
}
