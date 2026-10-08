/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.number

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.features.linknewdevice.impl.screens.number.model.Digit
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EXCEPTION

class EnterNumberStateTest {
    @Test
    fun `isContinueButtonEnabled is false if number is not complete`() {
        val sut = aEnterNumberState(
            number = "",
            sendingCode = AsyncAction.Uninitialized,
        )
        assertThat(sut.copy(number = "1").isContinueButtonEnabled).isFalse()
    }

    @Test
    fun `isContinueButtonEnabled is true if number is complete`() {
        val sut = aEnterNumberState(
            number = "12",
            sendingCode = AsyncAction.Uninitialized,
        )
        assertThat(sut.isContinueButtonEnabled).isTrue()
    }

    @Test
    fun `isContinueButtonEnabled is false if number is complete and sending is loading`() {
        val sut = aEnterNumberState(
            number = "12",
            sendingCode = AsyncAction.Loading,
        )
        assertThat(sut.isContinueButtonEnabled).isFalse()
    }

    @Test
    fun `isContinueButtonEnabled is true if number is complete and sending is not loading`() {
        listOf(
            AsyncAction.Uninitialized,
            AsyncAction.Failure(AN_EXCEPTION),
            AsyncAction.Success(Unit),
        ).forEach { action ->
            val sut = aEnterNumberState(
                number = "12",
                sendingCode = action,
            )
            assertThat(sut.isContinueButtonEnabled).isTrue()
        }
    }

    @Test
    fun `numberEntry is computed from number - case empty`() {
        val sut = aEnterNumberState(
            number = "",
        )
        assertThat(sut.numberEntry.size).isEqualTo(2)
        assertThat(sut.numberEntry.digits).containsExactly(
            Digit.Empty,
            Digit.Empty,
        )
    }

    @Test
    fun `numberEntry is computed from number - case half filled`() {
        val sut = aEnterNumberState(
            number = "1",
        )
        assertThat(sut.numberEntry.size).isEqualTo(2)
        assertThat(sut.numberEntry.digits).containsExactly(
            Digit.Filled('1'),
            Digit.Empty,
        )
    }

    @Test
    fun `numberEntry is computed from number - case filled`() {
        val sut = aEnterNumberState(
            number = "12",
        )
        assertThat(sut.numberEntry.size).isEqualTo(2)
        assertThat(sut.numberEntry.digits).containsExactly(
            Digit.Filled('1'),
            Digit.Filled('2'),
        )
    }
}
