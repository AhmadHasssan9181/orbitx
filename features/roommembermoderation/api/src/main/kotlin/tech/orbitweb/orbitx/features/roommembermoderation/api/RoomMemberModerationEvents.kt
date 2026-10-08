/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roommembermoderation.api

import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser

interface RoomMemberModerationEvents {
    data class ShowActionsForUser(val user: MatrixUser) : RoomMemberModerationEvents
    data class ProcessAction(val action: ModerationAction, val targetUser: MatrixUser) : RoomMemberModerationEvents
}
