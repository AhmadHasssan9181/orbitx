/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.notificationsettings

import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationMode
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationSettings
import org.matrix.rustcomponents.sdk.RoomNotificationMode as RustRoomNotificationMode
import org.matrix.rustcomponents.sdk.RoomNotificationSettings as RustRoomNotificationSettings

object RoomNotificationSettingsMapper {
    fun map(roomNotificationSettings: RustRoomNotificationSettings): RoomNotificationSettings =
        RoomNotificationSettings(
            mode = mapMode(roomNotificationSettings.mode),
            isDefault = roomNotificationSettings.isDefault
        )

    fun mapMode(mode: RustRoomNotificationMode): RoomNotificationMode =
        when (mode) {
            RustRoomNotificationMode.ALL_MESSAGES -> RoomNotificationMode.ALL_MESSAGES
            RustRoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY -> RoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY
            RustRoomNotificationMode.MUTE -> RoomNotificationMode.MUTE
        }

    fun mapMode(mode: RoomNotificationMode): RustRoomNotificationMode =
        when (mode) {
            RoomNotificationMode.ALL_MESSAGES -> RustRoomNotificationMode.ALL_MESSAGES
            RoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY -> RustRoomNotificationMode.MENTIONS_AND_KEYWORDS_ONLY
            RoomNotificationMode.MUTE -> RustRoomNotificationMode.MUTE
        }
}
