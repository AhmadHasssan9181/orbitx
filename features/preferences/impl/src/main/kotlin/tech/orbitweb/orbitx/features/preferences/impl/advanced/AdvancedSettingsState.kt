/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.advanced

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.features.preferences.impl.R
import tech.orbitweb.orbitx.libraries.designsystem.components.preferences.DropdownOption
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset
import kotlinx.collections.immutable.ImmutableList

data class AdvancedSettingsState(
    val isDeveloperModeEnabled: Boolean,
    val isSharePresenceEnabled: Boolean,
    val mediaOptimizationState: MediaOptimizationState?,
    val theme: ThemeOption,
    val availableThemeOptions: ImmutableList<ThemeOption>,
    val mediaPreviewConfigState: MediaPreviewConfigState,
    val liveLocationMinimumDistanceUpdate: Int?,
    val eventSink: (AdvancedSettingsEvents) -> Unit
)

sealed interface MediaOptimizationState {
    data class AllMedia(val isEnabled: Boolean) : MediaOptimizationState
    data class Split(
        val compressImages: Boolean,
        val videoPreset: VideoCompressionPreset,
    ) : MediaOptimizationState

    val shouldCompressImages: Boolean get() = when (this) {
        is AllMedia -> isEnabled
        is Split -> compressImages
    }
}

enum class ThemeOption : DropdownOption {
    System {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = stringResource(R.string.theme_system)
    },

    Light {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = stringResource(R.string.theme_light)
    },

    Dark {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = stringResource(R.string.theme_dark)
    },

    Black {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = stringResource(R.string.theme_black)
    },

    Ocean {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = "Ocean"
    },

    Forest {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = "Forest"
    },

    Sunset {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = "Sunset"
    },

    Cyberpunk {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = "Cyberpunk"
    },

    Graphite {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = "Graphite"
    },

    Slate {
        @Composable
        @ReadOnlyComposable
        override fun getText(): String = "Slate"
    }
}
