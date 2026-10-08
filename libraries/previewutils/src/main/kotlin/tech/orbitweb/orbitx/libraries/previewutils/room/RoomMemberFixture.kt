/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.previewutils.room

import kotlinx.collections.immutable.persistentListOf
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_ALICE
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_BOB
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_CAROL
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_DAVID
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_EVE
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_JUSTIN
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_MALLORY
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_SUSIE
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_VICTOR
import tech.orbitweb.orbitx.libraries.designsystem.preview.USER_NAME_WALTER
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMember
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMembershipState

fun aRoomMember(
    userId: UserId = UserId("@alice:server.org"),
    displayName: String? = null,
    avatarUrl: String? = null,
    membership: RoomMembershipState = RoomMembershipState.JOIN,
    isNameAmbiguous: Boolean = false,
    powerLevel: Long = 0L,
    isIgnored: Boolean = false,
    role: RoomMember.Role = RoomMember.Role.User,
    membershipChangeReason: String? = null,
    isServiceMember: Boolean = false,
) = RoomMember(
    userId = userId,
    displayName = displayName,
    avatarUrl = avatarUrl,
    membership = membership,
    isNameAmbiguous = isNameAmbiguous,
    powerLevel = powerLevel,
    isIgnored = isIgnored,
    role = role,
    membershipChangeReason = membershipChangeReason,
    isServiceMember = isServiceMember,
)

fun aRoomMemberList() = persistentListOf(
    anAlice(),
    aBob(),
    aRoomMember(UserId("@carol:server.org"), USER_NAME_CAROL),
    aRoomMember(UserId("@david:server.org"), USER_NAME_DAVID),
    aRoomMember(UserId("@eve:server.org"), USER_NAME_EVE),
    aRoomMember(UserId("@justin:server.org"), USER_NAME_JUSTIN),
    aRoomMember(UserId("@mallory:server.org"), USER_NAME_MALLORY),
    aRoomMember(UserId("@susie:server.org"), USER_NAME_SUSIE),
    aVictor(),
    aWalter(),
)

fun anAlice() = aRoomMember(UserId("@alice:server.org"), USER_NAME_ALICE, role = RoomMember.Role.Admin)
fun aBob() = aRoomMember(UserId("@bob:server.org"), USER_NAME_BOB, role = RoomMember.Role.Moderator)

fun aVictor() = aRoomMember(
    UserId("@victor:server.org"),
    USER_NAME_VICTOR,
    membership = RoomMembershipState.INVITE
)

fun aWalter() = aRoomMember(
    UserId("@walter:server.org"),
    USER_NAME_WALTER,
    membership = RoomMembershipState.INVITE
)
