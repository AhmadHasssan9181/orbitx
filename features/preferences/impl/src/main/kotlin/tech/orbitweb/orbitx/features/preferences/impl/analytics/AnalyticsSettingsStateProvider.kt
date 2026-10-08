/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.analytics

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.analytics.api.preferences.aAnalyticsPreferencesState

open class AnalyticsSettingsStateProvider : PreviewParameterProvider<AnalyticsSettingsState> {
    override val values: Sequence<AnalyticsSettingsState>
        get() = sequenceOf(
            aAnalyticsSettingsState(),
        )
}

fun aAnalyticsSettingsState() = AnalyticsSettingsState(
    analyticsPreferencesState = aAnalyticsPreferencesState(),
)
