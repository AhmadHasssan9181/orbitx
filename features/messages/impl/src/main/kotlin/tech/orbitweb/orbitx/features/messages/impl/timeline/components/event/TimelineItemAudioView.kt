/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayoutData
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemAudioContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemAudioContentProvider
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight

@Composable
fun TimelineItemAudioView(
    content: TimelineItemAudioContent,
    onContentLayoutChange: (ContentAvoidingLayoutData) -> Unit,
    modifier: Modifier = Modifier,
    onContentClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    eventId: tech.orbitweb.orbitx.libraries.matrix.api.core.EventId? = null,
) {
    val isViewOnce = content.caption?.contains("View-Once", ignoreCase = true) == true ||
        content.filename.contains("orbit_view_once_", ignoreCase = true) ||
        content.filename.contains("orbit_vo_", ignoreCase = true) ||
        content.filename.contains("viewonce", ignoreCase = true) ||
        content.filename.endsWith(".ephemeral", ignoreCase = true)

    if (isViewOnce) {
        val isDestroyed = tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.isDestroyed(eventId?.value)
        val context = androidx.compose.ui.platform.LocalContext.current
        ViewOnceMediaBubble(
            mediaType = "Audio",
            onContentClick = if (isDestroyed) {
                { android.widget.Toast.makeText(context, "This audio has already been played and destroyed", android.widget.Toast.LENGTH_SHORT).show() }
            } else {
                onContentClick
            },
            onLongClick = onLongClick,
            onContentLayoutChange = onContentLayoutChange,
            isDestroyed = isDestroyed,
            modifier = modifier,
        )
        return
    }

    TimelineItemAttachmentView(
        icon = CompoundIcons.Audio(),
        iconContentDescription = null,
        filename = content.filename,
        fileExtensionAndSize = content.fileExtensionAndSize,
        caption = content.caption,
        onContentLayoutChange = onContentLayoutChange,
        modifier = modifier,
    )
}

@PreviewsDayNight
@Composable
internal fun TimelineItemAudioViewPreview(@PreviewParameter(TimelineItemAudioContentProvider::class) content: TimelineItemAudioContent) =
    ElementTimelineItemPreview {
        TimelineItemAudioView(
            content,
            onContentLayoutChange = {},
        )
    }
