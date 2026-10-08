/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.login.impl.web

import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeWebClientUrlForAuthenticationRetriever(
    private val retrieveLambda: suspend (homeServerUrl: String) -> String = { lambdaError() }
) : WebClientUrlForAuthenticationRetriever {
    override suspend fun retrieve(homeServerUrl: String): String {
        return retrieveLambda(homeServerUrl)
    }
}
