/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.spaces

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.spaces.SpaceRoom
import tech.orbitweb.orbitx.libraries.previewutils.room.aSpaceRoom

open class HomeSpacesStateProvider : PreviewParameterProvider<HomeSpacesState> {
    override val values: Sequence<HomeSpacesState>
        get() = sequenceOf(
            aHomeSpacesState(
                spaceRooms = SpaceRoomProvider().values.toList(),
                seenSpaceInvites = setOf(
                    RoomId("!spaceId3:example.com"),
                ),
            ),
            aHomeSpacesState(
                space = CurrentSpace.Space(
                    spaceRoom = aSpaceRoom(roomId = RoomId("!mySpace:example.com"))
                ),
                spaceRooms = aListOfSpaceRooms(),
            ),
            aHomeSpacesState(
                space = CurrentSpace.Root,
                spaceRooms = emptyList(),
            ),
        )
}

internal fun aHomeSpacesState(
    space: CurrentSpace = CurrentSpace.Root,
    spaceRooms: List<SpaceRoom> = aListOfSpaceRooms(),
    seenSpaceInvites: Set<RoomId> = emptySet(),
    hideInvitesAvatar: Boolean = false,
    canExploreSpaces: Boolean = true,
    eventSink: (HomeSpacesEvents) -> Unit = {},
) = HomeSpacesState(
    space = space,
    spaceRooms = spaceRooms.toImmutableList(),
    seenSpaceInvites = seenSpaceInvites.toImmutableSet(),
    hideInvitesAvatar = hideInvitesAvatar,
    canExploreSpaces = canExploreSpaces,
    eventSink = eventSink,
)

fun aListOfSpaceRooms(): List<SpaceRoom> {
    return listOf(
        aSpaceRoom(roomId = RoomId("!spaceId0:example.com")),
        aSpaceRoom(roomId = RoomId("!spaceId1:example.com")),
        aSpaceRoom(roomId = RoomId("!spaceId2:example.com")),
    )
}
