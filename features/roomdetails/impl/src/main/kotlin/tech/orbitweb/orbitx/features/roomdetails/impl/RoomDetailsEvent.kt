/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.impl

sealed interface RoomDetailsEvent {
    data class LeaveRoom(val needsConfirmation: Boolean) : RoomDetailsEvent
    data object MuteNotification : RoomDetailsEvent
    data object UnmuteNotification : RoomDetailsEvent
    data class CopyToClipboard(val text: String) : RoomDetailsEvent
    data class SetFavorite(val isFavorite: Boolean) : RoomDetailsEvent
    data object MarkAsRead : RoomDetailsEvent
    data object MarkAsUnread : RoomDetailsEvent
}
