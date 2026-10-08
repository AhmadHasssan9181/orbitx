/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.joinroom.impl.di

import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

interface CancelKnockRoom {
    suspend operator fun invoke(roomId: RoomId): Result<Unit>
}

@ContributesBinding(SessionScope::class)
class DefaultCancelKnockRoom(private val client: MatrixClient) : CancelKnockRoom {
    override suspend fun invoke(roomId: RoomId): Result<Unit> {
        return client
            .getRoom(roomId)
            ?.use { it.leave() }
            ?: Result.failure(IllegalStateException("No pending room found"))
    }
}
