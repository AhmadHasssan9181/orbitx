/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invite.impl.declineandblock

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.invite.api.InviteData
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.di.SessionScope

@ContributesNode(SessionScope::class)
@AssistedInject
class DeclineAndBlockNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: DeclineAndBlockPresenter.Factory,
) : Node(buildContext, plugins = plugins) {
    data class Inputs(val inviteData: InviteData) : NodeInputs

    private val inviteData = inputs<Inputs>().inviteData
    private val presenter = presenterFactory.create(inviteData)

    @Composable
    override fun View(modifier: Modifier) {
        val state = presenter.present()
        DeclineAndBlockView(
            state = state,
            onBackClick = ::navigateUp,
            modifier = modifier
        )
    }
}
