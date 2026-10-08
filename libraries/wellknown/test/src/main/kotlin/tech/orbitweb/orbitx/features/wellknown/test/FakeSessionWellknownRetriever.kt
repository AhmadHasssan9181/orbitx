/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.wellknown.test

import tech.orbitweb.orbitx.libraries.wellknown.api.ElementWellKnown
import tech.orbitweb.orbitx.libraries.wellknown.api.SessionWellknownRetriever
import tech.orbitweb.orbitx.libraries.wellknown.api.WellknownRetrieverResult
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeSessionWellknownRetriever(
    private val getElementWellKnownResult: () -> WellknownRetrieverResult<ElementWellKnown> = { WellknownRetrieverResult.NotFound },
) : SessionWellknownRetriever {
    override suspend fun getElementWellKnown(): WellknownRetrieverResult<ElementWellKnown> = simulateLongTask {
        getElementWellKnownResult()
    }
}
