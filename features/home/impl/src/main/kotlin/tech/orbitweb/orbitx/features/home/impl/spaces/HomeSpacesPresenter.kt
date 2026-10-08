/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.spaces

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.map
import tech.orbitweb.orbitx.features.invite.api.SeenInvitesStore
import tech.orbitweb.orbitx.libraries.architecture.Presenter
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.ui.safety.rememberHideInvitesAvatar

@Inject
class HomeSpacesPresenter(
    private val client: MatrixClient,
    private val seenInvitesStore: SeenInvitesStore,
) : Presenter<HomeSpacesState> {
    @Composable
    override fun present(): HomeSpacesState {
        val hideInvitesAvatar by client.rememberHideInvitesAvatar()
        val spaceRooms by remember {
            client.spaceService.topLevelSpacesFlow.map { it.toImmutableList() }
        }.collectAsState(persistentListOf())

        val seenSpaceInvites by remember {
            seenInvitesStore.seenRoomIds().map { it.toImmutableSet() }
        }.collectAsState(persistentSetOf())

        fun handleEvent(event: HomeSpacesEvents) {
            // when (event) { }
        }

        return HomeSpacesState(
            space = CurrentSpace.Root,
            spaceRooms = spaceRooms,
            seenSpaceInvites = seenSpaceInvites,
            hideInvitesAvatar = hideInvitesAvatar,
            // TODO enable once we can link to the screen to explore public spaces
            canExploreSpaces = false,
            eventSink = ::handleEvent,
        )
    }
}
