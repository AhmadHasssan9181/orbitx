/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.developer.appsettings

import tech.orbitweb.orbitx.features.preferences.impl.developer.tracing.LogLevelItem
import tech.orbitweb.orbitx.libraries.featureflag.ui.model.FeatureUiModel
import tech.orbitweb.orbitx.libraries.matrix.api.tracing.TraceLogPack

sealed interface AppDeveloperSettingsEvent {
    data class UpdateEnabledFeature(val feature: FeatureUiModel, val isEnabled: Boolean) : AppDeveloperSettingsEvent
    data class SetCustomElementCallBaseUrl(val baseUrl: String?) : AppDeveloperSettingsEvent
    data class SetTracingLogLevel(val logLevel: LogLevelItem) : AppDeveloperSettingsEvent
    data class ToggleTracingLogPack(val logPack: TraceLogPack, val enabled: Boolean) : AppDeveloperSettingsEvent
}
