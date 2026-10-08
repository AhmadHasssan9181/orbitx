/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import tech.orbitweb.orbitx.compound.theme.ElementTheme
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.features.messages.impl.timeline.components.layout.ContentAvoidingLayoutData
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVoiceContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemVoiceContentProvider
import tech.orbitweb.orbitx.libraries.designsystem.atomic.atoms.PlaybackSpeedButton
import tech.orbitweb.orbitx.libraries.designsystem.components.media.WaveformPlaybackView
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.CircularProgressIndicator
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Icon
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.IconButton
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.libraries.ui.utils.a11y.isTalkbackActive
import tech.orbitweb.orbitx.libraries.voiceplayer.api.VoiceMessageEvent
import tech.orbitweb.orbitx.libraries.voiceplayer.api.VoiceMessageState
import tech.orbitweb.orbitx.libraries.voiceplayer.api.VoiceMessageStateProvider
import kotlinx.coroutines.delay

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TimelineItemVoiceView(
    state: VoiceMessageState,
    content: TimelineItemVoiceContent,
    onContentLayoutChange: (ContentAvoidingLayoutData) -> Unit,
    modifier: Modifier = Modifier,
    eventSink: ((tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent.TimelineItemEvent) -> Unit)? = null,
    isMine: Boolean = false,
) {
    val isViewOnce = content.caption?.contains("View-Once", ignoreCase = true) == true ||
        content.filename.contains("orbit_vo_", ignoreCase = true) ||
        content.filename.contains("viewonce", ignoreCase = true) ||
        content.filename.contains("orbit_view_once_", ignoreCase = true) ||
        content.filename.endsWith(".ephemeral", ignoreCase = true)
    val destroyedEventIds by tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.destroyedEventIdsFlow.collectAsState()
    val isDestroyed = isViewOnce && content.eventId?.value != null && destroyedEventIds.contains(content.eventId.value)
    val context = androidx.compose.ui.platform.LocalContext.current

    if (isViewOnce && isMine) {
        ViewOnceMediaBubble(
            mediaType = "Voice message",
            isMine = true,
            isDestroyed = isDestroyed,
            onContentClick = {
                android.widget.Toast.makeText(context, "You sent this as a view-once voice message. It cannot be reopened.", android.widget.Toast.LENGTH_SHORT).show()
            },
            onLongClick = null,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier,
        )
        return
    }

    if (isViewOnce && isDestroyed) {
        ViewOnceMediaBubble(
            mediaType = "Voice message",
            isMine = isMine,
            isDestroyed = true,
            onContentClick = {
                android.widget.Toast.makeText(context, "This voice message has already been played and destroyed", android.widget.Toast.LENGTH_SHORT).show()
            },
            onLongClick = null,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier,
        )
        return
    }

    var hasStartedPlayback by remember { mutableStateOf(false) }
    var hasEverBeenPlaying by remember { mutableStateOf(false) }

    // In WhatsApp style: view-once voice note shows the ViewOnce bubble until the user taps to play
    if (isViewOnce && !hasStartedPlayback && state.buttonType != VoiceMessageState.ButtonType.Pause && state.buttonType != VoiceMessageState.ButtonType.Downloading) {
        ViewOnceMediaBubble(
            mediaType = "Voice message",
            isMine = false,
            isDestroyed = false,
            onContentClick = {
                content.eventId?.value?.let { tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.markDestroyed(it) }
                hasStartedPlayback = true
                state.eventSink(VoiceMessageEvent.PlayPause)
            },
            onLongClick = null,
            onContentLayoutChange = onContentLayoutChange,
            modifier = modifier,
        )
        return
    }

    // Protect playback from screenshot and screen recording
    DisposableEffect(hasStartedPlayback) {
        val window = (context as? android.app.Activity)?.window
        if (hasStartedPlayback && isViewOnce) {
            window?.addFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)
        }
        onDispose {
            if (hasStartedPlayback && isViewOnce && content.eventId != null) {
                tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.markDestroyed(content.eventId.value)
                eventSink?.invoke(tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent.RedactViewOnce(content.eventId))
                tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.cleanupVoiceCache(context.cacheDir)
            }
            window?.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    // Once playback begins and finishes (reaches end or player resets), mark destroyed and queue redaction
    LaunchedEffect(state.buttonType, state.progress) {
        if (state.buttonType == VoiceMessageState.ButtonType.Pause) {
            hasEverBeenPlaying = true
        } else if (hasEverBeenPlaying && isViewOnce && content.eventId != null) {
            if ((state.buttonType == VoiceMessageState.ButtonType.Play && !state.showCursor) || state.progress >= 0.98f) {
                tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.markDestroyed(content.eventId.value)
                eventSink?.invoke(tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent.RedactViewOnce(content.eventId))
                tech.orbitweb.orbitx.libraries.core.crypto.OrbitEphemeralTracker.cleanupVoiceCache(context.cacheDir)
            }
        }
    }

    fun playPause() {
        if (isViewOnce) {
            hasStartedPlayback = true
        }
        state.eventSink(VoiceMessageEvent.PlayPause)
    }

    val a11y = stringResource(CommonStrings.common_voice_message)
    val a11yActionLabel = stringResource(
        when (state.buttonType) {
            VoiceMessageState.ButtonType.Play -> CommonStrings.a11y_play
            VoiceMessageState.ButtonType.Pause -> CommonStrings.a11y_pause
            VoiceMessageState.ButtonType.Downloading -> CommonStrings.common_downloading
            VoiceMessageState.ButtonType.Retry -> CommonStrings.action_retry
            VoiceMessageState.ButtonType.Disabled -> CommonStrings.error_unknown
        }
    )

    Column(modifier = modifier) {
        if (isViewOnce) {
            Row(
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF8B5CF6).copy(alpha = 0.2f))
                    .border(1.dp, Color(0xFF8B5CF6).copy(alpha = 0.5f), CircleShape)
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(Color(0xFF8B5CF6), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    androidx.compose.material3.Text(
                        text = "1",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                androidx.compose.material3.Text(
                    text = "View-Once Audio • Playing once",
                    color = Color(0xFFDDD6FE),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        Row(
            modifier = Modifier
                .clearAndSetSemantics {
                    contentDescription = a11y
                    if (state.buttonType == VoiceMessageState.ButtonType.Disabled) {
                        disabled()
                    } else if (state.buttonType in listOf(VoiceMessageState.ButtonType.Play, VoiceMessageState.ButtonType.Pause)) {
                        onClick(label = a11yActionLabel) {
                            playPause()
                            true
                        }
                    }
                }
                .onSizeChanged {
                    onContentLayoutChange(
                        ContentAvoidingLayoutData(
                            contentWidth = it.width,
                            contentHeight = it.height,
                        )
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (!isTalkbackActive()) {
                when (state.buttonType) {
                    VoiceMessageState.ButtonType.Play -> PlayButton(onClick = ::playPause)
                    VoiceMessageState.ButtonType.Pause -> PauseButton(onClick = ::playPause)
                    VoiceMessageState.ButtonType.Downloading -> ProgressButton()
                    VoiceMessageState.ButtonType.Retry -> RetryButton(onClick = ::playPause)
                    VoiceMessageState.ButtonType.Disabled -> PlayButton(onClick = {}, enabled = false)
                }
            }
            Spacer(Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                PlaybackSpeedButton(
                    speed = state.playbackSpeed,
                    onClick = { state.eventSink(VoiceMessageEvent.ChangePlaybackSpeed) },
                )
                Text(
                    text = state.time,
                    color = ElementTheme.colors.textSecondary,
                    style = ElementTheme.typography.fontBodySmMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(Modifier.width(8.dp))
            WaveformPlaybackView(
                showCursor = state.showCursor,
                playbackProgress = state.progress,
                waveform = content.waveform,
                modifier = Modifier
                    .weight(1f)
                    .height(34.dp),
                seekEnabled = !isTalkbackActive(),
                onSeek = { state.eventSink(VoiceMessageEvent.Seek(it)) },
            )
        }
    }
}

@Composable
private fun PlayButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    CustomIconButton(
        onClick = onClick,
        enabled = enabled,
    ) {
        ControlIcon(
            imageVector = CompoundIcons.PlaySolid(),
            contentDescription = stringResource(id = CommonStrings.a11y_play),
        )
    }
}

@Composable
private fun PauseButton(
    onClick: () -> Unit,
) {
    CustomIconButton(
        onClick = onClick,
    ) {
        ControlIcon(
            imageVector = CompoundIcons.PauseSolid(),
            contentDescription = stringResource(id = CommonStrings.a11y_pause),
        )
    }
}

@Composable
private fun RetryButton(
    onClick: () -> Unit,
) {
    CustomIconButton(
        onClick = onClick,
    ) {
        ControlIcon(
            imageVector = CompoundIcons.Restart(),
            contentDescription = stringResource(id = CommonStrings.action_retry),
        )
    }
}

@Composable
private fun ControlIcon(
    imageVector: ImageVector,
    contentDescription: String?,
) {
    Icon(
        modifier = Modifier.padding(vertical = 10.dp),
        imageVector = imageVector,
        contentDescription = contentDescription,
    )
}

/**
 * Progress button is shown when the voice message is being downloaded.
 *
 * The progress indicator is optimistic and displays a pause button (which
 * indicates the audio is playing) for 2 seconds before revealing the
 * actual progress indicator.
 */
@Composable
private fun ProgressButton(
    displayImmediately: Boolean = false,
) {
    var canDisplay by remember { mutableStateOf(displayImmediately) }
    LaunchedEffect(Unit) {
        delay(2000L)
        canDisplay = true
    }
    CustomIconButton(
        onClick = {},
        enabled = false,
    ) {
        if (canDisplay) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(2.dp)
                    .size(16.dp),
                color = ElementTheme.colors.iconSecondary,
                strokeWidth = 2.dp,
            )
        } else {
            ControlIcon(
                imageVector = CompoundIcons.PauseSolid(),
                contentDescription = stringResource(id = CommonStrings.a11y_pause),
            )
        }
    }
}

@Composable
private fun CustomIconButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(color = ElementTheme.colors.bgCanvasDefault, shape = CircleShape)
            .size(36.dp),
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = ElementTheme.colors.iconSecondary,
            disabledContentColor = ElementTheme.colors.iconDisabled,
        ),
        content = content,
    )
}

open class TimelineItemVoiceViewParametersProvider : PreviewParameterProvider<TimelineItemVoiceViewParameters> {
    private val voiceMessageStateProvider = VoiceMessageStateProvider()
    private val timelineItemVoiceContentProvider = TimelineItemVoiceContentProvider()
    override val values: Sequence<TimelineItemVoiceViewParameters>
        get() = timelineItemVoiceContentProvider.values.flatMap { content ->
            voiceMessageStateProvider.values.map { state ->
                TimelineItemVoiceViewParameters(
                    state = state,
                    content = content,
                )
            }
        }
}

data class TimelineItemVoiceViewParameters(
    val state: VoiceMessageState,
    val content: TimelineItemVoiceContent,
)

@PreviewsDayNight
@Composable
internal fun TimelineItemVoiceViewPreview(
    @PreviewParameter(TimelineItemVoiceViewParametersProvider::class) timelineItemVoiceViewParameters: TimelineItemVoiceViewParameters,
) = ElementPreview {
    TimelineItemVoiceView(
        state = timelineItemVoiceViewParameters.state,
        content = timelineItemVoiceViewParameters.content,
        onContentLayoutChange = {},
    )
}

@PreviewsDayNight
@Composable
internal fun TimelineItemVoiceViewUnifiedPreview() = ElementPreview {
    val timelineItemVoiceViewParametersProvider = TimelineItemVoiceViewParametersProvider()
    Column {
        timelineItemVoiceViewParametersProvider.values.forEach {
            TimelineItemVoiceView(
                state = it.state,
                content = it.content,
                onContentLayoutChange = {},
            )
        }
    }
}

@PreviewsDayNight
@Composable
internal fun ProgressButtonPreview() = ElementPreview {
    Row {
        ProgressButton(displayImmediately = true)
        ProgressButton(displayImmediately = false)
    }
}
