/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.preview

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.api.room.CurrentUserMembership
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomType
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.preview.RoomPreviewInfo
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories.aRustRoomPreviewInfo
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import org.junit.Test
import org.matrix.rustcomponents.sdk.Membership
import org.matrix.rustcomponents.sdk.JoinRule as RustJoinRule

class RoomPreviewInfoMapperTest {
    @Test
    fun `map should map values 1`() {
        assertThat(
            RoomPreviewInfoMapper.map(
                info = aRustRoomPreviewInfo(
                    membership = Membership.JOINED,
                )
            )
        ).isEqualTo(
            RoomPreviewInfo(
                roomId = A_ROOM_ID,
                canonicalAlias = A_ROOM_ALIAS,
                name = "name",
                topic = "topic",
                avatarUrl = "avatarUrl",
                numberOfJoinedMembers = 1L,
                roomType = RoomType.Room,
                isHistoryWorldReadable = true,
                membership = CurrentUserMembership.JOINED,
                joinRule = JoinRule.Public,
            )
        )
    }

    @Test
    fun `map should map values 2`() {
        assertThat(
            RoomPreviewInfoMapper.map(
                info = aRustRoomPreviewInfo(
                    canonicalAlias = null,
                    membership = Membership.JOINED,
                    joinRule = RustJoinRule.Knock,
                )
            )
        ).isEqualTo(
            RoomPreviewInfo(
                roomId = A_ROOM_ID,
                canonicalAlias = null,
                name = "name",
                topic = "topic",
                avatarUrl = "avatarUrl",
                numberOfJoinedMembers = 1L,
                roomType = RoomType.Room,
                isHistoryWorldReadable = true,
                membership = CurrentUserMembership.JOINED,
                joinRule = JoinRule.Knock,
            )
        )
    }
}
