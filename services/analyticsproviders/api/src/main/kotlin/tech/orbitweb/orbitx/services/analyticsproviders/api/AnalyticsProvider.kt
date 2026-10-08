/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analyticsproviders.api

import tech.orbitweb.orbitx.services.analyticsproviders.api.trackers.AnalyticsTracker
import tech.orbitweb.orbitx.services.analyticsproviders.api.trackers.ErrorTracker

interface AnalyticsProvider : AnalyticsTracker, ErrorTracker {
    /**
     * User friendly name.
     */
    val name: String

    fun init()

    fun stop()

    fun startTransaction(name: String, operation: String? = null, description: String? = null): AnalyticsTransaction?
}
