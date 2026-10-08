/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary

import com.google.common.truth.Truth.assertThat
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.anAggregatedReaction
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMembersState
import tech.orbitweb.orbitx.libraries.matrix.test.AN_AVATAR_URL
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeBaseRoom
import tech.orbitweb.orbitx.libraries.matrix.test.room.aRoomMember
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.test

class ReactionSummaryPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    private val aggregatedReaction = anAggregatedReaction(userId = A_USER_ID, key = "👍", isHighlighted = true)
    private val roomMember = aRoomMember(userId = A_USER_ID, avatarUrl = AN_AVATAR_URL, displayName = A_USER_NAME)
    private val summaryEvent = ReactionSummaryEvent.ShowReactionSummary(AN_EVENT_ID, listOf(aggregatedReaction), aggregatedReaction.key)
    private val room = FakeBaseRoom().apply {
        givenRoomMembersState(RoomMembersState.Ready(persistentListOf(roomMember)))
    }
    private val presenter = ReactionSummaryPresenter(room)

    @Test
    fun `present - handle showing and hiding the reaction summary`() = runTest {
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.target).isNull()

            initialState.eventSink(summaryEvent)
            assertThat(awaitItem().target).isNotNull()

            initialState.eventSink(ReactionSummaryEvent.Clear)
            assertThat(awaitItem().target).isNull()
        }
    }

    @Test
    fun `present - handle reaction summary content and avatars populated`() = runTest {
        presenter.test {
            val initialState = awaitItem()
            assertThat(initialState.target).isNull()

            initialState.eventSink(summaryEvent)
            val reactions = awaitItem().target?.reactions
            assertThat(reactions?.count()).isEqualTo(1)
            assertThat(reactions?.first()?.key).isEqualTo("👍")
            assertThat(reactions?.first()?.senders?.first()?.senderId).isEqualTo(A_USER_ID)
            assertThat(reactions?.first()?.senders?.first()?.user?.userId).isEqualTo(A_USER_ID)
            assertThat(reactions?.first()?.senders?.first()?.user?.avatarUrl).isEqualTo(AN_AVATAR_URL)
            assertThat(reactions?.first()?.senders?.first()?.user?.displayName).isEqualTo(A_USER_NAME)
        }
    }
}
