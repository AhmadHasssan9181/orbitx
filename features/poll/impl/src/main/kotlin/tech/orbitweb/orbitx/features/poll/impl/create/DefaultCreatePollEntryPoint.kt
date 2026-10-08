/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.create

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.poll.api.create.CreatePollEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.createNode

@ContributesBinding(AppScope::class)
class DefaultCreatePollEntryPoint : CreatePollEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: CreatePollEntryPoint.Params,
    ): Node {
        return parentNode.createNode<CreatePollNode>(
            buildContext = buildContext,
            plugins = listOf(CreatePollNode.Inputs(timelineMode = params.timelineMode, mode = params.mode))
        )
    }
}
