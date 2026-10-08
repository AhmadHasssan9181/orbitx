/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.linknewdevice

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.impl.fixtures.fakes.FakeFfiCheckCodeSender
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value

class RustCheckCodeSenderTest {
    @Test
    fun `send invokes the Ffi object`() = runTest {
        val sendResult = lambdaRecorder<UByte, Unit> { }
        val sut = RustCheckCodeSender(
            inner = FakeFfiCheckCodeSender(
                sendResult = sendResult,
            ),
            sessionDispatcher = StandardTestDispatcher(testScheduler),
        )
        sut.send(1.toUByte())
        sendResult.assertions().isCalledOnce().with(value(1.toUByte()))
    }

    @Test
    fun `validate always returns true for now`() = runTest {
        val sut = RustCheckCodeSender(
            inner = FakeFfiCheckCodeSender(),
            sessionDispatcher = StandardTestDispatcher(testScheduler),
        )
        val result = sut.validate(1.toUByte())
        assertThat(result).isTrue()
    }
}
