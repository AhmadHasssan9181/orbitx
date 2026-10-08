/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.test.analytics

import tech.orbitweb.orbitx.services.analytics.api.AnalyticsSdkManager
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsSdkSpan
import tech.orbitweb.orbitx.services.analytics.api.NoopAnalyticsSdkSpan
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeAnalyticsSdkManager(
    private val enableSdkAnalyticsLambda: ((Boolean) -> Unit) = { lambdaError() },
) : AnalyticsSdkManager {
    override fun enableSdkAnalytics(enabled: Boolean) {
        enableSdkAnalyticsLambda(enabled)
    }

    override fun startSpan(name: String, parentTraceId: String?): AnalyticsSdkSpan = NoopAnalyticsSdkSpan
    override fun bridge(parentTraceId: String?): AnalyticsSdkSpan = NoopAnalyticsSdkSpan
}
