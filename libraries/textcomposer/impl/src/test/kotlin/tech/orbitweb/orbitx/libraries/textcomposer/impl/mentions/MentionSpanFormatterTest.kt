/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.textcomposer.impl.mentions

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.core.toRoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ALIAS
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomMember
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomSummary
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.RoomMemberProfilesCache
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.RoomNamesCache
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.DefaultMentionSpanFormatter
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionType

class MentionSpanFormatterTest {
    private val roomMemberProfilesCache = RoomMemberProfilesCache()
    private val roomNamesCache = RoomNamesCache()
    private val formatter = DefaultMentionSpanFormatter(
        roomMemberProfilesCache = roomMemberProfilesCache,
        roomNamesCache = roomNamesCache,
    )

    @Test
    fun `formatDisplayText - formats user mention with empty cache`() = runTest {
        val userId = A_USER_ID
        val mentionType = MentionType.User(userId)
        val result = formatter.formatDisplayText(mentionType)
        assertThat(result.toString()).isEqualTo(userId.value)
    }

    @Test
    fun `formatDisplayText - formats user mention with filled cache`() = runTest {
        val userId = A_USER_ID
        val roomMember = aRoomMember(userId, displayName = "alice")
        roomMemberProfilesCache.replace(listOf(roomMember))
        val mentionType = MentionType.User(userId)
        val result = formatter.formatDisplayText(mentionType)
        assertThat(result.toString()).isEqualTo("@alice")
    }

    @Test
    fun `formatDisplayText - formats room mention with empty cache`() = runTest {
        val roomAlias = A_ROOM_ALIAS
        val mentionType = MentionType.Room(roomAlias.toRoomIdOrAlias())

        val result = formatter.formatDisplayText(mentionType)

        assertThat(result.toString()).isEqualTo(roomAlias.value)
    }

    @Test
    fun `formatDisplayText - formats room mention with filled cache`() = runTest {
        val roomAlias = A_ROOM_ALIAS
        val roomSummary = aRoomSummary(
            canonicalAlias = roomAlias,
            name = "my room"
        )
        roomNamesCache.replace(listOf(roomSummary))
        val mentionType = MentionType.Room(roomAlias.toRoomIdOrAlias())

        val result = formatter.formatDisplayText(mentionType)

        assertThat(result.toString()).isEqualTo("#my room")
    }

    @Test
    fun `formatDisplayText - formats room mention with room id and empty cache`() = runTest {
        val roomId = A_ROOM_ID
        val mentionType = MentionType.Room(roomId.toRoomIdOrAlias())

        val result = formatter.formatDisplayText(mentionType)

        assertThat(result.toString()).isEqualTo(roomId.value)
    }

    @Test
    fun `formatDisplayText - formats room mention with room id and filled cache`() = runTest {
        val roomId = A_ROOM_ID
        val roomSummary = aRoomSummary(
            roomId = roomId,
            name = "my room"
        )
        roomNamesCache.replace(listOf(roomSummary))

        val mentionType = MentionType.Room(roomId.toRoomIdOrAlias())
        val result = formatter.formatDisplayText(mentionType)

        assertThat(result.toString()).isEqualTo("#my room")
    }

    @Test
    fun `formatDisplayText - formats message mention with empty cache`() = runTest {
        val roomId = A_ROOM_ID
        val mentionType = MentionType.Message(roomId.toRoomIdOrAlias(), eventId = AN_EVENT_ID)

        val result = formatter.formatDisplayText(mentionType)

        assertThat(result.toString()).isEqualTo("💬 > ${roomId.value}")
    }

    @Test
    fun `formatDisplayText - formats message mention with filled cache`() = runTest {
        val roomId = A_ROOM_ID
        val roomSummary = aRoomSummary(
            roomId = roomId,
            name = "my room"
        )
        roomNamesCache.replace(listOf(roomSummary))

        val mentionType = MentionType.Message(roomId.toRoomIdOrAlias(), eventId = AN_EVENT_ID)

        val result = formatter.formatDisplayText(mentionType)

        assertThat(result.toString()).isEqualTo("💬 > #my room")
    }

    @Test
    fun `formatDisplayText - formats everyone mention`() = runTest {
        val mentionType = MentionType.Everyone

        val result = formatter.formatDisplayText(mentionType)

        assertThat(result.toString()).isEqualTo("@room")
    }
}
