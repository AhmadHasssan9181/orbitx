/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.appnavstate.test

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.services.appnavstate.api.AppNavigationState
import tech.orbitweb.orbitx.services.appnavstate.api.NavigationState

const val A_SESSION_OWNER = "aSessionOwner"
const val A_ROOM_OWNER = "aRoomOwner"
const val A_THREAD_OWNER = "aThreadOwner"

fun aNavigationState(
    sessionId: SessionId? = null,
    roomId: RoomId? = null,
    threadId: ThreadId? = null,
): NavigationState {
    if (sessionId == null) {
        return NavigationState.Root
    }
    val session = NavigationState.Session(A_SESSION_OWNER, sessionId)
    if (roomId == null) {
        return session
    }
    val room = NavigationState.Room(A_ROOM_OWNER, roomId, session)
    if (threadId == null) {
        return room
    }
    return NavigationState.Thread(A_THREAD_OWNER, threadId, room)
}

fun anAppNavigationState(
    navigationState: NavigationState = aNavigationState(),
    isInForeground: Boolean = true,
) = AppNavigationState(
    navigationState = navigationState,
    isInForeground = isInForeground,
)
