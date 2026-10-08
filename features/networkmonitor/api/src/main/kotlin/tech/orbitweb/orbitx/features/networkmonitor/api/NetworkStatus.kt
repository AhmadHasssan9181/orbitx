/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.networkmonitor.api

/**
 * Network connectivity status of the device.
 *
 * **Note:** this is *network* connectivity status, not *internet* connectivity status.
 */
enum class NetworkStatus {
    /**
     * The device is connected to a network.
     */
    Connected,

    /**
     * The device is not connected to any networks.
     */
    Disconnected
}
