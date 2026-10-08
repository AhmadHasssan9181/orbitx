/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.analytics

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsSdkManager
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsSdkSpan
import org.matrix.rustcomponents.sdk.enableSentryLogging

@ContributesBinding(AppScope::class)
class RustAnalyticsSdkManager : AnalyticsSdkManager {
    override fun enableSdkAnalytics(enabled: Boolean) {
        enableSentryLogging(enabled)
    }

    override fun startSpan(name: String, parentTraceId: String?): AnalyticsSdkSpan {
        return RustAnalyticsSdkSpan(name = name, parentTraceId = parentTraceId)
    }

    override fun bridge(parentTraceId: String?): AnalyticsSdkSpan {
        // A bridge span has no name
        return RustAnalyticsSdkSpan(name = null, parentTraceId = parentTraceId)
    }
}
