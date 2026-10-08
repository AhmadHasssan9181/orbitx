/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl

import tech.orbitweb.orbitx.features.home.impl.roomlist.RoomListState
import tech.orbitweb.orbitx.features.home.impl.spacefilters.SpaceFiltersState
import tech.orbitweb.orbitx.features.home.impl.spaces.HomeSpacesState
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutState
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import kotlinx.collections.immutable.ImmutableList

data class HomeState(
    /**
     * The current user of this session, in case of multiple accounts, will contains 3 items, with the
     * current user in the middle.
     */
    val currentUserAndNeighbors: ImmutableList<MatrixUser>,
    val showAvatarIndicator: Boolean,
    val hasNetworkConnection: Boolean,
    val currentHomeNavigationBarItem: HomeNavigationBarItem,
    val roomListState: RoomListState,
    val homeSpacesState: HomeSpacesState,
    val snackbarMessage: SnackbarMessage?,
    val canReportBug: Boolean,
    val directLogoutState: DirectLogoutState,
    val eventSink: (HomeEvent) -> Unit,
) {
    val isBackHandlerEnabled = currentHomeNavigationBarItem != HomeNavigationBarItem.Chats || roomListState.spaceFiltersState is SpaceFiltersState.Selected
    val displayRoomListFilters = currentHomeNavigationBarItem == HomeNavigationBarItem.Chats && roomListState.displayFilters
}
