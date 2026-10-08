/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.licenses.impl.list

import tech.orbitweb.orbitx.features.licenses.impl.LicensesProvider
import tech.orbitweb.orbitx.features.licenses.impl.model.DependencyLicenseItem
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeLicensesProvider(
    private val provideResult: () -> List<DependencyLicenseItem> = { lambdaError() }
) : LicensesProvider {
    override suspend fun provides(): List<DependencyLicenseItem> {
        return provideResult()
    }
}
