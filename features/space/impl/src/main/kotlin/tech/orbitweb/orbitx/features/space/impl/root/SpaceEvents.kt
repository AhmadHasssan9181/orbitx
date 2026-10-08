/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl.root

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom

sealed interface SpaceEvents {
    data object LoadMore : SpaceEvents
    data class Join(val spaceRoom: SpaceRoom) : SpaceEvents
    data object ClearFailures : SpaceEvents
    data class AcceptInvite(val spaceRoom: SpaceRoom) : SpaceEvents
    data class DeclineInvite(val spaceRoom: SpaceRoom) : SpaceEvents

    data class ShowTopicViewer(val topic: String) : SpaceEvents
    data object HideTopicViewer : SpaceEvents

    // Manage mode events
    data object EnterManageMode : SpaceEvents
    data object ExitManageMode : SpaceEvents
    data class ToggleRoomSelection(val roomId: RoomId) : SpaceEvents
    data object ConfirmRoomRemoval : SpaceEvents
    data object RemoveSelectedRooms : SpaceEvents
    data object ClearRemoveAction : SpaceEvents
}
