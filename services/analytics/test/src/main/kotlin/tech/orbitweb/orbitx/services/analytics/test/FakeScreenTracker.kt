/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import im.vector.app.features.analytics.plan.MobileScreen
import tech.orbitweb.orbitx.services.analytics.api.ScreenTracker
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeScreenTracker(
    private val trackScreenLambda: (MobileScreen.ScreenName) -> Unit = { lambdaError() }
) : ScreenTracker {
    @Composable
    override fun TrackScreen(screen: MobileScreen.ScreenName) {
        LaunchedEffect(Unit) {
            trackScreenLambda(screen)
        }
    }
}
