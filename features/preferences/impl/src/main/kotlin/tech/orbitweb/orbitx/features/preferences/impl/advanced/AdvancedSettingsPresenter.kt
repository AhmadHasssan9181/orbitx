/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.advanced

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.compound.theme.Theme
import tech.orbitweb.orbitx.compound.theme.mapToTheme
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.di.annotations.SessionCoroutineScope
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlagService
import tech.orbitweb.orbitx.libraries.featureflag.api.FeatureFlags
import tech.orbitweb.orbitx.libraries.preferences.api.store.AppPreferencesStore
import tech.orbitweb.orbitx.libraries.preferences.api.store.SessionPreferencesStore
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@Inject
class AdvancedSettingsPresenter(
    private val appPreferencesStore: AppPreferencesStore,
    private val sessionPreferencesStore: SessionPreferencesStore,
    private val mediaPreviewConfigStateStore: MediaPreviewConfigStateStore,
    @SessionCoroutineScope
    private val sessionCoroutineScope: CoroutineScope,
    private val featureFlagService: FeatureFlagService,
) : Presenter<AdvancedSettingsState> {
    @Composable
    override fun present(): AdvancedSettingsState {
        val isDeveloperModeEnabled by remember {
            appPreferencesStore.isDeveloperModeEnabledFlow()
        }.collectAsState(initial = false)
        val isSharePresenceEnabled by remember {
            sessionPreferencesStore.isSharePresenceEnabled()
        }.collectAsState(initial = true)
        val isBlackThemeAllowed by remember {
            featureFlagService.isFeatureEnabledFlow(FeatureFlags.AllowBlackTheme)
        }.collectAsState(initial = false)
        val theme = remember(isBlackThemeAllowed) {
            appPreferencesStore.getThemeFlow().mapToTheme(isBlackThemeAllowed)
        }.collectAsState(initial = Theme.System)

        val liveLocationMinimumDistanceUpdate by produceState<Int?>(null) {
            appPreferencesStore.getLiveLocationMinimumDistanceInMetersUpdateFlow().collect { value = it }
        }

        val mediaPreviewConfigState = mediaPreviewConfigStateStore.state()

        val themeOption by remember {
            derivedStateOf {
                when (theme.value) {
                    Theme.System -> ThemeOption.System
                    Theme.Dark -> ThemeOption.Dark
                    Theme.Black -> ThemeOption.Black
                    Theme.Light -> ThemeOption.Light
                    Theme.Ocean -> ThemeOption.Ocean
                    Theme.Forest -> ThemeOption.Forest
                    Theme.Sunset -> ThemeOption.Sunset
                    Theme.Cyberpunk -> ThemeOption.Cyberpunk
                    Theme.Graphite -> ThemeOption.Graphite
                    Theme.Slate -> ThemeOption.Slate
                }
            }
        }

        val hasSplitMediaQualityOptions by produceState<Boolean?>(null) {
            value = featureFlagService.isFeatureEnabled(FeatureFlags.SelectableMediaQuality)
        }

        val availableThemeOptions = remember(isBlackThemeAllowed) {
            if (isBlackThemeAllowed) {
                ThemeOption.entries
            } else {
                ThemeOption.entries.filterNot { it == ThemeOption.Black }
            }.toImmutableList()
        }

        val mediaOptimizationState by produceState<MediaOptimizationState?>(null) {
            val hasSplitMediaQualityOptionsFlow = featureFlagService.isFeatureEnabledFlow(FeatureFlags.SelectableMediaQuality)
            combine(
                hasSplitMediaQualityOptionsFlow,
                sessionPreferencesStore.doesOptimizeImages(),
                sessionPreferencesStore.getVideoCompressionPreset()
            ) { hasSplitOptions, compressImages, videoPreset ->
                if (hasSplitMediaQualityOptions == true) {
                    value = MediaOptimizationState.Split(
                        compressImages = compressImages,
                        videoPreset = videoPreset,
                    )
                } else if (hasSplitMediaQualityOptions == false) {
                    value = MediaOptimizationState.AllMedia(isEnabled = compressImages)
                }
            }.collect()
        }

        fun handleEvent(event: AdvancedSettingsEvents) {
            when (event) {
                is AdvancedSettingsEvents.SetDeveloperModeEnabled -> sessionCoroutineScope.launch {
                    appPreferencesStore.setDeveloperModeEnabled(event.enabled)
                }
                is AdvancedSettingsEvents.SetSharePresenceEnabled -> sessionCoroutineScope.launch {
                    sessionPreferencesStore.setSharePresence(event.enabled)
                }
                is AdvancedSettingsEvents.SetCompressMedia -> sessionCoroutineScope.launch {
                    sessionPreferencesStore.setOptimizeImages(event.compress)
                }
                is AdvancedSettingsEvents.SetTheme -> sessionCoroutineScope.launch {
                    when (event.theme) {
                        ThemeOption.System -> appPreferencesStore.setTheme(Theme.System.name)
                        ThemeOption.Dark -> appPreferencesStore.setTheme(Theme.Dark.name)
                        ThemeOption.Black -> appPreferencesStore.setTheme(Theme.Black.name)
                        ThemeOption.Light -> appPreferencesStore.setTheme(Theme.Light.name)
                        ThemeOption.Ocean -> appPreferencesStore.setTheme(Theme.Ocean.name)
                        ThemeOption.Forest -> appPreferencesStore.setTheme(Theme.Forest.name)
                        ThemeOption.Sunset -> appPreferencesStore.setTheme(Theme.Sunset.name)
                        ThemeOption.Cyberpunk -> appPreferencesStore.setTheme(Theme.Cyberpunk.name)
                        ThemeOption.Graphite -> appPreferencesStore.setTheme(Theme.Graphite.name)
                        ThemeOption.Slate -> appPreferencesStore.setTheme(Theme.Slate.name)
                    }
                }
                is AdvancedSettingsEvents.SetHideInviteAvatars -> mediaPreviewConfigStateStore.setHideInviteAvatars(event.value)
                is AdvancedSettingsEvents.SetTimelineMediaPreviewValue -> mediaPreviewConfigStateStore.setTimelineMediaPreviewValue(event.value)
                is AdvancedSettingsEvents.SetLiveLocationMinimumDistanceUpdate -> sessionCoroutineScope.launch {
                    appPreferencesStore.setLiveLocationMinimumDistanceInMetersUpdate(event.value)
                }
                is AdvancedSettingsEvents.SetCompressImages -> sessionCoroutineScope.launch {
                    sessionPreferencesStore.setOptimizeImages(event.compress)
                }
                is AdvancedSettingsEvents.SetVideoUploadQuality -> sessionCoroutineScope.launch {
                    sessionPreferencesStore.setVideoCompressionPreset(event.videoPreset)
                }
            }
        }

        return AdvancedSettingsState(
            isDeveloperModeEnabled = isDeveloperModeEnabled,
            isSharePresenceEnabled = isSharePresenceEnabled,
            mediaOptimizationState = mediaOptimizationState,
            theme = themeOption,
            availableThemeOptions = availableThemeOptions,
            mediaPreviewConfigState = mediaPreviewConfigState,
            liveLocationMinimumDistanceUpdate = liveLocationMinimumDistanceUpdate,
            eventSink = ::handleEvent,
        )
    }
}
