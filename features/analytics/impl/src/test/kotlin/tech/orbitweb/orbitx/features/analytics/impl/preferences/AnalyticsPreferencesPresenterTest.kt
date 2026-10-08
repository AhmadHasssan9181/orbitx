/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.analytics.impl.preferences

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.appconfig.AnalyticsConfig
import tech.orbitweb.orbitx.features.analytics.api.AnalyticsOptInEvents
import tech.orbitweb.orbitx.libraries.matrix.test.core.aBuildMeta
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule

class AnalyticsPreferencesPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state available`() = runTest {
        val presenter = AnalyticsPreferencesPresenter(
            FakeAnalyticsService(isEnabled = true, didAskUserConsent = true),
            aBuildMeta()
        )
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            skipItems(1)
            val initialState = awaitItem()
            assertThat(initialState.isEnabled).isTrue()
            assertThat(initialState.policyUrl).isEqualTo(AnalyticsConfig.POLICY_LINK)
        }
    }

    @Test
    fun `present - initial state not available`() = runTest {
        val presenter = AnalyticsPreferencesPresenter(
            FakeAnalyticsService(isEnabled = false, didAskUserConsent = false),
            aBuildMeta()
        )
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertThat(initialState.isEnabled).isFalse()
        }
    }

    @Test
    fun `present - enable and disable`() = runTest {
        val presenter = AnalyticsPreferencesPresenter(
            FakeAnalyticsService(isEnabled = true, didAskUserConsent = true),
            aBuildMeta()
        )
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            skipItems(1)
            val initialState = awaitItem()
            assertThat(initialState.isEnabled).isTrue()
            initialState.eventSink.invoke(AnalyticsOptInEvents.EnableAnalytics(false))
            assertThat(awaitItem().isEnabled).isFalse()
            initialState.eventSink.invoke(AnalyticsOptInEvents.EnableAnalytics(true))
            assertThat(awaitItem().isEnabled).isTrue()
        }
    }
}
