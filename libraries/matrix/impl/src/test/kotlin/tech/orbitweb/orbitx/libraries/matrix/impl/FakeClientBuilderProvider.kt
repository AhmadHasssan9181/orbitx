/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl

import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiClientBuilder
import org.matrix.rustcomponents.sdk.ClientBuilder

class FakeClientBuilderProvider(
    private val provideResult: () -> ClientBuilder = { FakeFfiClientBuilder() }
) : ClientBuilderProvider {
    override fun provide(): ClientBuilder {
        return provideResult()
    }
}
