/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.developer.appsettings

import tech.orbitweb.orbitx.features.preferences.impl.developer.tracing.LogLevelItem
import tech.orbitweb.orbitx.features.rageshake.api.preferences.RageshakePreferencesState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.featureflag.ui.model.FeatureUiModel
import tech.orbitweb.orbitx.libraries.matrix.api.tracing.TraceLogPack
import kotlinx.collections.immutable.ImmutableList

data class AppDeveloperSettingsState(
    val features: ImmutableList<FeatureUiModel>,
    val rageshakeState: RageshakePreferencesState,
    val customElementCallBaseUrlState: CustomElementCallBaseUrlState,
    val tracingLogLevel: AsyncData<LogLevelItem>,
    val tracingLogPacks: ImmutableList<TraceLogPack>,
    val eventSink: (AppDeveloperSettingsEvent) -> Unit
)

data class CustomElementCallBaseUrlState(
    val baseUrl: String?,
    val validator: (String?) -> Boolean,
)
