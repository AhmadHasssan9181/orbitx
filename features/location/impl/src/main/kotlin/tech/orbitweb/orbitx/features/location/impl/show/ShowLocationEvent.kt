/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.show

import tech.orbitweb.orbitx.features.location.api.Location

sealed interface ShowLocationEvent {
    data class Share(val location: Location) : ShowLocationEvent
    data class TrackMyLocation(val enabled: Boolean) : ShowLocationEvent
    data object DismissDialog : ShowLocationEvent
    data object RequestPermissions : ShowLocationEvent
    data object OpenAppSettings : ShowLocationEvent
    data object OpenLocationSettings : ShowLocationEvent
    data object StopLocationSharing : ShowLocationEvent
}
