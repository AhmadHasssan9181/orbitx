/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securityandprivacy.impl.manageauthorizedspaces

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

data class ManageAuthorizedSpacesState(
    val selectableSpaces: ImmutableSet<SpaceRoom>,
    val unknownSpaceIds: ImmutableList<RoomId>,
    val selectedIds: ImmutableList<RoomId>,
    val eventSink: (ManageAuthorizedSpacesEvent) -> Unit
) {
    val isDoneButtonEnabled = selectedIds.isNotEmpty()
}
