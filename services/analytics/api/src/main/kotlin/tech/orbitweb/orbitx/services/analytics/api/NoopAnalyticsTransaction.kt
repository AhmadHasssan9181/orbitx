/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.api

import tech.orbitweb.orbitx.services.analyticsproviders.api.AnalyticsTransaction
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object NoopAnalyticsTransaction : AnalyticsTransaction {
    override val duration: Duration = 0.seconds
    override fun startChild(operation: String, description: String?): AnalyticsTransaction = NoopAnalyticsTransaction
    override fun putExtraData(key: String, value: String) {}
    override fun putIndexableData(key: String, value: String) {}
    override fun isFinished(): Boolean = true
    override fun traceId(): String? = null
    override fun attachError(throwable: Throwable) {}
    override fun finish() {}
}
