/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room

data class RoomNotificationSettings(
    val mode: RoomNotificationMode,
    val isDefault: Boolean,
)

enum class RoomNotificationMode {
    ALL_MESSAGES,
    MENTIONS_AND_KEYWORDS_ONLY,
    MUTE
}
