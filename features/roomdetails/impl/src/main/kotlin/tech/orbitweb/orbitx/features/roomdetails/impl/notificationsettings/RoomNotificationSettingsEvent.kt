/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.impl.notificationsettings

import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationMode

sealed interface RoomNotificationSettingsEvent {
    data class ChangeRoomNotificationMode(val mode: RoomNotificationMode) : RoomNotificationSettingsEvent
    data class SetNotificationMode(val isDefault: Boolean) : RoomNotificationSettingsEvent
    data object DeleteCustomNotification : RoomNotificationSettingsEvent
    data object ClearSetNotificationError : RoomNotificationSettingsEvent
    data object ClearRestoreDefaultError : RoomNotificationSettingsEvent
}
