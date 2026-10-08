/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.impl.watchers

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.orbitweb.orbitx.libraries.di.annotations.AppCoroutineScope
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsLongRunningTransaction
import tech.orbitweb.orbitx.services.analytics.api.AnalyticsService
import tech.orbitweb.orbitx.services.analytics.api.cancelLongRunningTransaction
import tech.orbitweb.orbitx.services.analytics.api.finishLongRunningTransaction
import tech.orbitweb.orbitx.services.analytics.api.watchers.AnalyticsColdStartWatcher
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultAnalyticsColdStartWatcher(
    private val analyticsService: AnalyticsService,
    @AppCoroutineScope private val appCoroutineScope: CoroutineScope,
) : AnalyticsColdStartWatcher {
    private val isColdStart = AtomicBoolean(true)

    override fun start() {
        analyticsService.userConsentFlow
            .onEach { hasConsent ->
                if (hasConsent) {
                    if (isColdStart.get()) {
                        Timber.d("Starting cold start check")
                        analyticsService.startLongRunningTransaction(AnalyticsLongRunningTransaction.ColdStart)
                    } else {
                        error("The app is no longer in a cold start state")
                    }
                }
            }
            .catch { Timber.w(it.message) }
            .launchIn(appCoroutineScope)
    }

    override fun whenLoggingIn() {
        if (isColdStart.getAndSet(false)) {
            analyticsService.cancelLongRunningTransaction(AnalyticsLongRunningTransaction.ColdStart)
            Timber.d("Canceled cold start check: user is logging in")
        }
    }

    override fun onRoomListVisible() {
        if (isColdStart.getAndSet(false)) {
            analyticsService.finishLongRunningTransaction(AnalyticsLongRunningTransaction.ColdStart) {
                Timber.d("Room list is visible, finishing cold start check after ${it.duration}")
            }
        }
    }
}
