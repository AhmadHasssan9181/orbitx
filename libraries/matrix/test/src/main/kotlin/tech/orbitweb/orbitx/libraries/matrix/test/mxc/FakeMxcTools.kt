/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.mxc

import tech.orbitweb.orbitx.libraries.matrix.api.mxc.MxcTools
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeMxcTools(
    private val mxcUri2FilePathResult: (String) -> String? = { lambdaError() }
) : MxcTools {
    override fun mxcUri2FilePath(mxcUri: String): String? {
        return mxcUri2FilePathResult(mxcUri)
    }
}
