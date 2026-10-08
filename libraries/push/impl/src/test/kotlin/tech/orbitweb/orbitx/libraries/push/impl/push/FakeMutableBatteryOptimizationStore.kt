/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.push

import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeMutableBatteryOptimizationStore(
    private val showBatteryOptimizationBannerResult: () -> Unit = { lambdaError() },
    private val onOptimizationBannerDismissedResult: () -> Unit = { lambdaError() },
    private val resetResult: () -> Unit = { lambdaError() },
) : MutableBatteryOptimizationStore {
    override suspend fun showBatteryOptimizationBanner() {
        showBatteryOptimizationBannerResult()
    }

    override suspend fun onOptimizationBannerDismissed() {
        onOptimizationBannerDismissedResult()
    }

    override suspend fun reset() {
        resetResult()
    }
}
