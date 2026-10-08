/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.noop

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import im.vector.app.features.analytics.itf.VectorAnalyticsEvent
import im.vector.app.features.analytics.itf.VectorAnalyticsScreen
import im.vector.app.features.analytics.plan.SuperProperties
import im.vector.app.features.analytics.plan.UserProperties
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsLongRunningTransaction
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsSdkSpan
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService
import tech.orbitweb.orbitx.services.analytics.api.NoopAnalyticsSdkSpan
import tech.orbitweb.orbitx.services.analytics.api.NoopAnalyticsTransaction
import tech.orbitweb.orbitx.services.analyticsproviders.api.AnalyticsProvider
import tech.orbitweb.orbitx.services.analyticsproviders.api.AnalyticsTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NoopAnalyticsService : AnalyticsService {
    override fun getAvailableAnalyticsProviders(): Set<AnalyticsProvider> = emptySet()
    override val userConsentFlow: Flow<Boolean> = flowOf(false)
    override suspend fun setUserConsent(userConsent: Boolean) = Unit
    override val didAskUserConsentFlow: Flow<Boolean> = flowOf(true)
    override suspend fun setDidAskUserConsent() = Unit
    override val analyticsIdFlow: Flow<String> = flowOf("")
    override suspend fun setAnalyticsId(analyticsId: String) = Unit
    override fun capture(event: VectorAnalyticsEvent) = Unit
    override fun screen(screen: VectorAnalyticsScreen) = Unit
    override fun updateUserProperties(userProperties: UserProperties) = Unit
    override fun trackError(throwable: Throwable) = Unit
    override fun updateSuperProperties(updatedProperties: SuperProperties) = Unit
    override fun startTransaction(name: String, operation: String?, description: String?): AnalyticsTransaction = NoopAnalyticsTransaction
    override fun startLongRunningTransaction(
        longRunningTransaction: AnalyticsLongRunningTransaction,
        parentTransaction: AnalyticsTransaction?,
    ): AnalyticsTransaction = NoopAnalyticsTransaction
    override fun getLongRunningTransaction(longRunningTransaction: AnalyticsLongRunningTransaction): AnalyticsTransaction? = null
    override fun removeLongRunningTransaction(longRunningTransaction: AnalyticsLongRunningTransaction) = NoopAnalyticsTransaction

    override fun enterSdkSpan(name: String?, parentTraceId: String?): AnalyticsSdkSpan = NoopAnalyticsSdkSpan
}
