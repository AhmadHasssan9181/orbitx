/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.unifiedpush

const val A_UNIFIED_PUSH_GATEWAY = "aGateway"

class FakeDefaultPushGatewayHttpUrlProvider(
    private val provideResult: () -> String = { A_UNIFIED_PUSH_GATEWAY }
) : DefaultPushGatewayHttpUrlProvider {
    override fun provide(): String {
        return provideResult()
    }
}
