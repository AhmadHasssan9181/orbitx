/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.textcomposer.mentions

import androidx.compose.runtime.Immutable
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.slashcommands.api.SlashCommandSuggestion

@Immutable
sealed interface ResolvedSuggestion {
    data object AtRoom : ResolvedSuggestion
    data class Member(val roomMember: RoomMember) : ResolvedSuggestion
    data class Alias(
        val roomAlias: RoomAlias,
        val roomId: RoomId,
        val roomName: String?,
        val roomAvatarUrl: String?,
    ) : ResolvedSuggestion {
        fun getAvatarData(size: AvatarSize) = AvatarData(
            id = roomId.value,
            name = roomName,
            url = roomAvatarUrl,
            size = size,
        )
    }

    data class Command(
        val command: SlashCommandSuggestion,
    ) : ResolvedSuggestion
}
