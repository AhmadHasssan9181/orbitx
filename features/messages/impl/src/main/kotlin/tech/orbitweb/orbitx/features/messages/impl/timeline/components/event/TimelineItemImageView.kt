/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import android.text.SpannedString
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import io.element.android.wysiwyg.compose.EditorStyledText
import io.element.android.wysiwyg.link.Link
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.ATimelineItemEventRow
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayout
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayoutData
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemGroupPosition
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemImageContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemImageContentProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemImageContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.ProtectedView
import tech.orbitweb.orbitx.features.messages.impl.timeline.protection.coerceRatioWhenHidingContent
import tech.orbitweb.orbitx.libraries.designsystem.components.blurhash.blurHashBackground
import tech.orbitweb.orbitx.libraries.designsystem.modifiers.onKeyboardContextMenuAction
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.textcomposer.ElementRichTextEditorStyle
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.libraries.ui.utils.a11y.isTalkbackActive

private const val TALL_IMAGE_RATIO_DIVISOR = 3
@Composable
fun TimelineItemImageView(
    content: TimelineItemImageContent,
    hideMediaContent: Boolean,
    onContentClick: (() -> Unit)?,
    onLongClick: (() -> Unit)?,
    onLinkClick: (Link) -> Unit,
    onLinkLongClick: (Link) -> Unit,
    onShowContentClick: () -> Unit,
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
            mediaType = "Photo",
            isMine = isMine,
            onContentClick = if (isMine) {
                {
                    android.widget.Toast.makeText(
                        context,
                        "You sent this as a view-once photo. It cannot be reopened.",
                        android.widget.Toast.LENGTH_SHORT,
                    ).show()
                }
            } else if (isDestroyed) {
                {
                    android.widget.Toast.makeText(
                        context,
                        "This photo has already been viewed and destroyed",
                        android.widget.Toast.LENGTH_SHORT,
                    ).show()
                }
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

    val a11yLabel = stringResource(CommonStrings.common_image)
    val description = content.caption?.let { "$a11yLabel: $it" } ?: a11yLabel
    Column(modifier = modifier) {
        val containerModifier = if (content.showCaption) {
            Modifier.clip(RoundedCornerShape(10.dp))
        } else {
            Modifier
        }
        TimelineItemAspectRatioBox(
            modifier = containerModifier.blurHashBackground(content.blurhash, alpha = 0.9f).align(Alignment.CenterHorizontally),
            aspectRatio = coerceRatioWhenHidingContent(content.aspectRatio, hideMediaContent),
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
                            if (!isTalkbackActive() && onContentClick != null) {
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
                    model = content.thumbnailMediaRequestData,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    contentDescription = description,
                    onState = { isLoaded = it is AsyncImagePainter.State.Success },
                )
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
                LocalTextStyle provides ElementTheme.typography.fontBodyLgRegular
            ) {
                val width = content.width ?: 0
                val height = content.height ?: 0
                // if image is narrow and tall use DEFAULT_ASPECT_RATIO
                val aspectRatio = if (width < height / TALL_IMAGE_RATIO_DIVISOR) {
                    DEFAULT_ASPECT_RATIO
                } else {
                    content.aspectRatio ?: DEFAULT_ASPECT_RATIO
                }
                EditorStyledText(
                    modifier = Modifier
                        .padding(horizontal = 4.dp) // This is (12.dp - 8.dp) contentPadding from CommonLayout
                        .widthIn(min = MIN_HEIGHT_IN_DP.dp * aspectRatio, max = MAX_HEIGHT_IN_DP.dp * aspectRatio),
                    text = caption,
                    style = ElementRichTextEditorStyle.textStyle(),
                    onLinkClickedListener = onLinkClick,
                    onLinkLongClickedListener = onLinkLongClick,
                    releaseOnDetach = false,
                    onTextLayout = ContentAvoidingLayout.measureLegacyLastTextLine(onContentLayoutChange = onContentLayoutChange),
                )
            }
        }
    }
}

@PreviewsDayNight
@Composable
internal fun TimelineItemImageViewPreview(@PreviewParameter(TimelineItemImageContentProvider::class) content: TimelineItemImageContent) = ElementPreview {
    TimelineItemImageView(
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
internal fun TimelineItemImageViewHideMediaContentPreview() = ElementPreview {
    TimelineItemImageView(
        content = aTimelineItemImageContent(),
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
internal fun TimelineImageWithCaptionRowPreview() = ElementPreview {
    Column {
        sequenceOf(false, true).forEach { isMine ->
            ATimelineItemEventRow(
                event = aTimelineItemEvent(
                    isMine = isMine,
                    content = aTimelineItemImageContent(
                        filename = "image.jpg",
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
                content = aTimelineItemImageContent(
                    filename = "image.jpg",
                    caption = "Image with null aspectRatio",
                    aspectRatio = null,
                ),
                groupPosition = TimelineItemGroupPosition.Last,
            ),
        )
    }
}

@PreviewsDayNight
@Composable
internal fun ATimelineItemEventRowPreview() = ElementPreview {
    Column {
        sequenceOf(false, true).forEach { isMine ->
            ATimelineItemEventRow(
                event = aTimelineItemEvent(
                    isMine = isMine,
                    content = aTimelineItemImageContent(
                        filename = "image.jpg",
                        caption = "A long caption that may wrap into several lines",
                        width = 80,
                        height = 300,
                        aspectRatio = 80f / 300f,
                    ),
                    groupPosition = TimelineItemGroupPosition.Last,
                ),
            )
        }
        ATimelineItemEventRow(
            event = aTimelineItemEvent(
                isMine = false,
                content = aTimelineItemImageContent(
                    filename = "image.jpg",
                    caption = "Narrow image with null aspectRatio",
                    width = 80,
                    height = 300,
                    aspectRatio = null,
                ),
                groupPosition = TimelineItemGroupPosition.Last,
            ),
        )
    }
}
