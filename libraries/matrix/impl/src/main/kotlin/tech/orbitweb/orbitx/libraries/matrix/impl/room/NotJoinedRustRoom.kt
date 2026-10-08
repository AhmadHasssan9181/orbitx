/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room

import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.core.SessionId
import tech.orbitweb.orbitx.libraries.matrix.api.room.NotJoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.room.RoomMembershipDetails
import tech.orbitweb.orbitx.libraries.matrix.api.room.preview.RoomPreviewInfo
import tech.orbitweb.orbitx.libraries.matrix.impl.room.member.RoomMemberMapper

class NotJoinedRustRoom(
    private val sessionId: SessionId,
    override val localRoom: RustBaseRoom?,
    override val previewInfo: RoomPreviewInfo,
) : NotJoinedRoom {
    override suspend fun membershipDetails(): Result<RoomMembershipDetails?> = runCatchingExceptions {
        val room = localRoom?.innerRoom ?: return@runCatchingExceptions null
        val (ownMember, senderInfo) = room.memberWithSenderInfo(sessionId.value)
        RoomMembershipDetails(
            currentUserMember = RoomMemberMapper.map(ownMember),
            senderMember = senderInfo?.let { RoomMemberMapper.map(it) },
        )
    }

    override fun close() {
        localRoom?.close()
    }
}
