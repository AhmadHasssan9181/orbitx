/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomaliasresolver.impl

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.roomaliasesolver.api.RoomAliasResolverEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.createNode

@ContributesBinding(AppScope::class)
class DefaultRoomAliasResolverEntryPoint : RoomAliasResolverEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: RoomAliasResolverEntryPoint.Params,
        callback: RoomAliasResolverEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<RoomAliasResolverNode>(
            buildContext = buildContext,
            plugins = listOf(params, callback),
        )
    }
}
