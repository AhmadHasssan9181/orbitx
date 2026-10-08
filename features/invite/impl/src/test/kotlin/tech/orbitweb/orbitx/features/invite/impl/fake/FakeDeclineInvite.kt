/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl.fake

import tech.orbitweb.orbitx.features.invite.impl.DeclineInvite
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeDeclineInvite(
    private val lambda: (RoomId, Boolean, Boolean, String?) -> Result<RoomId> = { _, _, _, _ -> lambdaError() },
) : DeclineInvite {
    override suspend fun invoke(roomId: RoomId, blockUser: Boolean, reportRoom: Boolean, reportReason: String?): Result<RoomId> = simulateLongTask {
        lambda(roomId, blockUser, reportRoom, reportReason)
    }
}
