/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.room

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.user.MatrixUser
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories.aRustRoomHero
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.factories.aRustRoomInfo
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID

class RoomInfoExtTest {
    @Test
    fun `get non empty element Heroes`() {
        val result = aRustRoomInfo(
            isDm = true,
            heroes = listOf(aRustRoomHero())
        ).elementHeroes()
        assertThat(result).isEqualTo(
            listOf(
                MatrixUser(
                    userId = UserId(A_USER_ID.value),
                    displayName = "displayName",
                    avatarUrl = "avatarUrl",
                )
            )
        )
    }

    @Test
    fun `too many heroes and element Heroes is empty`() {
        val result = aRustRoomInfo(
            isDm = true,
            heroes = listOf(aRustRoomHero(), aRustRoomHero())
        ).elementHeroes()
        assertThat(result).isEmpty()
    }

    @Test
    fun `not direct and element Heroes is empty`() {
        val result = aRustRoomInfo(
            isDm = false,
            heroes = listOf(aRustRoomHero())
        ).elementHeroes()
        assertThat(result).isEmpty()
    }
}
