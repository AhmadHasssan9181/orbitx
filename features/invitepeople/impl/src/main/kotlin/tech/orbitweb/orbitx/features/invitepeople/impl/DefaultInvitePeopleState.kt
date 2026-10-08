/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.invitepeople.impl

import androidx.compose.foundation.text.input.TextFieldState
import tech.orbitweb.orbitx.features.invitepeople.api.InvitePeopleEvents
import tech.orbitweb.orbitx.features.invitepeople.api.InvitePeopleState
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.SearchBarResultState
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import kotlinx.collections.immutable.ImmutableList

data class DefaultInvitePeopleState(
    val room: AsyncData<Unit>,
    override val canInvite: Boolean,
    val searchQuery: TextFieldState,
    val showSearchLoader: Boolean,
    val searchResults: SearchBarResultState<ImmutableList<InvitableUser>>,
    val selectedUsers: ImmutableList<MatrixUser>,
    override val isSearchActive: Boolean,
    override val sendInvitesAction: AsyncAction<Unit>,
    override val createRoomFromDmAction: AsyncAction<RoomId>,
    val suggestions: ImmutableList<InvitableUser>,
    override val eventSink: (InvitePeopleEvents) -> Unit
) : InvitePeopleState
