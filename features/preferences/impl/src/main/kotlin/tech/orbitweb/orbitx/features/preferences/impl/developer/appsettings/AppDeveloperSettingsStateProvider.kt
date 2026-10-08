/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.developer.appsettings

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.preferences.impl.developer.tracing.LogLevelItem
import tech.orbitweb.orbitx.features.rageshake.api.preferences.aRageshakePreferencesState
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.featureflag.ui.model.aFeatureUiModelList
import tech.orbitweb.orbitx.libraries.matrix.api.tracing.TraceLogPack

open class AppDeveloperSettingsStateProvider : PreviewParameterProvider<AppDeveloperSettingsState> {
    override val values: Sequence<AppDeveloperSettingsState>
        get() = sequenceOf(
            anAppDeveloperSettingsState(),
            anAppDeveloperSettingsState(
                customElementCallBaseUrlState = aCustomElementCallBaseUrlState(
                    baseUrl = "https://call.element.ahoy",
                )
            ),
        )
}

fun anAppDeveloperSettingsState(
    customElementCallBaseUrlState: CustomElementCallBaseUrlState = aCustomElementCallBaseUrlState(),
    traceLogPacks: List<TraceLogPack> = emptyList(),
    eventSink: (AppDeveloperSettingsEvent) -> Unit = {},
) = AppDeveloperSettingsState(
    features = aFeatureUiModelList(),
    rageshakeState = aRageshakePreferencesState(),
    customElementCallBaseUrlState = customElementCallBaseUrlState,
    tracingLogLevel = AsyncData.Success(LogLevelItem.INFO),
    tracingLogPacks = traceLogPacks.toImmutableList(),
    eventSink = eventSink,
)

fun aCustomElementCallBaseUrlState(
    baseUrl: String? = null,
    validator: (String?) -> Boolean = { true },
) = CustomElementCallBaseUrlState(
    baseUrl = baseUrl,
    validator = validator,
)
