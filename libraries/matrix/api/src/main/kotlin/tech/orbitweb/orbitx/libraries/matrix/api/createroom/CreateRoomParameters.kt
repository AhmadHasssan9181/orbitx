/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.createroom

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.history.RoomHistoryVisibility
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomVisibility
import java.util.Optional

data class CreateRoomParameters(
    val name: String?,
    val topic: String? = null,
    val isEncrypted: Boolean,
    val isDirect: Boolean = false,
    val visibility: RoomVisibility,
    val preset: RoomPreset,
    val invite: List<UserId>? = null,
    val avatar: String? = null,
    val joinRuleOverride: JoinRule? = null,
    val historyVisibilityOverride: RoomHistoryVisibility? = null,
    val roomAliasName: Optional<String> = Optional.empty(),
    val isSpace: Boolean = false,
)
