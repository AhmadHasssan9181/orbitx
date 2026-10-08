/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.verifysession.test

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.features.verifysession.api.OutgoingVerificationEntryPoint
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeOutgoingVerificationEntryPoint : OutgoingVerificationEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: OutgoingVerificationEntryPoint.Params,
        callback: OutgoingVerificationEntryPoint.Callback,
    ): Node = lambdaError()
}
