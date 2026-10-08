/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.createroom.impl.configureroom

import java.util.Optional

sealed interface RoomVisibilityState {
    val joinRuleItem: JoinRuleItem
    data class Private(override val joinRuleItem: JoinRuleItem.PrivateVisibility) : RoomVisibilityState

    data class Public(
        val roomAddress: RoomAddress,
        override val joinRuleItem: JoinRuleItem.PublicVisibility,
    ) : RoomVisibilityState

    fun roomAddress(): Optional<String> {
        return when (this) {
            is Private -> Optional.empty()
            is Public -> Optional.of(roomAddress.value)
        }
    }
}
