/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.notifications.edit

import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomNotificationMode

sealed interface EditDefaultNotificationSettingStateEvents {
    data class SetNotificationMode(val mode: RoomNotificationMode) : EditDefaultNotificationSettingStateEvents
    data object ClearError : EditDefaultNotificationSettingStateEvents
}
