/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rolesandpermissions.impl.roles

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.rolesandpermissions.api.ChangeRoomMemberRolesEntryPoint
import tech.orbitweb.orbitx.features.rolesandpermissions.api.ChangeRoomMemberRolesListType
import tech.orbitweb.orbitx.libraries.architecture.createNode
import tech.orbitweb.orbitx.libraries.di.SessionScope
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom

@ContributesBinding(SessionScope::class)
class DefaultChangeRoomMemberRolesEntyPoint : ChangeRoomMemberRolesEntryPoint {
    override fun createNode(
        parentNode: Node,
        buildContext: BuildContext,
        room: JoinedRoom,
        listType: ChangeRoomMemberRolesListType,
    ): Node {
        return parentNode.createNode<ChangeRoomMemberRolesRootNode>(
            buildContext = buildContext,
            plugins = listOf(
                ChangeRoomMemberRolesRootNode.Inputs(joinedRoom = room, listType = listType),
            )
        )
    }
}
