/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.rolesandpermissions.api.RolesAndPermissionsEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.di.RoomScope

@ContributesBinding(RoomScope::class)
class DefaultRolesAndPermissionsEntryPoint : RolesAndPermissionsEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        callback: RolesAndPermissionsEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<RolesAndPermissionsFlowNode>(buildContext, listOf(callback))
    }
}
