/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.troubleshoot.impl.history

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.troubleshoot.api.PushHistoryEntryPoint

@ContributesBinding(AppScope::class)
class DefaultPushHistoryEntryPoint : PushHistoryEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        callback: PushHistoryEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<PushHistoryNode>(buildContext, listOf(callback))
    }
}
