/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.live.service

import tech.orbitweb.orbitx.features.location.api.Location

interface LiveLocationReceiver {
    suspend fun onLocationUpdate(location: Location)
    suspend fun onUnrecoverableError()
}
