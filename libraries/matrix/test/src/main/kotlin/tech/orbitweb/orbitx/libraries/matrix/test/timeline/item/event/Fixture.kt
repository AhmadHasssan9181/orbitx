/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.timeline.item.event

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MembershipChange
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.RoomMembershipContent
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID

fun aRoomMembershipContent(
    userId: UserId = A_USER_ID,
    userDisplayName: String? = null,
    change: MembershipChange? = null,
    reason: String? = null,
) = RoomMembershipContent(
    userId = userId,
    userDisplayName = userDisplayName,
    change = change,
    reason = reason,
)
