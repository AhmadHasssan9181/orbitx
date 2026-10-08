/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl.addroom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.space.impl.di.SpaceFlowScope
import tech.orbitweb.orbitx.libraries.architecture.appyx.launchMolecule
import tech.orbitweb.orbitx.libraries.architecture.callback

@ContributesNode(SpaceFlowScope::class)
@AssistedInject
class AddRoomToSpaceNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    private val presenter: AddRoomToSpacePresenter,
) : Node(buildContext, plugins = plugins) {
    interface Callback : Plugin {
        fun onFinish()
    }

    private val callback: Callback = callback()
    private val stateFlow = launchMolecule { presenter.present() }

    @Composable
    override fun View(modifier: Modifier) {
        val state by stateFlow.collectAsState()
        AddRoomToSpaceView(
            state = state,
            onBackClick = callback::onFinish,
            onAddRoom = callback::onFinish,
            modifier = modifier
        )
    }
}
