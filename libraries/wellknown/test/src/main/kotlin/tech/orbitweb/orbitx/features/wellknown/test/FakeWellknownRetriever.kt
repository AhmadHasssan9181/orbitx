/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.wellknown.test

import tech.orbitweb.orbitx.libraries.wellknown.api.ElementWellKnown
import tech.orbitweb.orbitx.libraries.wellknown.api.WellknownRetriever
import tech.orbitweb.orbitx.libraries.wellknown.api.WellknownRetrieverResult
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeWellknownRetriever(
    private val getElementWellKnownResult: (String) -> WellknownRetrieverResult<ElementWellKnown> = { WellknownRetrieverResult.NotFound },
) : WellknownRetriever {
    override suspend fun getElementWellKnown(baseUrl: String): WellknownRetrieverResult<ElementWellKnown> = simulateLongTask {
        getElementWellKnownResult(baseUrl)
    }
}
