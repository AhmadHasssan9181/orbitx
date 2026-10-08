/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.roomdirectory.impl.root.model

import tech.orbitweb.orbitx.features.roomdirectory.api.RoomDescription
import tech.orbitweb.orbitx.libraries.matrix.api.roomdirectory.RoomDescription as MatrixRoomDescription

fun MatrixRoomDescription.toFeatureModel(): RoomDescription {
    return RoomDescription(
        roomId = roomId,
        name = name,
        alias = alias,
        topic = topic,
        avatarUrl = avatarUrl,
        numberOfMembers = numberOfMembers,
        joinRule = when (joinRule) {
            MatrixRoomDescription.JoinRule.PUBLIC -> RoomDescription.JoinRule.PUBLIC
            MatrixRoomDescription.JoinRule.KNOCK -> RoomDescription.JoinRule.KNOCK
            MatrixRoomDescription.JoinRule.RESTRICTED -> RoomDescription.JoinRule.RESTRICTED
            MatrixRoomDescription.JoinRule.KNOCK_RESTRICTED -> RoomDescription.JoinRule.KNOCK_RESTRICTED
            MatrixRoomDescription.JoinRule.INVITE -> RoomDescription.JoinRule.INVITE
            MatrixRoomDescription.JoinRule.UNKNOWN -> RoomDescription.JoinRule.UNKNOWN
        }
    )
}
