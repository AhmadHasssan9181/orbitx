/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.roomdirectory

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.matrix.rustcomponents.sdk.PublicRoomJoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomDescription
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories.aRustRoomDescription
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.roomdirectory.aRoomDescription

class RoomDescriptionMapperTest {
    @Test
    fun map() {
        assertThat(RoomDescriptionMapper().map(aRustRoomDescription())).isEqualTo(
            aRoomDescription(
                roomId = A_ROOM_ID,
                name = "name",
                topic = "topic",
                alias = A_ROOM_ALIAS,
                avatarUrl = "avatarUrl",
                joinRule = RoomDescription.JoinRule.PUBLIC,
                isWorldReadable = true,
                joinedMembers = 2L
            )
        )
    }

    @Test
    fun mapWithNullAlias() {
        assertThat(RoomDescriptionMapper().map(aRustRoomDescription().copy(alias = null)).alias).isNull()
    }

    @Test
    fun `map join rule`() {
        assertThat(PublicRoomJoinRule.PUBLIC.map()).isEqualTo(RoomDescription.JoinRule.PUBLIC)
        assertThat(PublicRoomJoinRule.KNOCK.map()).isEqualTo(RoomDescription.JoinRule.KNOCK)
        assertThat(null.map()).isEqualTo(RoomDescription.JoinRule.UNKNOWN)
    }
}
