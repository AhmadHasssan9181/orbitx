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
import tech.orbitweb.orbitx.libraries.fullscreenintent.api.FullScreenIntentPermissionsEvents
import tech.orbitweb.orbitx.libraries.fullscreenintent.api.FullScreenIntentPermissionsState
import tech.orbitweb.orbitx.libraries.fullscreenintent.api.aFullScreenIntentPermissionsState
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
fun FullScreenIntentPermissionBanner(
    state: FullScreenIntentPermissionsState,
    modifier: Modifier = Modifier
) {
    Announcement(
        title = stringResource(R.string.full_screen_intent_banner_title),
        description = stringResource(R.string.full_screen_intent_banner_message),
        type = AnnouncementType.Actionable(
            actionText = stringResource(CommonStrings.action_continue),
            onDismissClick = { state.eventSink(FullScreenIntentPermissionsEvents.Dismiss) },
            onActionClick = { state.eventSink(FullScreenIntentPermissionsEvents.OpenSettings) },
        ),
        modifier = modifier.roomListBannerPadding(),
    )
}

@PreviewsDayNight
@Composable
internal fun FullScreenIntentPermissionBannerPreview() {
    ElementPreview {
        FullScreenIntentPermissionBanner(aFullScreenIntentPermissionsState())
    }
}
