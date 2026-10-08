/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securityandprivacy.impl.editroomaddress

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias

/**
 * Returns the local part of the alias.
 */
fun RoomAlias.addressName(): String {
    return value.drop(1).split(":").first()
}

/**
 * Checks if the room alias matches the given server name.
 */
fun RoomAlias.matchesServer(serverName: String): Boolean {
    return value.split(":").last() == serverName
}
