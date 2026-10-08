/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.mediaviewer.api.MediaGalleryEntryPoint
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.gallery.root.MediaGalleryFlowNode

@ContributesBinding(AppScope::class)
class DefaultMediaGalleryEntryPoint : MediaGalleryEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        callback: MediaGalleryEntryPoint.Callback,
    ): Node {
        return parentNode.createNode<MediaGalleryFlowNode>(
            buildContext = buildContext,
            plugins = listOf(callback),
        )
    }
}
