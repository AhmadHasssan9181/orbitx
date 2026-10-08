/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.common.userlocation

import androidx.compose.runtime.Composable
import org.maplibre.compose.location.Location

data class UserLocationState(val location: Location?) {
    fun interface Factory {
        @Composable
        fun create(hasLocationPermission: Boolean): UserLocationState
    }
}
