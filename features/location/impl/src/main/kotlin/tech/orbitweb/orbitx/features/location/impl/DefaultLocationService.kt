/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.features.location.api.BuildConfig
import tech.orbitweb.orbitx.features.location.api.LocationService

@ContributesBinding(AppScope::class)
class DefaultLocationService : LocationService {
    override fun isServiceAvailable(): Boolean {
        return BuildConfig.MAPTILER_API_KEY.isNotEmpty()
    }
}
