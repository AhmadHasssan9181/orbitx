/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.leaveroom.api

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

interface LeaveRoomEvent {
    data class LeaveRoom(val roomId: RoomId, val needsConfirmation: Boolean) : LeaveRoomEvent
}
