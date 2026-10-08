/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roommembermoderation.impl

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.roommembermoderation.api.ModerationActionState
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationEvents
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationPermissions
import tech.orbitweb.orbitx.features.roommembermoderation.api.RoomMemberModerationState
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

data class InternalRoomMemberModerationState(
    override val permissions: RoomMemberModerationPermissions,
    val selectedUser: MatrixUser?,
    val actions: ImmutableList<ModerationActionState>,
    val kickUserAsyncAction: AsyncAction<Unit>,
    val banUserAsyncAction: AsyncAction<Unit>,
    val unbanUserAsyncAction: AsyncAction<Unit>,
    override val eventSink: (RoomMemberModerationEvents) -> Unit,
) : RoomMemberModerationState {
    val canDisplayActions = actions.isNotEmpty()
}
