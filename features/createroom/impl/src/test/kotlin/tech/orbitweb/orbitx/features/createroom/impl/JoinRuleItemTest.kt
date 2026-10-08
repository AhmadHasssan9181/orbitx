/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.impl

import com.google.common.truth.Truth.assertThat
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import tech.orbitweb.orbitx.features.createroom.impl.configureroom.JoinRuleItem
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.AllowRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.test.A_ROOM_ID

class JoinRuleItemTest {
    @Test
    fun `toJoinRule works as expected`() {
        assertThat(JoinRuleItem.PrivateVisibility.Private.toJoinRule()).isEqualTo(JoinRule.Invite)
        assertThat(JoinRuleItem.PublicVisibility.Public.toJoinRule()).isEqualTo(JoinRule.Public)
        assertThat(JoinRuleItem.PublicVisibility.AskToJoin.toJoinRule()).isEqualTo(JoinRule.Knock)
        assertThat(JoinRuleItem.PrivateVisibility.Restricted(A_ROOM_ID).toJoinRule())
            .isEqualTo(JoinRule.Restricted(persistentListOf(AllowRule.RoomMembership(A_ROOM_ID))))
        assertThat(JoinRuleItem.PrivateVisibility.AskToJoinRestricted(A_ROOM_ID).toJoinRule())
            .isEqualTo(JoinRule.KnockRestricted(persistentListOf(AllowRule.RoomMembership(A_ROOM_ID))))
    }
}
