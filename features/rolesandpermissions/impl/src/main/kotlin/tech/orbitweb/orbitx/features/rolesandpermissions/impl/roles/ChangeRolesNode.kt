/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.roles

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.first
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.features.rolesandpermissions.api.ChangeRoomMemberRolesListType
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.appyx.launchMolecule
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.core.bool.orFalse
import tech.orbitweb.orbitx.libraries.di.RoomScope
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember

@ContributesNode(RoomScope::class)
@AssistedInject
class ChangeRolesNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: ChangeRolesPresenter.Factory,
) : Node(buildContext, plugins = plugins) {
    data class Inputs(
        val listType: ChangeRoomMemberRolesListType,
    ) : NodeInputs

    private val inputs: Inputs = inputs()
    private val presenter = presenterFactory.create(inputs.listType.toRoomMemberRole())
    private val stateFlow = launchMolecule { presenter.present() }

    suspend fun waitForCompletion(): Boolean {
        val successState = stateFlow.first { it.savingState.isSuccess() }
        return successState.savingState.dataOrNull().orFalse()
    }

    @Composable
    override fun View(modifier: Modifier) {
        val state by stateFlow.collectAsState()
        ChangeRolesView(
            state = state,
            modifier = modifier,
        )
    }
}

internal fun ChangeRoomMemberRolesListType.toRoomMemberRole() = when (this) {
    ChangeRoomMemberRolesListType.Admins -> RoomMember.Role.Admin
    ChangeRoomMemberRolesListType.Moderators -> RoomMember.Role.Moderator
    ChangeRoomMemberRolesListType.SelectNewOwnersWhenLeaving -> RoomMember.Role.Owner(isCreator = false)
}
