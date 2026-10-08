/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.lockscreen.impl

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.lockscreen.api.DeviceUnlockEntryPoint
import tech.orbitweb.orbitx.features.lockscreen.impl.device.DeviceUnlockCallbackHolder
import tech.orbitweb.orbitx.features.lockscreen.impl.device.DeviceUnlockNode
import tech.orbitweb.orbitx.libraries.architecture.createNode

@ContributesBinding(AppScope::class)
class DefaultDeviceUnlockEntryPoint(
    private val deviceUnlockCallbackHolder: DeviceUnlockCallbackHolder,
) : DeviceUnlockEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
    ): Node {
        return parentNode.createNode<DeviceUnlockNode>(
            buildContext = buildContext,
        )
    }

    override fun requestUnlock(callback: DeviceUnlockEntryPoint.Callback) {
        deviceUnlockCallbackHolder.requestUnlock(callback)
    }
}
