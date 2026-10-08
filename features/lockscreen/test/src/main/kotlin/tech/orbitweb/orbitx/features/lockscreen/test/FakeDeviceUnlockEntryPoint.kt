/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.test

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import tech.orbitweb.orbitx.features.lockscreen.api.DeviceUnlockEntryPoint
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeDeviceUnlockEntryPoint : DeviceUnlockEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
    ): Node = lambdaError()

    override fun requestUnlock(callback: DeviceUnlockEntryPoint.Callback) = lambdaError()
}
