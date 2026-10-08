/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.space.api.SpaceEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesBinding(SessionScope::class)
class DefaultSpaceEntryPoint : SpaceEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        inputs: SpaceEntryPoint.Inputs,
        callback: SpaceEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<SpaceFlowNode>(
            buildContext = buildContext,
            plugins = listOf(inputs, callback),
        )
    }
}
