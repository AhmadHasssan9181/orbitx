/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.messagecomposer

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.features.messages.impl.messagecomposer.suggestions.DefaultRoomAliasSuggestionsDataSource
import tech.orbitweb.orbitx.features.messages.impl.messagecomposer.suggestions.RoomAliasSuggestion
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomSummary
import tech.orbitweb.orbitx.libraries.matrix.test.roomlist.FakeDynamicRoomList
import tech.orbitweb.orbitx.libraries.matrix.test.roomlist.FakeRoomListService

class DefaultRoomAliasSuggestionsDataSourceTest {
    @Test
    fun `getAllRoomAliasSuggestions must emit a list of room alias suggestions`() = runTest {
        val roomList = FakeDynamicRoomList()
        val roomListService = FakeRoomListService(
            createRoomListLambda = { roomList }
        )
        val sut = DefaultRoomAliasSuggestionsDataSource(
            roomListService
        )
        val aRoomSummaryWithAnAlias = aRoomSummary(
            canonicalAlias = A_ROOM_ALIAS
        )
        sut.getAllRoomAliasSuggestions().test {
            assertThat(awaitItem()).isEmpty()
            roomList.summaries.emit(
                listOf(
                    aRoomSummary(roomId = A_ROOM_ID_2, canonicalAlias = null),
                    aRoomSummaryWithAnAlias,
                )
            )
            assertThat(awaitItem()).isEqualTo(
                listOf(
                    RoomAliasSuggestion(
                        roomAlias = A_ROOM_ALIAS,
                        roomId = aRoomSummaryWithAnAlias.roomId,
                        roomName = aRoomSummaryWithAnAlias.info.name,
                        roomAvatarUrl = aRoomSummaryWithAnAlias.info.avatarUrl
                    )
                )
            )
        }
    }
}
