/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.joinroom.impl

import tech.orbitweb.orbitx.features.joinroom.impl.di.ForgetRoom
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeForgetRoom(
    var lambda: (RoomId) -> Result<Unit> = { Result.success(Unit) }
) : ForgetRoom {
    override suspend fun invoke(roomId: RoomId) = simulateLongTask {
        lambda(roomId)
    }
}
