/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.test

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.features.location.api.ShowLocationEntryPoint
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeShowLocationEntryPoint : ShowLocationEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        inputs: ShowLocationEntryPoint.Inputs,
    ): Node = lambdaError()
}
