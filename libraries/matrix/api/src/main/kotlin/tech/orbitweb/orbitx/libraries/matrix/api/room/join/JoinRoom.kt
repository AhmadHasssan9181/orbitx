/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.room.join

import im.vector.app.features.analytics.plan.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias

interface JoinRoom {
    suspend operator fun invoke(
        roomIdOrAlias: RoomIdOrAlias,
        serverNames: List<String>,
        trigger: JoinedRoom.Trigger,
    ): Result<Unit>

    sealed class Failures : Exception() {
        data object UnauthorizedJoin : Failures()
    }
}
