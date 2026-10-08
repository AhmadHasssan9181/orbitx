/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

import tech.orbitweb.orbitx.libraries.pushproviders.api.Distributor
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeRegisterUnifiedPushUseCase(
    private val result: (Distributor, String) -> Result<Unit> = { _, _ -> lambdaError() }
) : RegisterUnifiedPushUseCase {
    override suspend fun execute(distributor: Distributor, clientSecret: String): Result<Unit> {
        return result(distributor, clientSecret)
    }
}
