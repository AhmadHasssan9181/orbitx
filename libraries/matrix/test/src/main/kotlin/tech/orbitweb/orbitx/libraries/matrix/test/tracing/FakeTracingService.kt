/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.tracing

import tech.orbitweb.orbitx.libraries.matrix.api.tracing.TracingService
import tech.orbitweb.orbitx.libraries.matrix.api.tracing.WriteToFilesConfiguration
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import timber.log.Timber

class FakeTracingService(
    private val createTimberTreeResult: (String) -> Timber.Tree = { lambdaError() },
    private val updateWriteToFilesConfigurationResult: (WriteToFilesConfiguration) -> Unit = { lambdaError() }
) : TracingService {
    override fun createTimberTree(target: String): Timber.Tree {
        return createTimberTreeResult(target)
    }

    override fun updateWriteToFilesConfiguration(config: WriteToFilesConfiguration) {
        updateWriteToFilesConfigurationResult(config)
    }
}
