/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.analytics

import im.vector.app.features.analytics.plan.JoinedRoom
import kotlinx.coroutines.flow.first
import tech.orbitweb.orbitx.libraries.matrix.api.room.BaseRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomInfo

private fun Long.toAnalyticsRoomSize(): JoinedRoom.RoomSize {
    return when (this) {
        0L,
        1L -> JoinedRoom.RoomSize.One
        2L -> JoinedRoom.RoomSize.Two
        in 3..10 -> JoinedRoom.RoomSize.ThreeToTen
        in 11..100 -> JoinedRoom.RoomSize.ElevenToOneHundred
        in 101..1000 -> JoinedRoom.RoomSize.OneHundredAndOneToAThousand
        else -> JoinedRoom.RoomSize.MoreThanAThousand
    }
}

suspend fun BaseRoom.toAnalyticsJoinedRoom(trigger: JoinedRoom.Trigger?): JoinedRoom {
    val roomInfo = roomInfoFlow.first()
    return roomInfo.toAnalyticsJoinedRoom(trigger)
}

fun RoomInfo.toAnalyticsJoinedRoom(trigger: JoinedRoom.Trigger?): JoinedRoom {
    return JoinedRoom(
        isDM = isDm,
        isSpace = isSpace,
        roomSize = joinedMembersCount.toAnalyticsRoomSize(),
        trigger = trigger
    )
}
