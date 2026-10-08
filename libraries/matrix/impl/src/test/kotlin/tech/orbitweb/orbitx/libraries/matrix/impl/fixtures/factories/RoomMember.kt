/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories

import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import org.matrix.rustcomponents.sdk.MembershipState
import org.matrix.rustcomponents.sdk.PowerLevel
import org.matrix.rustcomponents.sdk.RoomMember
import uniffi.matrix_sdk.RoomMemberRole

internal fun aRustRoomMember(
    userId: UserId,
    displayName: String? = null,
    avatarUrl: String? = null,
    membership: MembershipState = MembershipState.Join,
    isNameAmbiguous: Boolean = false,
    powerLevel: PowerLevel = PowerLevel.Value(0L),
    isIgnored: Boolean = false,
    role: RoomMemberRole = RoomMemberRole.USER,
    membershipChangeReason: String? = null,
    isServiceMember: Boolean = false,
) = RoomMember(
    userId = userId.value,
    displayName = displayName,
    avatarUrl = avatarUrl,
    membership = membership,
    isNameAmbiguous = isNameAmbiguous,
    powerLevel = powerLevel,
    isIgnored = isIgnored,
    suggestedRoleForPowerLevel = role,
    membershipChangeReason = membershipChangeReason,
    isServiceMember = isServiceMember,
)
