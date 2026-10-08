/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.spaces

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.LeaveSpaceHandle
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.LeaveSpaceRoom
import tech.orbitweb.orbitx.libraries.matrix.test.A_SPACE_ID
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeLeaveSpaceHandle(
    override val id: RoomId = A_SPACE_ID,
    private val roomsResult: () -> Result<List<LeaveSpaceRoom>> = { lambdaError() },
    private val leaveResult: (List<RoomId>) -> Result<Unit> = { lambdaError() },
    private val closeResult: () -> Unit = { lambdaError() },
) : LeaveSpaceHandle {
    override suspend fun rooms(): Result<List<LeaveSpaceRoom>> = simulateLongTask {
        roomsResult()
    }

    override suspend fun leave(roomIds: List<RoomId>): Result<Unit> = simulateLongTask {
        leaveResult(roomIds)
    }

    override fun close() {
        return closeResult()
    }
}
