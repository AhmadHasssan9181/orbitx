/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.appnav

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.appnav.root.RootPresenter
import tech.orbitweb.orbitx.features.rageshake.api.crash.aCrashDetectionState
import tech.orbitweb.orbitx.features.rageshake.api.detection.aRageshakeDetectionState
import tech.orbitweb.orbitx.libraries.matrix.test.FakeSdkMetadata
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.services.apperror.api.AppErrorState
import tech.orbitweb.orbitx.services.apperror.api.AppErrorStateService
import tech.orbitweb.orbitx.services.apperror.test.FakeAppErrorStateService
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class RootPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state`() = runTest {
        val presenter = createRootPresenter()
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertThat(initialState.crashDetectionState.crashDetected).isFalse()
        }
    }

    @Test
    fun `present - passes app error state`() = runTest {
        val presenter = createRootPresenter(
            appErrorService = FakeAppErrorStateService().apply {
                setAppErrorState(
                    AppErrorState.Error(
                        title = "Bad news",
                        body = "Something bad happened",
                        dismiss = {
                            setAppErrorState(AppErrorState.NoError)
                        }
                    )
                )
            }
        )
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertThat(initialState.errorState).isInstanceOf(AppErrorState.Error::class.java)
            val initialErrorState = initialState.errorState as AppErrorState.Error
            assertThat(initialErrorState.title).isEqualTo("Bad news")
            assertThat(initialErrorState.body).isEqualTo("Something bad happened")

            initialErrorState.dismiss()
            assertThat(awaitItem().errorState).isInstanceOf(AppErrorState.NoError::class.java)
        }
    }

    private fun createRootPresenter(
        appErrorService: AppErrorStateService = FakeAppErrorStateService(),
    ): RootPresenter {
        return RootPresenter(
            crashDetectionPresenter = { aCrashDetectionState() },
            rageshakeDetectionPresenter = { aRageshakeDetectionState() },
            appErrorStateService = appErrorService,
            analyticsService = FakeAnalyticsService(),
            sdkMetadata = FakeSdkMetadata("sha")
        )
    }
}
