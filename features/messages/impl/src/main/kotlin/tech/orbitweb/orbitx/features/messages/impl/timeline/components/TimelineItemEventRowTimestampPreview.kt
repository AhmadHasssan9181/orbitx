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
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemReactions
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemTextContent
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight

@PreviewsDayNight
@Composable
internal fun TimelineItemEventRowTimestampPreview(
    @PreviewParameter(TimelineItemEventForTimestampViewProvider::class) event: TimelineItem.Event
) = ElementPreview {
    Column {
        when (event.content) {
            is TimelineItemTextContent -> listOf(
                "Text",
                "Text longer, displayed on 1 line",
                "Text which should be rendered on several lines",
            ).forEach { str ->
                ATimelineItemEventRow(
                    event = event.copy(
                        content = event.content.copy(
                            body = str,
                            formattedBody = str,
                        ),
                        reactionsState = aTimelineItemReactions(count = 0),
                    ),
                )
            }
            else -> ATimelineItemEventRow(
                event = event.copy(
                    content = event.content,
                    reactionsState = aTimelineItemReactions(count = 0),
                ),
            )
        }
    }
}
