/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import android.text.SpannedString
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayout
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayoutData
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemTextBasedContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemTextBasedContentProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.aTimelineItemTextContent
import tech.orbitweb.orbitx.features.messages.impl.utils.containsOnlyEmojis
import tech.orbitweb.orbitx.libraries.androidutils.text.LinkifyHelper
import tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralCrypto
import tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.textcomposer.ElementRichTextEditorStyle
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.LocalMentionSpanUpdater
import io.element.android.wysiwyg.compose.EditorStyledText
import io.element.android.wysiwyg.link.Link

@Composable
fun TimelineItemTextView(
    content: TimelineItemTextBasedContent,
    onLinkClick: (Link) -> Unit,
    onLinkLongClick: (Link) -> Unit,
    modifier: Modifier = Modifier,
    onContentLayoutChange: (ContentAvoidingLayoutData) -> Unit = {},
    eventId: tech.orbitweb.orbitx.libraries.matrix.api.core.EventId? = null,
    eventSink: ((tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent.TimelineItemEvent) -> Unit)? = null,
    isMine: Boolean = false,
) {
    if (content.body.startsWith("🔒 View-Once Message")) {
        val destroyedEventIds by OrbitEphemeralTracker.destroyedEventIdsFlow.collectAsState()
        val isDestroyed = eventId?.value != null && destroyedEventIds.contains(eventId.value)
        val context = LocalContext.current
        var showRevealDialog by remember { mutableStateOf(false) }

        val closeAndDestroy = {
            showRevealDialog = false
            if (eventId != null) {
                OrbitEphemeralTracker.markDestroyed(eventId.value)
                eventSink?.invoke(TimelineEvent.RedactViewOnce(eventId))
            }
        }

        ViewOnceMediaBubble(
            mediaType = "Message",
            isMine = isMine,
            onContentClick = if (isMine) {
                { Toast.makeText(context, "You sent this as a view-once message. It cannot be reopened.", Toast.LENGTH_SHORT).show() }
            } else if (isDestroyed) {
                { Toast.makeText(context, "This message has already been viewed and destroyed", Toast.LENGTH_SHORT).show() }
            } else {
                {
                    if (eventId != null) {
                        OrbitEphemeralTracker.markDestroyed(eventId.value)
                    }
                    showRevealDialog = true
                }
            },
            onLongClick = null,
            onContentLayoutChange = onContentLayoutChange,
            isDestroyed = isDestroyed,
            modifier = modifier,
        )

        if (showRevealDialog) {
            val rawCipher = content.body.substringAfter("🔒 View-Once Message").trim()
            val decrypted = remember(rawCipher) {
                OrbitEphemeralCrypto.decryptString(rawCipher) ?: "Unable to decrypt message."
            }
            androidx.compose.runtime.DisposableEffect(Unit) {
                onDispose {
                    if (eventId != null) {
                        OrbitEphemeralTracker.markDestroyed(eventId.value)
                        eventSink?.invoke(TimelineEvent.RedactViewOnce(eventId))
                    }
                }
            }
            Dialog(
                onDismissRequest = closeAndDestroy,
                properties = DialogProperties(
                    securePolicy = SecureFlagPolicy.SecureOn
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF16152B))
                        .border(1.dp, Color(0xFF8B5CF6).copy(alpha = 0.6f), RoundedCornerShape(24.dp))
                        .padding(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "1",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp
                            )
                        }
                        Text(
                            text = "View-Once Message",
                            style = ElementTheme.typography.fontHeadingSmMedium,
                            color = Color.White
                        )
                        Text(
                            text = decrypted,
                            style = ElementTheme.typography.fontBodyLgRegular,
                            color = Color(0xFFF3F4F6),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0F0E1A))
                                .padding(16.dp)
                        )
                        Text(
                            text = "This message will be permanently destroyed once you exit.",
                            style = ElementTheme.typography.fontBodySmRegular,
                            color = Color(0xFF9CA3AF),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = closeAndDestroy,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B5CF6)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Done",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        return
    }

    val emojiOnly = content.formattedBody.toString() == content.body &&
        content.body.replace(" ", "").containsOnlyEmojis()
    val textStyle = when {
        emojiOnly -> ElementTheme.typography.fontHeadingXlRegular
        else -> ElementTheme.typography.fontBodyLgRegular
    }
    CompositionLocalProvider(
        LocalContentColor provides ElementTheme.colors.textPrimary,
        LocalTextStyle provides textStyle
    ) {
        val text = getTextWithResolvedMentions(content)
        Box(modifier.semantics { contentDescription = content.plainText }) {
            EditorStyledText(
                text = text,
                onLinkClickedListener = onLinkClick,
                onLinkLongClickedListener = onLinkLongClick,
                style = ElementRichTextEditorStyle.textStyle(),
                onTextLayout = ContentAvoidingLayout.measureLegacyLastTextLine(onContentLayoutChange = onContentLayoutChange),
                releaseOnDetach = false,
            )
        }
    }
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun getTextWithResolvedMentions(content: TimelineItemTextBasedContent): CharSequence {
    val mentionSpanUpdater = LocalMentionSpanUpdater.current
    val bodyWithResolvedMentions = mentionSpanUpdater.rememberMentionSpans(content.formattedBody)
    return SpannedString.valueOf(bodyWithResolvedMentions)
}

@PreviewsDayNight
@Composable
internal fun TimelineItemTextViewPreview(
    @PreviewParameter(TimelineItemTextBasedContentProvider::class) content: TimelineItemTextBasedContent
) = ElementPreview {
    TimelineItemTextView(
        content = content,
        onLinkClick = {},
        onLinkLongClick = {},
    )
}

@Preview
@Composable
internal fun TimelineItemTextViewWithLinkifiedUrlPreview() = ElementPreview {
    val content = aTimelineItemTextContent(
        formattedBody = LinkifyHelper.linkify("The link should end after the first '?' (url: github.com/element-hq/element-x-android/README?)?.")
    )
    TimelineItemTextView(
        content = content,
        onLinkClick = {},
        onLinkLongClick = {},
    )
}

@Preview
@Composable
internal fun TimelineItemTextViewWithLinkifiedUrlAndNestedParenthesisPreview() = ElementPreview {
    val content = aTimelineItemTextContent(
        formattedBody = LinkifyHelper.linkify("The link should end after the '(ME)' ((url: github.com/element-hq/element-x-android/READ(ME)))!")
    )
    TimelineItemTextView(
        content = content,
        onLinkClick = {},
        onLinkLongClick = {},
    )
}
