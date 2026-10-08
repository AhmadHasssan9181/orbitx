/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.impl.addpeople

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.invitepeople.api.InvitePeoplePresenter
import tech.orbitweb.orbitx.features.invitepeople.api.InvitePeopleRenderer
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.callback
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

@ContributesNode(SessionScope::class)
@AssistedInject
class AddPeopleNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    invitePeoplePresenterFactory: InvitePeoplePresenter.Factory,
    private val invitePeopleRenderer: InvitePeopleRenderer,
) : Node(buildContext, plugins = plugins) {
    data class Inputs(
        val roomId: RoomId,
    ) : NodeInputs

    interface Callback : Plugin {
        fun onFinish()
    }

    private val callback: Callback = callback()
    private val roomId = inputs<Inputs>().roomId
    private val invitePeoplePresenter = invitePeoplePresenterFactory.create(
        joinedRoom = null,
        roomId = roomId,
    )

    @Composable
    override fun View(modifier: Modifier) {
        val state = invitePeoplePresenter.present()
        AddPeopleView(
            state = state,
            onFinish = callback::onFinish,
        ) {
            invitePeopleRenderer.Render(state, Modifier)
        }
    }
}
