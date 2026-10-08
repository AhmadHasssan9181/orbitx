/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.features.home.impl.R
import tech.orbitweb.orbitx.libraries.designsystem.components.Announcement
import tech.orbitweb.orbitx.libraries.designsystem.components.AnnouncementType
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.push.api.battery.BatteryOptimizationEvents
import tech.orbitweb.orbitx.libraries.push.api.battery.BatteryOptimizationState
import tech.orbitweb.orbitx.libraries.push.api.battery.aBatteryOptimizationState

@Composable
internal fun BatteryOptimizationBanner(
    state: BatteryOptimizationState,
    modifier: Modifier = Modifier,
) {
    Announcement(
        modifier = modifier.roomListBannerPadding(),
        title = stringResource(R.string.banner_battery_optimization_title_android),
        description = stringResource(R.string.banner_battery_optimization_content_android),
        type = AnnouncementType.Actionable(
            actionText = stringResource(R.string.banner_battery_optimization_submit_android),
            onActionClick = { state.eventSink(BatteryOptimizationEvents.RequestDisableOptimizations) },
            onDismissClick = { state.eventSink(BatteryOptimizationEvents.Dismiss) },
        ),
    )
}

@PreviewsDayNight
@Composable
internal fun BatteryOptimizationBannerPreview() = ElementPreview {
    BatteryOptimizationBanner(
        state = aBatteryOptimizationState(),
    )
}
