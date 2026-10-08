/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.impl.configureroom

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import tech.orbitweb.orbitx.libraries.matrix.ui.media.AvatarAction
import tech.orbitweb.orbitx.libraries.matrix.ui.room.address.RoomAddressValidity
import tech.orbitweb.orbitx.libraries.permissions.api.PermissionsState

data class ConfigureRoomState(
    val isSpace: Boolean,
    val config: CreateRoomConfig,
    val avatarActions: ImmutableList<AvatarAction>,
    val createRoomAction: AsyncAction<RoomId>,
    val cameraPermissionState: PermissionsState,
    val roomAddressValidity: RoomAddressValidity,
    val homeserverName: String,
    val availableJoinRules: ImmutableList<JoinRuleItem>,
    val spaces: ImmutableList<SpaceRoom>,
    val eventSink: (ConfigureRoomEvents) -> Unit
) {
    val isValid: Boolean = config.roomName?.isNotEmpty() == true &&
        (config.visibilityState is RoomVisibilityState.Private || roomAddressValidity == RoomAddressValidity.Valid) &&
        config.visibilityState.joinRuleItem in availableJoinRules
}
