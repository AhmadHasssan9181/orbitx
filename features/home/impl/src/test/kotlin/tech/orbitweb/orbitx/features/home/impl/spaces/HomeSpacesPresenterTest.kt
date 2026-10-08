/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.home.impl.spaces

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.features.invite.api.SeenInvitesStore
import tech.orbitweb.orbitx.features.invite.test.InMemorySeenInvitesStore
import tech.orbitweb.orbitx.libraries.matrix.api.MatrixClient
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.tests.testutils.test

class HomeSpacesPresenterTest {
    @Test
    fun `present - initial state`() = runTest {
        val presenter = createPresenter()
        presenter.test {
            val state = awaitItem()
            assertThat(state.space).isEqualTo(CurrentSpace.Root)
            assertThat(state.spaceRooms).isEmpty()
            assertThat(state.hideInvitesAvatar).isFalse()
            assertThat(state.seenSpaceInvites).isEmpty()
        }
    }

    private fun createPresenter(
        client: MatrixClient = FakeMatrixClient(),
        seenInvitesStore: SeenInvitesStore = InMemorySeenInvitesStore(),
    ) = HomeSpacesPresenter(
        client = client,
        seenInvitesStore = seenInvitesStore,
    )
}
