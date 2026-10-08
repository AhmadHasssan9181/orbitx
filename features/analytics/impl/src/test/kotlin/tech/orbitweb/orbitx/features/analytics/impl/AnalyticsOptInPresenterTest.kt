/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.analytics.impl

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.analytics.api.AnalyticsOptInEvents
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule

class AnalyticsOptInPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - enable`() = runTest {
        val analyticsService = FakeAnalyticsService(isEnabled = false)
        val presenter = AnalyticsOptInPresenter(
            aBuildMeta(),
            analyticsService
        )
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertThat(analyticsService.didAskUserConsentFlow.first()).isFalse()
            initialState.eventSink.invoke(AnalyticsOptInEvents.EnableAnalytics(true))
            assertThat(analyticsService.didAskUserConsentFlow.first()).isTrue()
            assertThat(analyticsService.userConsentFlow.first()).isTrue()
        }
    }

    @Test
    fun `present - not now`() = runTest {
        val analyticsService = FakeAnalyticsService(isEnabled = false)
        val presenter = AnalyticsOptInPresenter(
            aBuildMeta(),
            analyticsService
        )
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertThat(analyticsService.didAskUserConsentFlow.first()).isFalse()
            initialState.eventSink.invoke(AnalyticsOptInEvents.EnableAnalytics(false))
            assertThat(analyticsService.didAskUserConsentFlow.first()).isTrue()
            assertThat(analyticsService.userConsentFlow.first()).isFalse()
        }
    }
}
