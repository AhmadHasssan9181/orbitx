/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.impl.configureroom

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

data class CreateRoomConfig(
    val roomName: String? = null,
    val topic: String? = null,
    val avatarUri: String? = null,
    val invites: ImmutableList<MatrixUser> = persistentListOf(),
    val visibilityState: RoomVisibilityState = RoomVisibilityState.Private(JoinRuleItem.PrivateVisibility.Private),
    val parentSpace: SpaceRoom? = null,
)
