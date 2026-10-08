/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.troubleshoot

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.pushproviders.test.FakePushProvider
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTestState
import tech.orbitweb.orbitx.libraries.troubleshoot.test.runAndTestState
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider

class PushProvidersTestTest {
    @Test
    fun `test PushProvidersTest with empty list`() = runTest {
        val sut = PushProvidersTest(
            pushProviders = emptySet(),
            stringProvider = FakeStringProvider(),
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Failure())
            sut.reset()
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
        }
    }

    @Test
    fun `test PushProvidersTest with 2 push providers`() = runTest {
        val sut = PushProvidersTest(
            pushProviders = setOf(
                FakePushProvider(name = "foo"),
                FakePushProvider(name = "bar"),
            ),
            stringProvider = FakeStringProvider(),
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Success)
            assertThat(lastItem.description).contains("foo")
            assertThat(lastItem.description).contains("bar")
        }
    }
}
