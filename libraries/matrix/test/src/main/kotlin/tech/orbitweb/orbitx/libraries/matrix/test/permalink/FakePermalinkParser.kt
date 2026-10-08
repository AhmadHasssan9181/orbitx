/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.permalink

import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkData
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkParser
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakePermalinkParser(
    private var result: (String) -> PermalinkData = { lambdaError() }
) : PermalinkParser {
    fun givenResult(result: PermalinkData) {
        this.result = { result }
    }

    override fun parse(uriString: String): PermalinkData {
        return result(uriString)
    }
}
