/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.reportroom.impl.fakes

import tech.orbitweb.orbitx.features.reportroom.impl.ReportRoom
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeReportRoom(
    var lambda: (RoomId, Boolean, String, Boolean) -> Result<Unit> = { _, _, _, _ -> lambdaError() }
) : ReportRoom {
    override suspend fun invoke(
        roomId: RoomId,
        shouldReport: Boolean,
        reason: String,
        shouldLeave: Boolean
    ): Result<Unit> = simulateLongTask {
        lambda(roomId, shouldReport, reason, shouldLeave)
    }
}
