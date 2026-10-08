/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.model

import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPermissions

data class MediaPermissions(
    val canRedactOwn: Boolean,
    val canRedactOther: Boolean,
) {
    companion object {
        val DEFAULT = MediaPermissions(
            canRedactOwn = false,
            canRedactOther = false,
        )
    }
}

fun RoomPermissions.mediaPermissions(): MediaPermissions {
    return MediaPermissions(
        canRedactOwn = canOwnUserRedactOwn(),
        canRedactOther = canOwnUserRedactOther(),
    )
}
