/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room.location

import tech.orbitweb.orbitx.libraries.matrix.api.room.location.LiveLocationException
import org.matrix.rustcomponents.sdk.LiveLocationException as RustLiveLocationException

fun RustLiveLocationException.map(): LiveLocationException {
    return when (this) {
        is RustLiveLocationException.Network -> LiveLocationException.Network()
        is RustLiveLocationException.NotLive -> LiveLocationException.NotLive()
        else -> LiveLocationException.Other(this)
    }
}
