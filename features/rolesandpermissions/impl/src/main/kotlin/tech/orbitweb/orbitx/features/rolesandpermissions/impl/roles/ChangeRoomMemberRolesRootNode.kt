/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.roles

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.model.permanent.PermanentNavModel
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.plugin.Plugin
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import tech.orbitweb.orbitx.annotations.ContributesNode
import tech.orbitweb.orbitx.appnav.di.RoomGraphFactory
import tech.orbitweb.orbitx.features.rolesandpermissions.api.ChangeRoomMemberRolesEntryPoint
import tech.orbitweb.orbitx.features.rolesandpermissions.api.ChangeRoomMemberRolesListType
import tech.orbitweb.orbitx.libraries.architecture.NodeInputs
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.architecture.inputs
import tech.orbitweb.orbitx.libraries.di.DependencyInjectionGraphOwner
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import kotlinx.parcelize.Parcelize

@ContributesNode(SessionScope::class)
@AssistedInject
class ChangeRoomMemberRolesRootNode(
    @Assisted buildContext: BuildContext,
    @Assisted plugins: List<Plugin>,
    roomGraphFactory: RoomGraphFactory,
) : ParentNode<ChangeRoomMemberRolesRootNode.NavTarget>(
    navModel = PermanentNavModel(
        navTargets = setOf(NavTarget),
        savedStateMap = buildContext.savedStateMap,
    ),
    buildContext = buildContext,
    plugins = plugins,
), DependencyInjectionGraphOwner, ChangeRoomMemberRolesEntryPoint.NodeProxy {
    @Parcelize object NavTarget : Parcelable

    data class Inputs(
        val joinedRoom: JoinedRoom,
        val listType: ChangeRoomMemberRolesListType,
    ) : NodeInputs

    private val inputs = inputs<Inputs>()

    override val graph = roomGraphFactory.create(inputs.joinedRoom)

    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node {
        return createNode<ChangeRolesNode>(
            buildContext = buildContext,
            plugins = listOf(ChangeRolesNode.Inputs(listType = inputs.listType)),
        )
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(modifier = modifier, navModel = navModel)
    }

    override val roomId: RoomId = inputs.joinedRoom.roomId

    override suspend fun waitForCompletion(): Boolean {
        return waitForChildAttached<ChangeRolesNode>().waitForCompletion()
    }
}
