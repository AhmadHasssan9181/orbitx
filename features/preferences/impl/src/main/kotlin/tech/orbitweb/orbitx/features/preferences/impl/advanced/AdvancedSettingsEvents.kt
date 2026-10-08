/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.advanced

import tech.orbitweb.orbitx.libraries.matrix.api.media.MediaPreviewValue
import tech.orbitweb.orbitx.libraries.preferences.api.store.VideoCompressionPreset

sealed interface AdvancedSettingsEvents {
    data class SetDeveloperModeEnabled(val enabled: Boolean) : AdvancedSettingsEvents
    data class SetSharePresenceEnabled(val enabled: Boolean) : AdvancedSettingsEvents
    data class SetCompressMedia(val compress: Boolean) : AdvancedSettingsEvents
    data class SetCompressImages(val compress: Boolean) : AdvancedSettingsEvents
    data class SetVideoUploadQuality(val videoPreset: VideoCompressionPreset) : AdvancedSettingsEvents
    data class SetTheme(val theme: ThemeOption) : AdvancedSettingsEvents
    data class SetTimelineMediaPreviewValue(val value: MediaPreviewValue) : AdvancedSettingsEvents
    data class SetHideInviteAvatars(val value: Boolean) : AdvancedSettingsEvents
    data class SetLiveLocationMinimumDistanceUpdate(val value: Int) : AdvancedSettingsEvents
}
