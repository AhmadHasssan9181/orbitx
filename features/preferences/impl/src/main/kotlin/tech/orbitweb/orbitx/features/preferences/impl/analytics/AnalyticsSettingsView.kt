/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.analytics

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import tech.orbitweb.orbitx.features.analytics.api.preferences.AnalyticsPreferencesView
import tech.orbitweb.orbitx.libraries.designsystem.components.preferences.PreferencePage
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun AnalyticsSettingsView(
    state: AnalyticsSettingsState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PreferencePage(
        modifier = modifier,
        onBackClick = onBackClick,
        title = stringResource(id = CommonStrings.common_analytics)
    ) {
        AnalyticsPreferencesView(
            state = state.analyticsPreferencesState,
        )
    }
}

@PreviewsDayNight
@Composable
internal fun AnalyticsSettingsViewPreview(@PreviewParameter(AnalyticsSettingsStateProvider::class) state: AnalyticsSettingsState) = ElementPreview {
    AnalyticsSettingsView(
        state = state,
        onBackClick = {},
    )
}
