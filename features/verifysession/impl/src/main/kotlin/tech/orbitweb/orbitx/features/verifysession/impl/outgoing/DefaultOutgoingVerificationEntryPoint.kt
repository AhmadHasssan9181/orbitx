/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.verifysession.impl.outgoing

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.verifysession.api.OutgoingVerificationEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.createNode

@ContributesBinding(AppScope::class)
class DefaultOutgoingVerificationEntryPoint : OutgoingVerificationEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: OutgoingVerificationEntryPoint.Params,
        callback: OutgoingVerificationEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<OutgoingVerificationNode>(buildContext, listOf(params, callback))
    }
}
