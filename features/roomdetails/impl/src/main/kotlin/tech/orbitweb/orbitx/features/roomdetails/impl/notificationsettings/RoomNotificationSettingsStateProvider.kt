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

internal class RoomNotificationSettingsStateProvider : PreviewParameterProvider<RoomNotificationSettingsState> {
    override val values: Sequence<RoomNotificationSettingsState>
        get() = sequenceOf(
            aRoomNotificationSettingsState(),
            aRoomNotificationSettingsState(isDefault = false),
            aRoomNotificationSettingsState(setNotificationSettingAction = AsyncAction.Loading),
            aRoomNotificationSettingsState(setNotificationSettingAction = AsyncAction.Failure(RuntimeException("error"))),
            aRoomNotificationSettingsState(restoreDefaultAction = AsyncAction.Loading),
            aRoomNotificationSettingsState(restoreDefaultAction = AsyncAction.Failure(RuntimeException("error"))),
            aRoomNotificationSettingsState(displayMentionsOnlyDisclaimer = true)
        )

    private fun aRoomNotificationSettingsState(
        isDefault: Boolean = true,
        setNotificationSettingAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
        restoreDefaultAction: AsyncAction<Unit> = AsyncAction.Uninitialized,
        displayMentionsOnlyDisclaimer: Boolean = false,
    ): RoomNotificationSettingsState {
        return RoomNotificationSettingsState(
            showUserDefinedSettingStyle = false,
            roomName = "Room 1",
            AsyncData.Success(aRoomNotificationSettings(
                mode = RoomNotificationMode.MUTE,
                isDefault = isDefault
            )),
            pendingRoomNotificationMode = null,
            pendingSetDefault = null,
            defaultRoomNotificationMode = RoomNotificationMode.ALL_MESSAGES,
            setNotificationSettingAction = setNotificationSettingAction,
            restoreDefaultAction = restoreDefaultAction,
            displayMentionsOnlyDisclaimer = displayMentionsOnlyDisclaimer,
            eventSink = { },
        )
    }
}
