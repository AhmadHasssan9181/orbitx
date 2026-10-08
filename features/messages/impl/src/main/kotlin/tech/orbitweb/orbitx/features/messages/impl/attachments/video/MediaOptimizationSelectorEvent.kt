/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.attachments.video

import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset

sealed interface MediaOptimizationSelectorEvent {
    data class SelectImageOptimization(val enabled: Boolean) : MediaOptimizationSelectorEvent
    data class SelectVideoPreset(val preset: VideoCompressionPreset) : MediaOptimizationSelectorEvent
    data object OpenVideoPresetSelectorDialog : MediaOptimizationSelectorEvent
    data object DismissVideoPresetSelectorDialog : MediaOptimizationSelectorEvent
}
