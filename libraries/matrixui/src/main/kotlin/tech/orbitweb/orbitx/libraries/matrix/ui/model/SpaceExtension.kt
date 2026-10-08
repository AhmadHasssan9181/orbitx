/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import tech.orbitweb.orbitx.compound.tokens.generated.CompoundIcons
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoomVisibility
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

fun SpaceRoom.getAvatarData(size: AvatarSize) = AvatarData(
    id = roomId.value,
    name = displayName,
    url = avatarUrl,
    size = size,
)

val SpaceRoomVisibility.icon: ImageVector
    @Composable
    get() {
        return when (this) {
            SpaceRoomVisibility.Private -> CompoundIcons.LockSolid()
            SpaceRoomVisibility.Public -> CompoundIcons.Public()
            SpaceRoomVisibility.SpaceMembers -> CompoundIcons.Space()
        }
    }

val SpaceRoomVisibility.label: String
    @Composable
    @ReadOnlyComposable
    get() {
        return when (this) {
            SpaceRoomVisibility.Private -> stringResource(CommonStrings.common_private)
            SpaceRoomVisibility.Public -> stringResource(CommonStrings.common_public)
            SpaceRoomVisibility.SpaceMembers -> stringResource(CommonStrings.common_space_members)
        }
    }
