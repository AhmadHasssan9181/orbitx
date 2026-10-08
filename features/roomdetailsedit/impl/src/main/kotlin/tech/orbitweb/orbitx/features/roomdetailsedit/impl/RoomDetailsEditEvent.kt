/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetailsedit.impl

import tech.orbitweb.orbitx.libraries.matrix.ui.media.AvatarAction

sealed interface RoomDetailsEditEvent {
    data class HandleAvatarAction(val action: AvatarAction) : RoomDetailsEditEvent
    data class UpdateRoomName(val name: String) : RoomDetailsEditEvent
    data class UpdateRoomTopic(val topic: String) : RoomDetailsEditEvent
    data object OnBackPress : RoomDetailsEditEvent
    data object Save : RoomDetailsEditEvent
    data object CloseDialog : RoomDetailsEditEvent
}
