/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test

import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClientProvider
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId

class FakeMatrixClientProvider(
    var getClient: (SessionId) -> Result<MatrixClient> = { Result.success(FakeMatrixClient()) }
) : MatrixClientProvider {
    override suspend fun getOrRestore(sessionId: SessionId): Result<MatrixClient> = getClient(sessionId)

    override fun getOrNull(sessionId: SessionId): MatrixClient? = getClient(sessionId).getOrNull()
}
