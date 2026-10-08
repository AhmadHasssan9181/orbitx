/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.core

import tech.orbitweb.orbitx.libraries.androidutils.metadata.isInDebug
import java.io.Serializable

@JvmInline
value class RoomAlias(val value: String) : Serializable {
    init {
        if (isInDebug && !MatrixPatterns.isRoomAlias(value)) {
            error("`$value` is not a valid room alias.\n Example room alias: `#room_alias:domain`.")
        }
    }

    override fun toString(): String = value
}
