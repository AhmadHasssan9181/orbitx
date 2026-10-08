/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl.settings

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

data class SpaceSettingsState(
    val roomId: RoomId,
    val name: String,
    val canonicalAlias: RoomAlias?,
    val avatarUrl: String?,
    val memberCount: Long,
    val canEditDetails: Boolean,
    val showRolesAndPermissions: Boolean,
    val showSecurityAndPrivacy: Boolean,
    val eventSink: (SpaceSettingsEvents) -> Unit
)
