/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roommembermoderation.impl

import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationEvents

sealed interface InternalRoomMemberModerationEvents : RoomMemberModerationEvents {
    data class DoKickUser(val reason: String) : InternalRoomMemberModerationEvents
    data class DoBanUser(val reason: String) : InternalRoomMemberModerationEvents
    data class DoUnbanUser(val reason: String) : InternalRoomMemberModerationEvents
    data object Reset : InternalRoomMemberModerationEvents
}
