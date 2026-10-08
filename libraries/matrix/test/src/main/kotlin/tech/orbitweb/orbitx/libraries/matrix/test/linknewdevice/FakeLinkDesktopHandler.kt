/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.linknewdevice

import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.LinkDesktopHandler
import tech.orbitweb.orbitx.libraries.matrix.api.linknewdevice.LinkDesktopStep
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeLinkDesktopHandler(
    private val handleScannedQrCodeResult: (ByteArray) -> Unit = { lambdaError() },
) : LinkDesktopHandler {
    private val mutableLinkDesktopStep: MutableStateFlow<LinkDesktopStep> = MutableStateFlow(LinkDesktopStep.Uninitialized)
    override val linkDesktopStep: StateFlow<LinkDesktopStep>
        get() = mutableLinkDesktopStep.asStateFlow()

    override suspend fun handleScannedQrCode(data: ByteArray) {
        handleScannedQrCodeResult(data)
    }

    suspend fun emitStep(step: LinkDesktopStep) {
        mutableLinkDesktopStep.emit(step)
    }
}
