/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.impl.notificationsettings

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.roomdetails.impl.aRoomNotificationSettings
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationMode

internal class UserDefinedRoomNotificationSettingsStateProvider : PreviewParameterProvider<RoomNotificationSettingsState> {
    override val values: Sequence<RoomNotificationSettingsState>
        get() = sequenceOf(
            RoomNotificationSettingsState(
                showUserDefinedSettingStyle = false,
                roomName = "Room 1",
                AsyncData.Success(
                    aRoomNotificationSettings(
                        mode = RoomNotificationMode.MUTE,
                        isDefault = false
                    )
                ),
                pendingRoomNotificationMode = null,
                pendingSetDefault = null,
                defaultRoomNotificationMode = RoomNotificationMode.ALL_MESSAGES,
                setNotificationSettingAction = AsyncAction.Uninitialized,
                restoreDefaultAction = AsyncAction.Uninitialized,
                displayMentionsOnlyDisclaimer = false,
                eventSink = { },
            ),
        )
}
