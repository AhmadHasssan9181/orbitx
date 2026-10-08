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
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

@Composable
internal fun NewNotificationSoundBanner(
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Announcement(
        modifier = modifier.roomListBannerPadding(),
        title = stringResource(R.string.banner_new_sound_title),
        description = stringResource(R.string.banner_new_sound_message),
        type = AnnouncementType.Actionable(
            actionText = stringResource(CommonStrings.action_ok),
            onActionClick = onDismissClick,
            onDismissClick = onDismissClick,
        ),
    )
}

@PreviewsDayNight
@Composable
internal fun NewNotificationSoundBannerPreview() = ElementPreview {
    NewNotificationSoundBanner(
        onDismissClick = {},
    )
}
