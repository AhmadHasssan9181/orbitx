/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemReactions
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemGroupPosition
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemThreadInfo
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemImageContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemTextContent
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply.InReplyToDetails
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply.InReplyToDetailsProvider

@PreviewsDayNight
@Composable
internal fun TimelineItemEventRowWithReplyPreview(
    @PreviewParameter(InReplyToDetailsProvider::class) inReplyToDetails: InReplyToDetails,
) = ElementPreview {
    TimelineItemEventRowWithReplyContentToPreview(inReplyToDetails)
}

@Composable
internal fun TimelineItemEventRowWithReplyContentToPreview(
    inReplyToDetails: InReplyToDetails,
    displayNameAmbiguous: Boolean = false,
) {
    Column {
        sequenceOf(false, true).forEach {
            ATimelineItemEventRow(
                event = aTimelineItemEvent(
                    isMine = it,
                    timelineItemReactions = aTimelineItemReactions(count = 0),
                    content = aTimelineItemTextContent(body = "A reply."),
                    inReplyTo = inReplyToDetails,
                    displayNameAmbiguous = displayNameAmbiguous,
                    groupPosition = TimelineItemGroupPosition.First,
                ),
            )
            ATimelineItemEventRow(
                event = aTimelineItemEvent(
                    isMine = it,
                    timelineItemReactions = aTimelineItemReactions(count = 0),
                    content = aTimelineItemImageContent(
                        aspectRatio = 2.5f,
                        filename = "image.jpg",
                        caption = "A reply with an image.",
                    ),
                    inReplyTo = inReplyToDetails,
                    displayNameAmbiguous = displayNameAmbiguous,
                    threadInfo = TimelineItemThreadInfo.ThreadResponse(threadRootId = ThreadId("\$thread-root-id")),
                    groupPosition = TimelineItemGroupPosition.Last,
                ),
            )
        }
    }
}
