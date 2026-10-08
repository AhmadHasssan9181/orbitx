/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securityandprivacy.impl.manageauthorizedspaces

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

sealed interface ManageAuthorizedSpacesEvent {
    data object Cancel : ManageAuthorizedSpacesEvent
    data object Done : ManageAuthorizedSpacesEvent
    data class ToggleSpace(val roomId: RoomId) : ManageAuthorizedSpacesEvent
}
