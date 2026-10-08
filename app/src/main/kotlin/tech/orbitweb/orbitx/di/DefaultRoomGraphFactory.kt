/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.di

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.appnav.di.RoomGraphFactory
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom

@ContributesBinding(SessionScope::class)
class DefaultRoomGraphFactory(
    private val sessionGraph: SessionGraph,
) : RoomGraphFactory {
    override fun create(room: JoinedRoom): Any {
        return sessionGraph.roomGraphFactory
            .create(room, room)
    }
}
