/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.location.impl.common.actions

import tech.orbitweb.orbitx.features.location.api.Location

class FakeLocationActions(
    private var isLocationEnabled: Boolean = true,
) : LocationActions {
    var sharedLocation: Location? = null
        private set

    var sharedLabel: String? = null
        private set

    var openSettingsInvocationsCount = 0
        private set

    var openLocationSettingsInvocationsCount = 0
        private set

    override fun share(location: Location, label: String?) {
        sharedLocation = location
        sharedLabel = label
    }

    override fun openAppSettings() {
        openSettingsInvocationsCount++
    }

    override fun isLocationEnabled(): Boolean {
        return isLocationEnabled
    }

    override fun openLocationSettings() {
        openLocationSettingsInvocationsCount++
    }

    fun givenLocationEnabled(enabled: Boolean) {
        isLocationEnabled = enabled
    }
}
