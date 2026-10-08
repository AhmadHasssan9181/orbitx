/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.analytics.impl.store

import kotlinx.coroutines.flow.MutableStateFlow
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

internal class FakeAnalyticsStore(
    defaultUserConsent: Boolean = false,
    defaultDidAskUserConsent: Boolean = false,
    defaultAnalyticsId: String = "",
    private val resetLambda: () -> Unit = { lambdaError() },
) : AnalyticsStore {
    override val userConsentFlow = MutableStateFlow(defaultUserConsent)
    override val didAskUserConsentFlow = MutableStateFlow(defaultDidAskUserConsent)
    override val analyticsIdFlow = MutableStateFlow(defaultAnalyticsId)

    override suspend fun setUserConsent(newUserConsent: Boolean) {
        userConsentFlow.emit(newUserConsent)
    }

    override suspend fun setDidAskUserConsent(newValue: Boolean) {
        didAskUserConsentFlow.emit(newValue)
    }

    override suspend fun setAnalyticsId(newAnalyticsId: String) {
        analyticsIdFlow.emit(newAnalyticsId)
    }

    override suspend fun reset() {
        resetLambda()
    }
}
