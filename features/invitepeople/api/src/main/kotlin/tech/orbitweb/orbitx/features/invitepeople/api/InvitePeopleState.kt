/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invitepeople.api

import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId

interface InvitePeopleState {
    val canInvite: Boolean
    val isSearchActive: Boolean
    val sendInvitesAction: AsyncAction<Unit>
    val createRoomFromDmAction: AsyncAction<RoomId>
    val eventSink: (InvitePeopleEvents) -> Unit
}
