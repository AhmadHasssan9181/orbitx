/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase

import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeFirebaseNewInstallationIdHandler(
    private val handleResult: (String) -> Unit = { lambdaError() }
) : FirebaseNewInstallationIdHandler {
    override suspend fun handle(installationId: String) {
        handleResult(installationId)
    }
}
