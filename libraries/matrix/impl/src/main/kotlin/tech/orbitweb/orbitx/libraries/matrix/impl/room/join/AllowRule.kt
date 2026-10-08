/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.join

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.AllowRule
import org.matrix.rustcomponents.sdk.AllowRule as RustAllowRule

fun RustAllowRule.map(): AllowRule {
    return when (this) {
        is RustAllowRule.RoomMembership -> AllowRule.RoomMembership(RoomId(roomId))
        is RustAllowRule.Custom -> AllowRule.Custom(json)
    }
}

fun AllowRule.map(): RustAllowRule {
    return when (this) {
        is AllowRule.RoomMembership -> RustAllowRule.RoomMembership(roomId.value)
        is AllowRule.Custom -> RustAllowRule.Custom(json)
    }
}
