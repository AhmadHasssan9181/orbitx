/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.userprofile.impl

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.userprofile.api.UserProfileEntryPoint
import tech.orbitweb.orbitx.libraries.architecture.createNode

@ContributesBinding(AppScope::class)
class DefaultUserProfileEntryPoint : UserProfileEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        params: UserProfileEntryPoint.Params,
        callback: UserProfileEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<UserProfileFlowNode>(
            buildContext = buildContext,
            plugins = listOf(params, callback),
        )
    }
}
