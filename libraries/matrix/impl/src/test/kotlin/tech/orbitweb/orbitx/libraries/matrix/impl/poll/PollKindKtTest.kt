/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.poll

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.poll.PollKind
import org.matrix.rustcomponents.sdk.PollKind as RustPollKind

class PollKindKtTest {
    @Test
    fun `map should return Disclosed when RustPollKind is Disclosed`() {
        val pollKind = RustPollKind.DISCLOSED.map()
        assertThat(pollKind).isEqualTo(PollKind.Disclosed)
    }

    @Test
    fun `map should return Undisclosed when RustPollKind is Undisclosed`() {
        val pollKind = RustPollKind.UNDISCLOSED.map()
        assertThat(pollKind).isEqualTo(PollKind.Undisclosed)
    }

    @Test
    fun `toInner should return DISCLOSED when PollKind is Disclosed`() {
        val rustPollKind = PollKind.Disclosed.toInner()
        assertThat(rustPollKind).isEqualTo(RustPollKind.DISCLOSED)
    }

    @Test
    fun `toInner should return UNDISCLOSED when PollKind is Undisclosed`() {
        val rustPollKind = PollKind.Undisclosed.toInner()
        assertThat(rustPollKind).isEqualTo(RustPollKind.UNDISCLOSED)
    }
}
