/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.permalink

import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomAlias
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId

interface PermalinkBuilder {
    fun permalinkForUser(userId: UserId): Result<String>
    fun permalinkForRoomAlias(roomAlias: RoomAlias): Result<String>
}

sealed class PermalinkBuilderError : Throwable() {
    data object InvalidData : PermalinkBuilderError()
}
