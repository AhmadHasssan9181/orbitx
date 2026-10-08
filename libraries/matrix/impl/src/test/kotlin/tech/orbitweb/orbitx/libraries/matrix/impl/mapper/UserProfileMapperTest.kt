/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.mapper

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories.aRustUserProfile
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import org.junit.Test

class UserProfileMapperTest {
    @Test
    fun map() {
        assertThat(aRustUserProfile(A_USER_ID.value, "displayName", "avatarUrl").map())
            .isEqualTo(MatrixUser(A_USER_ID, "displayName", "avatarUrl"))
    }
}
