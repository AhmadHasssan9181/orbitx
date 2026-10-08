/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.viewfolder.impl.folder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.viewfolder.impl.model.Item
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.inputs

@ContributesNode(AppScope::class)
@AssistedInject
class ViewFolderNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: ViewFolderPresenter.Factory,
) : Node(buildContext, plugins = plugins) {
    data class Inputs(
        val canGoUp: Boolean,
        val path: String,
    ) : NodeInputs

    interface Callback : Plugin {
        fun onBackClick()
        fun navigateToItem(item: Item)
    }

    private val callback: Callback = callback()
    private val inputs: Inputs = inputs()

    private val presenter = presenterFactory.create(
        canGoUp = inputs.canGoUp,
        path = inputs.path,
    )

    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()
        ViewFolderView(
            state = state,
            modifier = modifier,
            onNavigateTo = callback::navigateToItem,
            onBackClick = callback::onBackClick,
        )
    }
}
