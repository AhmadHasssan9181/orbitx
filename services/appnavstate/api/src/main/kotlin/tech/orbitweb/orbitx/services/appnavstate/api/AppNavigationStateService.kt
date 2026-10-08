/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.appnavstate.api

import kotlinx.coroutines.flow.StateFlow
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId

/**
 * A service that tracks the navigation and foreground states of the app.
 */
interface AppNavigationStateService {
    val appNavigationState: StateFlow<AppNavigationState>

    fun onNavigateToSession(owner: String, sessionId: SessionId)
    fun onLeavingSession(owner: String)

    fun onNavigateToRoom(owner: String, roomId: RoomId)
    fun onLeavingRoom(owner: String)

    fun onNavigateToThread(owner: String, threadId: ThreadId)
    fun onLeavingThread(owner: String)
}
