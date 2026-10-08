/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.textcomposer.impl.mentions

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.toRoomIdOrAlias
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkData
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.permalink.FakePermalinkParser
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionType
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class IntentionalMentionSpanProviderTest : RobolectricTest() {
    @JvmField @Rule
    val warmUpRule = WarmUpRule()

    private val permalinkParser = FakePermalinkParser()
    private val mentionSpanProvider = aMentionSpanProvider(permalinkParser)

    @Test
    fun `getting mention span for a user returns a MentionSpan of type USER`() {
        permalinkParser.givenResult(PermalinkData.UserLink(A_USER_ID))
        val mentionSpan = mentionSpanProvider.getMentionSpanFor("@me:matrix.org", "https://matrix.to/#/${A_USER_ID.value}")
        assertThat(mentionSpan?.type).isInstanceOf(MentionType.User::class.java)
        val userType = mentionSpan?.type as MentionType.User
        assertThat(userType.userId).isEqualTo(A_USER_ID)
    }

    @Test
    fun `getting mention span for everyone in the room returns a MentionSpan of type EVERYONE`() {
        permalinkParser.givenResult(PermalinkData.FallbackLink(uri = Uri.EMPTY))
        val mentionSpan = mentionSpanProvider.getMentionSpanFor("@room", "#")
        assertThat(mentionSpan?.type).isEqualTo(MentionType.Everyone)
    }

    @Test
    fun `getting mention span for a room returns a MentionSpan of type ROOM`() {
        permalinkParser.givenResult(
            PermalinkData.RoomLink(
                roomIdOrAlias = RoomAlias("#room:matrix.org").toRoomIdOrAlias(),
            )
        )
        val mentionSpan = mentionSpanProvider.getMentionSpanFor("#room:matrix.org", "https://matrix.to/#/#room:matrix.org")
        assertThat(mentionSpan?.type).isInstanceOf(MentionType.Room::class.java)
        val roomType = mentionSpan?.type as MentionType.Room
        assertThat(roomType.roomIdOrAlias).isEqualTo(RoomAlias("#room:matrix.org").toRoomIdOrAlias())
    }
}
