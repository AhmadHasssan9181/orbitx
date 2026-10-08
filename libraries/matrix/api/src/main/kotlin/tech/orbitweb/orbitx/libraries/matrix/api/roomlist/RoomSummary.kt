/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.roomlist

import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomInfo

data class RoomSummary(
    val info: RoomInfo,
    val latestEvent: LatestEventValue,
) {
    val roomId = info.id
    val latestEventTimestamp = when (latestEvent) {
        is LatestEventValue.None -> null
        is LatestEventValue.Local -> latestEvent.timestamp
        is LatestEventValue.Remote -> latestEvent.timestamp
        is LatestEventValue.RoomInvite -> latestEvent.timestamp
    }
    val isDm = info.isDm
}
