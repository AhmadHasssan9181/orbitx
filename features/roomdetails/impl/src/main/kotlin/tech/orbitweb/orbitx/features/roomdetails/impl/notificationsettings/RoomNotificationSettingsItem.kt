/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.impl.notificationsettings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.features.roomdetails.impl.R
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationMode
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class RoomNotificationSettingsItem(
    val mode: RoomNotificationMode,
    val title: String,
)

@Composable
fun roomNotificationSettingsItems(): ImmutableList<RoomNotificationSettingsItem> {
    return RoomNotificationMode.entries
        .map {
            when (it) {
                RoomNotificationMode.ALL_MESSAGES -> RoomNotificationSettingsItem(
                    mode = it,
                    title = stringResource(R.string.screen_room_notification_settings_mode_all_messages),
                )
                RoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY -> RoomNotificationSettingsItem(
                    mode = it,
                    title = stringResource(R.string.screen_room_notification_settings_mode_mentions_and_keywords),
                )
                RoomNotificationMode.MUTE -> RoomNotificationSettingsItem(
                    mode = it,
                    title = stringResource(CommonStrings.common_mute),
                )
            }
        }
        .toImmutableList()
}
