/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.roomselect.impl

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.roomselect.api.RoomSelectEntryPoint

@ContributesBinding(SessionScope::class)
class DefaultRoomSelectEntryPoint : RoomSelectEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: RoomSelectEntryPoint.Params,
        callback: RoomSelectEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<RoomSelectNode>(
            buildContext = buildContext,
            plugins = listOf(
                RoomSelectNode.Inputs(
                    mode = params.mode,
                    maxNumberOfRooms = params.maxNumberOfRooms,
                ),
                callback,
            )
        )
    }
}
