/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.knockrequests.api

import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPermissions

data class KnockRequestPermissions(
    val canAccept: Boolean,
    val canDecline: Boolean,
    val canBan: Boolean,
) {
    val hasAny = canAccept || canDecline || canBan

    companion object {
        val DEFAULT = KnockRequestPermissions(
            canAccept = false,
            canDecline = false,
            canBan = false,
        )
    }
}

fun RoomPermissions.knockRequestPermissions(): KnockRequestPermissions {
    return KnockRequestPermissions(
        canAccept = canOwnUserInvite(),
        canDecline = canOwnUserKick(),
        canBan = canOwnUserBan(),
    )
}
