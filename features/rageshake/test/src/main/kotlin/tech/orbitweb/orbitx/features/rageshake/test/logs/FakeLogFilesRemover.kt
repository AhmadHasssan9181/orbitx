/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.test.logs

import tech.orbitweb.orbitx.features.rageshake.api.logs.LogFilesRemover
import tech.orbitweb.orbitx.tests.testutils.lambda.LambdaOneParamRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import java.io.File

class FakeLogFilesRemover(
    val performLambda: LambdaOneParamRecorder<(File) -> Boolean, Unit> = lambdaRecorder<(File) -> Boolean, Unit> { },
) : LogFilesRemover {
    override suspend fun perform(predicate: (File) -> Boolean) {
        performLambda(predicate)
    }
}
