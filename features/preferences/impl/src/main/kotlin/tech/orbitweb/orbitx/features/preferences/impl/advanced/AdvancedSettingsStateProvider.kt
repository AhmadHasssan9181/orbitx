/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.advanced

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaPreviewValue
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

open class AdvancedSettingsStateProvider : PreviewParameterProvider<AdvancedSettingsState> {
    override val values: Sequence<AdvancedSettingsState>
        get() = sequenceOf(
            aAdvancedSettingsState(),
            aAdvancedSettingsState(isDeveloperModeEnabled = true),
            aAdvancedSettingsState(isSharePresenceEnabled = true),
            aAdvancedSettingsState(mediaOptimizationState = MediaOptimizationState.AllMedia(isEnabled = true)),
            aAdvancedSettingsState(hideInviteAvatars = true),
            aAdvancedSettingsState(timelineMediaPreviewValue = MediaPreviewValue.Off),
            aAdvancedSettingsState(setHideInviteAvatarsAction = AsyncAction.Loading),
            aAdvancedSettingsState(setTimelineMediaPreviewAction = AsyncAction.Loading),
            aAdvancedSettingsState(mediaOptimizationState = MediaOptimizationState.Split(
                compressImages = true,
                videoPreset = VideoCompressionPreset.HIGH,
            )),
        )
}

fun aAdvancedSettingsState(
    isDeveloperModeEnabled: Boolean = false,
    isSharePresenceEnabled: Boolean = false,
    mediaOptimizationState: MediaOptimizationState = MediaOptimizationState.AllMedia(isEnabled = false),
    theme: ThemeOption = ThemeOption.System,
    availableThemeOptions: ImmutableList<ThemeOption> = ThemeOption.entries.toImmutableList(),
    hideInviteAvatars: Boolean = false,
    timelineMediaPreviewValue: MediaPreviewValue = MediaPreviewValue.On,
    liveLocationMinimumDistanceUpdate: Int? = 50,
    setTimelineMediaPreviewAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    setHideInviteAvatarsAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
    eventSink: (AdvancedSettingsEvents) -> Unit = {},
) = AdvancedSettingsState(
    isDeveloperModeEnabled = isDeveloperModeEnabled,
    isSharePresenceEnabled = isSharePresenceEnabled,
    mediaOptimizationState = mediaOptimizationState,
    theme = theme,
    availableThemeOptions = availableThemeOptions,
    mediaPreviewConfigState = MediaPreviewConfigState(
        hideInviteAvatars = hideInviteAvatars,
        timelineMediaPreviewValue = timelineMediaPreviewValue,
        setTimelineMediaPreviewAction = setTimelineMediaPreviewAction,
        setHideInviteAvatarsAction = setHideInviteAvatarsAction
    ),
    liveLocationMinimumDistanceUpdate = liveLocationMinimumDistanceUpdate,
    eventSink = eventSink
)
