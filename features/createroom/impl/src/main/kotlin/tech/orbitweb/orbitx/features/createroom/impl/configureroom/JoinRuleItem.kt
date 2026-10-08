/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.impl.configureroom

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.persistentListOf
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.AllowRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule

/**
 * Join rule items to display in UI.
 */
@Immutable
sealed interface JoinRuleItem {
    sealed interface PrivateVisibility : JoinRuleItem {
        data object Private : PrivateVisibility
        data class Restricted(val parentSpaceId: RoomId) : PrivateVisibility
        data class AskToJoinRestricted(val parentSpaceId: RoomId) : PrivateVisibility
    }

    /**
     * Those join rule items that represent public visibility of the room/space.
     */
    @Immutable
    sealed interface PublicVisibility : JoinRuleItem {
        data object Public : PublicVisibility
        data object AskToJoin : PublicVisibility
    }

    /**
     * Transforms a [JoinRuleItem] option into a [JoinRule].
     */
    fun toJoinRule(): JoinRule = when (this) {
        PrivateVisibility.Private -> JoinRule.Invite
        is PrivateVisibility.Restricted -> JoinRule.Restricted(persistentListOf(AllowRule.RoomMembership(parentSpaceId)))
        is PrivateVisibility.AskToJoinRestricted -> JoinRule.KnockRestricted(persistentListOf(AllowRule.RoomMembership(parentSpaceId)))
        PublicVisibility.Public -> JoinRule.Public
        PublicVisibility.AskToJoin -> JoinRule.Knock
    }
}
