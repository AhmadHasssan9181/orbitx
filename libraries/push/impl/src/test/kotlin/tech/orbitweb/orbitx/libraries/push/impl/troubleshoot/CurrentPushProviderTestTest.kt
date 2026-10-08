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
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.push.test.FakePushService
import tech.orbitweb.orbitx.libraries.pushproviders.api.Distributor
import tech.orbitweb.orbitx.libraries.pushproviders.test.FakePushProvider
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTestState
import tech.orbitweb.orbitx.libraries.troubleshoot.test.runAndTestState
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider

class CurrentPushProviderTestTest {
    @Test
    fun `test CurrentPushProviderTest with a push provider and a distributor`() = runTest {
        val sut = CurrentPushProviderTest(
            pushService = FakePushService(
                currentPushProvider = {
                    FakePushProvider(
                        name = "foo",
                        currentDistributorValue = { "aDistributor" },
                    )
                }
            ),
            stringProvider = FakeStringProvider(),
            sessionId = A_SESSION_ID,
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Success)
            assertThat(lastItem.description).contains("foo")
        }
    }

    @Test
    fun `test CurrentPushProviderTest with a push provider supporting multiple distributors, distributor found`() = runTest {
        val sut = CurrentPushProviderTest(
            pushService = FakePushService(
                currentPushProvider = {
                    FakePushProvider(
                        name = "foo",
                        currentDistributorValue = { "aDistributor" },
                        supportMultipleDistributors = true,
                        distributors = listOf(Distributor("aDistributor", "aDistributor"))
                    )
                },
            ),
            stringProvider = FakeStringProvider(),
            sessionId = A_SESSION_ID,
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Success)
            assertThat(lastItem.description).contains("foo")
        }
    }

    @Test
    fun `test CurrentPushProviderTest with a push provider supporting multiple distributors, no distributor`() = runTest {
        val sut = CurrentPushProviderTest(
            pushService = FakePushService(
                currentPushProvider = {
                    FakePushProvider(
                        name = "foo",
                        currentDistributorValue = { null },
                        supportMultipleDistributors = true,
                    )
                },
            ),
            stringProvider = FakeStringProvider(),
            sessionId = A_SESSION_ID,
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Failure())
        }
    }

    @Test
    fun `test CurrentPushProviderTest with a push provider supporting multiple distributors, distributor not found`() = runTest {
        val sut = CurrentPushProviderTest(
            pushService = FakePushService(
                currentPushProvider = {
                    FakePushProvider(
                        name = "foo",
                        currentDistributorValue = { "aDistributor" },
                        supportMultipleDistributors = true,
                        distributors = emptyList()
                    )
                },
            ),
            stringProvider = FakeStringProvider(),
            sessionId = A_SESSION_ID,
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Failure())
        }
    }

    @Test
    fun `test CurrentPushProviderTest without push provider`() = runTest {
        val sut = CurrentPushProviderTest(
            pushService = FakePushService(
                currentPushProvider = { null },
            ),
            stringProvider = FakeStringProvider(),
            sessionId = A_SESSION_ID,
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
}
