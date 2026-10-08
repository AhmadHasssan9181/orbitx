/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemStateContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemStateEventContent
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text

@Composable
fun TimelineItemStateView(
    content: TimelineItemStateContent,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        color = ElementTheme.colors.textSecondary,
        style = ElementTheme.typography.fontBodyMdRegular,
        text = content.body,
        textAlign = TextAlign.Center,
    )
}

@PreviewsDayNight
@Composable
internal fun TimelineItemStateViewPreview() = ElementPreview {
    TimelineItemStateView(
        content = aTimelineItemStateEventContent(),
    )
}
