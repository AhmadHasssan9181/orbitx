/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdirectory.impl

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.roomdirectory.api.RoomDirectoryEntryPoint
import tech.orbitweb.orbitx.features.roomdirectory.impl.root.RoomDirectoryNode
import tech.orbitweb.orbitx.libraries.architecture.createNode

@ContributesBinding(AppScope::class)
class DefaultRoomDirectoryEntryPoint : RoomDirectoryEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        callback: RoomDirectoryEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<RoomDirectoryNode>(buildContext, listOf(callback))
    }
}
