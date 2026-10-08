/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.appnavstate.api

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId

fun NavigationState.currentSessionId(): SessionId? {
    return when (this) {
        NavigationState.Root -> null
        is NavigationState.Session -> sessionId
        is NavigationState.Room -> parentSession.sessionId
        is NavigationState.Thread -> parentRoom.parentSession.sessionId
    }
}

fun NavigationState.currentRoomId(): RoomId? {
    return when (this) {
        NavigationState.Root -> null
        is NavigationState.Session -> null
        is NavigationState.Room -> roomId
        is NavigationState.Thread -> parentRoom.roomId
    }
}

fun NavigationState.currentThreadId(): ThreadId? {
    return when (this) {
        NavigationState.Root -> null
        is NavigationState.Session -> null
        is NavigationState.Room -> null
        is NavigationState.Thread -> threadId
    }
}
