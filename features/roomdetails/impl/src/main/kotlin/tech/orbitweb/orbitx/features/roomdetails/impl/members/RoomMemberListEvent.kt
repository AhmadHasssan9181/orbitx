/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdetails.impl.members

import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember

sealed interface RoomMemberListEvent {
    data class ChangeSelectedSection(val section: SelectedSection) : RoomMemberListEvent
    data class RoomMemberSelected(val roomMember: RoomMember) : RoomMemberListEvent
}
