/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.server

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import org.junit.Test

class DefaultUserServerResolverTest {
    @Test
    fun resolve() {
        // Given
        val userServerResolver = DefaultUserServerResolver(FakeMatrixClient(
            userIdServerNameLambda = { "dummy.org" }
        ))

        // When
        val result = userServerResolver.resolve()

        // Then
        assertThat(result).isEqualTo("dummy.org")
    }
}
