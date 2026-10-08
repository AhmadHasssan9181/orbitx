/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.space.impl.settings

import tech.orbitweb.orbitx.features.roomdetailsedit.api.RoomDetailsEditPermissions
import tech.orbitweb.orbitx.features.roomdetailsedit.api.roomDetailsEditPermissions
import tech.orbitweb.orbitx.features.securityandprivacy.api.SecurityAndPrivacyPermissions
import tech.orbitweb.orbitx.features.securityandprivacy.api.securityAndPrivacyPermissions
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPermissions
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.canEditRolesAndPermissions

data class SpaceSettingsPermissions(
    val editDetailsPermissions: RoomDetailsEditPermissions,
    val canEditRolesAndPermissions: Boolean,
    val securityAndPrivacyPermissions: SecurityAndPrivacyPermissions,
) {
    fun hasAny(joinRule: JoinRule?): Boolean {
        return editDetailsPermissions.hasAny ||
            canEditRolesAndPermissions ||
            securityAndPrivacyPermissions.hasAny(isSpace = true, joinRule = joinRule)
    }

    companion object {
        val DEFAULT = SpaceSettingsPermissions(
            editDetailsPermissions = RoomDetailsEditPermissions.DEFAULT,
            canEditRolesAndPermissions = false,
            securityAndPrivacyPermissions = SecurityAndPrivacyPermissions.DEFAULT,
        )
    }
}

fun RoomPermissions.spaceSettingsPermissions(): SpaceSettingsPermissions {
    return SpaceSettingsPermissions(
        editDetailsPermissions = roomDetailsEditPermissions(),
        canEditRolesAndPermissions = canEditRolesAndPermissions(),
        securityAndPrivacyPermissions = securityAndPrivacyPermissions(),
    )
}
