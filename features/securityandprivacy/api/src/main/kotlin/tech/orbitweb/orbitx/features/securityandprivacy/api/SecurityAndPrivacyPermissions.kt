/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securityandprivacy.api

import tech.orbitweb.orbitx.libraries.matrix.api.room.StateEventType
import tech.orbitweb.orbitx.libraries.matrix.api.room.join.JoinRule
import tech.orbitweb.orbitx.libraries.matrix.api.room.powerlevels.RoomPermissions

data class SecurityAndPrivacyPermissions(
    val canChangeRoomAccess: Boolean,
    val canChangeHistoryVisibility: Boolean,
    val canChangeEncryption: Boolean,
    val canChangeRoomVisibility: Boolean,
) {
    fun hasAny(isSpace: Boolean, joinRule: JoinRule?): Boolean {
        val canChangeRoomVisibility = when (joinRule) {
            is JoinRule.Public,
            is JoinRule.Knock,
            is JoinRule.KnockRestricted -> canChangeRoomVisibility
            else -> false
        }
        return if (isSpace) {
            canChangeRoomAccess || canChangeRoomVisibility
        } else {
            canChangeRoomAccess || canChangeRoomVisibility || canChangeHistoryVisibility || canChangeEncryption
        }
    }

    companion object {
        val DEFAULT = SecurityAndPrivacyPermissions(
            canChangeRoomAccess = false,
            canChangeHistoryVisibility = false,
            canChangeEncryption = false,
            canChangeRoomVisibility = false,
        )
    }
}

fun RoomPermissions.securityAndPrivacyPermissions(): SecurityAndPrivacyPermissions {
    return SecurityAndPrivacyPermissions(
        canChangeRoomAccess = canOwnUserSendState(StateEventType.RoomJoinRules),
        canChangeHistoryVisibility = canOwnUserSendState(StateEventType.RoomHistoryVisibility),
        canChangeEncryption = canOwnUserSendState(StateEventType.RoomEncryption),
        canChangeRoomVisibility = canOwnUserSendState(StateEventType.RoomCanonicalAlias),
    )
}
