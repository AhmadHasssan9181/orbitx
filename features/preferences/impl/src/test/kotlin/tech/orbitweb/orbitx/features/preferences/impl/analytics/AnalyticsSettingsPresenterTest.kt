/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.preferences.impl.analytics

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.analytics.api.preferences.aAnalyticsPreferencesState
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule

class AnalyticsSettingsPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state`() = runTest {
        val presenter = AnalyticsSettingsPresenter(
            analyticsPreferencesPresenter = { aAnalyticsPreferencesState() },
        )
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertThat(initialState.analyticsPreferencesState.isEnabled).isFalse()
        }
    }
}
