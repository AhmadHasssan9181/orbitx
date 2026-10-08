/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.linknewdevice

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.LinkMobileHandler
import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.LinkMobileStep
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.simulateLongTask

class FakeLinkMobileHandler(
    private val startResult: () -> Unit = { lambdaError() },
) : LinkMobileHandler {
    private val mutableLinkMobileStep: MutableStateFlow<LinkMobileStep> = MutableStateFlow(LinkMobileStep.Uninitialized)
    override val linkMobileStep: StateFlow<LinkMobileStep>
        get() = mutableLinkMobileStep.asStateFlow()

    override suspend fun start() = simulateLongTask {
        startResult()
    }

    suspend fun emitStep(step: LinkMobileStep) {
        mutableLinkMobileStep.emit(step)
    }
}
