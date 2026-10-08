/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.analytics.api.preferences

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

open class AnalyticsPreferencesStateProvider : PreviewParameterProvider<AnalyticsPreferencesState> {
    override val values: Sequence<AnalyticsPreferencesState>
        get() = sequenceOf(
            aAnalyticsPreferencesState().copy(isEnabled = true),
            aAnalyticsPreferencesState().copy(isEnabled = true, policyUrl = ""),
        )
}

fun aAnalyticsPreferencesState(
    applicationName: String = "ORBIT Chat",
    isEnabled: Boolean = false,
    policyUrl: String = "https://orbitweb.tech/privacy",
) = AnalyticsPreferencesState(
    applicationName = applicationName,
    isEnabled = isEnabled,
    policyUrl = policyUrl,
    eventSink = {}
)
