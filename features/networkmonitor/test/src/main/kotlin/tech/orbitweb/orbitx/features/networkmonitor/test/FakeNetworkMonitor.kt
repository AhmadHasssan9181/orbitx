/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.networkmonitor.test

import tech.orbitweb.orbitx.features.networkmonitor.api.NetworkMonitor
import tech.orbitweb.orbitx.features.networkmonitor.api.NetworkStatus
import kotlinx.coroutines.flow.MutableStateFlow

class FakeNetworkMonitor(
    initialStatus: NetworkStatus = NetworkStatus.Connected,
) : NetworkMonitor {
    override val connectivity = MutableStateFlow(initialStatus)
    override val isNetworkBlocked = MutableStateFlow(false)
    override val isInAirGappedEnvironment = MutableStateFlow(false)

    fun givenNetworkBlocked(isBlocked: Boolean) {
        isNetworkBlocked.value = isBlocked
    }

    fun givenIsInAirGappedEnvironment(isInAirGapped: Boolean) {
        isInAirGappedEnvironment.value = isInAirGapped
    }
}
