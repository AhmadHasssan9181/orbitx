/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.joinroom.impl

import tech.orbitweb.orbitx.features.joinroom.impl.di.KnockRoom
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomIdOrAlias
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeKnockRoom(
    var lambda: (RoomIdOrAlias, String, List<String>) -> Result<Unit> = { _, _, _ -> Result.success(Unit) }
) : KnockRoom {
    override suspend fun invoke(roomIdOrAlias: RoomIdOrAlias, message: String, serverNames: List<String>): Result<Unit> = simulateLongTask {
        lambda(roomIdOrAlias, message, serverNames)
    }
}
