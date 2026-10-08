/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import android.text.SpannedString
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.ATimelineItemEventRow
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayout
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayoutData
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemGroupPosition
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVideoContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVideoContentProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemVideoContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.ProtectedView
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.coerceRatioWhenHidingContent
import tech.orbitweb.orbitx.libraries.designsystem.components.blurhash.blurHashBackground
import tech.orbitweb.orbitx.libraries.designsystem.modifiers.onKeyboardContextMenuAction
import tech.orbitweb.orbitx.libraries.designsystem.modifiers.roundedBackground
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.matrix.ui.media.MAX_THUMBNAIL_HEIGHT
import tech.orbitweb.orbitx.libraries.matrix.ui.media.MAX_THUMBNAIL_WIDTH
import tech.orbitweb.orbitx.libraries.matrix.ui.media.MediaRequestData
import tech.orbitweb.orbitx.libraries.textcomposer.ElementRichTextEditorStyle
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.libraries.ui.utils.a11y.isTalkbackActive
import io.element.android.wysiwyg.compose.EditorStyledText
import io.element.android.wysiwyg.link.Link

@Composable
fun TimelineItemVideoView(
    content: TimelineItemVideoContent,
    hideMediaContent: Boolean,
    onContentClick: (() -> Unit)?,
    onLongClick: (() -> Unit)?,
    onShowContentClick: () -> Unit,
    onLinkClick: (Link) -> Unit,
    onLinkLongClick: (Link) -> Unit,
    onContentLayoutChange: (ContentAvoidingLayoutData) -> Unit,
    modifier: Modifier = Modifier,
    eventId: tech.orbitweb.orbitx.libraries.matrix.api.core.EventId? = null,
    isMine: Boolean = false,
) {
    val isViewOnce = content.caption?.contains("View-Once", ignoreCase = true) == true ||
        content.filename.contains("orbit_view_once_", ignoreCase = true) ||
        content.filename.contains("orbit_vo_", ignoreCase = true) ||
        content.filename.contains("viewonce", ignoreCase = true) ||
        content.filename.endsWith(".ephemeral", ignoreCase = true)
    if (isViewOnce) {
        val destroyedEventIds by tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.destroyedEventIdsFlow.collectAsState()
        val isDestroyed = eventId?.value != null && destroyedEventIds.contains(eventId.value)
        val context = androidx.compose.ui.platform.LocalContext.current
        ViewOnceMediaBubble(
            mediaType = "Video",
            isMine = isMine,
            onContentClick = if (isMine) {
                { android.widget.Toast.makeText(context, "You sent this as a view-once video. It cannot be reopened.", android.widget.Toast.LENGTH_SHORT).show() }
            } else if (isDestroyed) {
                { android.widget.Toast.makeText(context, "This video has already been viewed and destroyed", android.widget.Toast.LENGTH_SHORT).show() }
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

    val isTalkbackActive = isTalkbackActive()
    val a11yLabel = stringResource(CommonStrings.common_video)
    val description = content.caption?.let { "$a11yLabel: $it" } ?: a11yLabel
    Column(modifier = modifier) {
        val containerModifier = if (content.showCaption) {
            Modifier
                .padding(top = 6.dp)
                .clip(RoundedCornerShape(6.dp))
        } else {
            Modifier
        }
        TimelineItemAspectRatioBox(
            modifier = containerModifier.blurHashBackground(content.blurHash, alpha = 0.9f),
            aspectRatio = coerceRatioWhenHidingContent(content.aspectRatio, hideMediaContent),
            contentAlignment = Alignment.Center,
        ) {
            ProtectedView(
                hideContent = hideMediaContent,
                onShowClick = onShowContentClick,
            ) {
                var isLoaded by remember { mutableStateOf(false) }
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(if (isLoaded) Modifier.background(Color.White) else Modifier)
                        .then(
                            if (!isTalkbackActive && onContentClick != null) {
                                Modifier
                                    .combinedClickable(
                                        onClick = onContentClick,
                                        onLongClick = onLongClick,
                                    )
                                    .onKeyboardContextMenuAction(onLongClick)
                            } else {
                                Modifier
                            }
                        ),
                    model = MediaRequestData(
                        source = content.thumbnailSource,
                        kind = MediaRequestData.Kind.Thumbnail(
                            width = content.thumbnailWidth?.toLong() ?: MAX_THUMBNAIL_WIDTH,
                            height = content.thumbnailHeight?.toLong() ?: MAX_THUMBNAIL_HEIGHT,
                        )
                    ),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    contentDescription = description,
                    onState = { isLoaded = it is AsyncImagePainter.State.Success },
                )

                Box(
                    modifier = Modifier.roundedBackground(),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        imageVector = CompoundIcons.PlaySolid(),
                        contentDescription = stringResource(id = CommonStrings.a11y_play),
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.semantics { hideFromAccessibility() }
                    )
                }
            }
        }

        if (content.showCaption) {
            Spacer(modifier = Modifier.height(8.dp))
            val caption = if (LocalInspectionMode.current) {
                SpannedString(content.caption)
            } else {
                content.formattedCaption ?: SpannedString(content.caption)
            }
            CompositionLocalProvider(
                LocalContentColor provides ElementTheme.colors.textPrimary,
                LocalTextStyle provides ElementTheme.typography.fontBodyLgRegular,
            ) {
                val aspectRatio = content.aspectRatio ?: DEFAULT_ASPECT_RATIO
                EditorStyledText(
                    modifier = Modifier
                        .padding(horizontal = 4.dp) // This is (12.dp - 8.dp) contentPadding from CommonLayout
                        .widthIn(min = MIN_HEIGHT_IN_DP.dp * aspectRatio, max = MAX_HEIGHT_IN_DP.dp * aspectRatio),
                    text = caption,
                    onLinkClickedListener = onLinkClick,
                    onLinkLongClickedListener = onLinkLongClick,
                    style = ElementRichTextEditorStyle.textStyle(),
                    releaseOnDetach = false,
                    onTextLayout = ContentAvoidingLayout.measureLegacyLastTextLine(onContentLayoutChange = onContentLayoutChange),
                )
            }
        }
    }
}

@PreviewsDayNight
@Composable
internal fun TimelineItemVideoViewPreview(@PreviewParameter(TimelineItemVideoContentProvider::class) content: TimelineItemVideoContent) = ElementPreview {
    TimelineItemVideoView(
        content = content,
        hideMediaContent = false,
        onShowContentClick = {},
        onContentClick = {},
        onLongClick = {},
        onLinkClick = {},
        onLinkLongClick = {},
        onContentLayoutChange = {},
    )
}

@PreviewsDayNight
@Composable
internal fun TimelineItemVideoViewHideMediaContentPreview() = ElementPreview {
    TimelineItemVideoView(
        content = aTimelineItemVideoContent(),
        hideMediaContent = true,
        onShowContentClick = {},
        onContentClick = {},
        onLongClick = {},
        onLinkClick = {},
        onLinkLongClick = {},
        onContentLayoutChange = {},
    )
}

@PreviewsDayNight
@Composable
internal fun TimelineVideoWithCaptionRowPreview() = ElementPreview {
    Column {
        sequenceOf(false, true).forEach { isMine ->
            ATimelineItemEventRow(
                event = aTimelineItemEvent(
                    isMine = isMine,
                    content = aTimelineItemVideoContent().copy(
                        filename = "video.mp4",
                        caption = "A long caption that may wrap into several lines",
                        aspectRatio = 2.5f,
                    ),
                    groupPosition = TimelineItemGroupPosition.Last,
                ),
            )
        }
        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                isMine = false,
                content = aTimelineItemVideoContent().copy(
                    filename = "video.mp4",
                    caption = "Video with null aspect ratio",
                    aspectRatio = null,
                ),
                groupPosition = TimelineItemGroupPosition.Last,
            ),
        )
    }
}
