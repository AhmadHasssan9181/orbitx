/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.oauth.test.customtab

import tech.orbitweb.orbitx.libraries.oauth.api.OAuthAction
import tech.orbitweb.orbitx.libraries.oauth.api.OAuthActionFlow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * This is actually a copy of DefaultOAuthActionFlow.
 */
class FakeOAuthActionFlow : OAuthActionFlow {
    private val mutableStateFlow = MutableStateFlow<OAuthAction?>(null)

    override fun post(oAuthAction: OAuthAction) {
        mutableStateFlow.value = oAuthAction
    }

    override suspend fun collect(collector: FlowCollector<OAuthAction?>) {
        mutableStateFlow.collect(collector)
    }

    override fun reset() {
        mutableStateFlow.value = null
    }
}
