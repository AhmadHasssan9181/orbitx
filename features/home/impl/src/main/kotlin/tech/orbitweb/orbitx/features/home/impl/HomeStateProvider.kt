/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.home.impl.roomlist.RoomListState
import tech.orbitweb.orbitx.features.home.impl.roomlist.RoomListStateProvider
import tech.orbitweb.orbitx.features.home.impl.roomlist.aRoomListState
import tech.orbitweb.orbitx.features.home.impl.roomlist.aRoomsContentState
import tech.orbitweb.orbitx.features.home.impl.roomlist.generateRoomListRoomSummaryList
import tech.orbitweb.orbitx.features.home.impl.spaces.HomeSpacesState
import tech.orbitweb.orbitx.features.home.impl.spaces.aHomeSpacesState
import tech.orbitweb.orbitx.features.logout.api.direct.DirectLogoutState
import tech.orbitweb.orbitx.features.logout.api.direct.aDirectLogoutState
import tech.orbitweb.orbitx.libraries.designsystem.utils.snackbar.SnackbarMessage
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings

open class HomeStateProvider : PreviewParameterProvider<HomeState> {
    override val values: Sequence<HomeState>
        get() = sequenceOf(
            aHomeState(),
            aHomeState(hasNetworkConnection = false),
            aHomeState(snackbarMessage = SnackbarMessage(CommonStrings.common_verification_complete)),
            aHomeState(
                roomListState = aRoomListState(
                    // Add more rooms to see the blur effect under the NavigationBar
                    contentState = aRoomsContentState(
                        summaries = generateRoomListRoomSummaryList(),
                    )
                ),
                // For the bottom nav bar to be visible in the preview, the user must be member of at least one space
                homeSpacesState = aHomeSpacesState(),
            ),
            aHomeState(
                currentHomeNavigationBarItem = HomeNavigationBarItem.Spaces,
            ),
        ) + RoomListStateProvider().values.map {
            aHomeState(roomListState = it)
        } + aHomeState(
            currentHomeNavigationBarItem = HomeNavigationBarItem.Spaces,
            homeSpacesState = aHomeSpacesState(
                spaceRooms = emptyList(),
            ),
        )
}

internal fun aHomeState(
    matrixUser: MatrixUser = MatrixUser(userId = UserId("@id:domain"), displayName = "User#1"),
    currentUserAndNeighbors: List<MatrixUser> = listOf(matrixUser),
    showAvatarIndicator: Boolean = false,
    hasNetworkConnection: Boolean = true,
    snackbarMessage: SnackbarMessage? = null,
    currentHomeNavigationBarItem: HomeNavigationBarItem = HomeNavigationBarItem.Chats,
    roomListState: RoomListState = aRoomListState(),
    homeSpacesState: HomeSpacesState = aHomeSpacesState(),
    canReportBug: Boolean = true,
    directLogoutState: DirectLogoutState = aDirectLogoutState(),
    eventSink: (HomeEvent) -> Unit = {}
) = HomeState(
    currentUserAndNeighbors = currentUserAndNeighbors.toImmutableList(),
    showAvatarIndicator = showAvatarIndicator,
    hasNetworkConnection = hasNetworkConnection,
    snackbarMessage = snackbarMessage,
    canReportBug = canReportBug,
    directLogoutState = directLogoutState,
    currentHomeNavigationBarItem = currentHomeNavigationBarItem,
    roomListState = roomListState,
    homeSpacesState = homeSpacesState,
    eventSink = eventSink,
)
