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

/**
 * Can represent the current global app navigation state.
 * @param owner mostly a Node identifier associated with the state.
 * We are using the owner parameter to check when calling onLeaving methods is still using the same owner than his companion onNavigate.
 * Why this is needed : for now we rely on lifecycle methods of the node, which are async.
 * If you navigate quickly between nodes, onCreate of the new node is called before onDestroy of the previous node.
 * So we assume if we don't get the same owner, we can skip the onLeaving action as we already replaced it.
 */
sealed class NavigationState(open val owner: String) {
    data object Root : NavigationState("ROOT")

    data class Session(
        override val owner: String,
        val sessionId: SessionId,
    ) : NavigationState(owner)

    data class Room(
        override val owner: String,
        val roomId: RoomId,
        val parentSession: Session,
    ) : NavigationState(owner)

    data class Thread(
        override val owner: String,
        val threadId: ThreadId,
        val parentRoom: Room,
    ) : NavigationState(owner)
}
