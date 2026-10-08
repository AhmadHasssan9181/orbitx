/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.test

import tech.orbitweb.orbitx.features.location.api.LocationService

class FakeLocationService(
    private val isServiceAvailable: Boolean = false,
) : LocationService {
    override fun isServiceAvailable() = isServiceAvailable
}
