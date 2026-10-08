/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.analytics.api.preferences

import tech.orbitweb.orbitx.features.analytics.api.AnalyticsOptInEvents

data class AnalyticsPreferencesState(
    val applicationName: String,
    val isEnabled: Boolean,
    val policyUrl: String,
    val eventSink: (AnalyticsOptInEvents) -> Unit,
)
