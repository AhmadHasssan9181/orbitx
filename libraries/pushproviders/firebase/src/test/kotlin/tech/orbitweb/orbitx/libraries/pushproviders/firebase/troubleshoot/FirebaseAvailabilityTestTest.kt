/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.pushproviders.firebase.troubleshoot

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.pushproviders.firebase.FakeIsPlayServiceAvailable
import tech.orbitweb.orbitx.libraries.pushproviders.firebase.FirebaseConfig
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTestState
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.TestFilterData
import tech.orbitweb.orbitx.libraries.troubleshoot.test.runAndTestState
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider

class FirebaseAvailabilityTestTest {
    @Test
    fun `test FirebaseAvailabilityTest success`() = runTest {
        val sut = FirebaseAvailabilityTest(
            isPlayServiceAvailable = FakeIsPlayServiceAvailable(true),
            stringProvider = FakeStringProvider(),
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(false))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Success)
        }
    }

    @Test
    fun `test FirebaseAvailabilityTest failure`() = runTest {
        val sut = FirebaseAvailabilityTest(
            isPlayServiceAvailable = FakeIsPlayServiceAvailable(false),
            stringProvider = FakeStringProvider(),
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(false))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Failure())
            sut.reset()
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(false))
        }
    }

    @Test
    fun `test FirebaseAvailabilityTest isRelevant`() {
        val sut = FirebaseAvailabilityTest(
            isPlayServiceAvailable = FakeIsPlayServiceAvailable(false),
            stringProvider = FakeStringProvider(),
        )
        assertThat(sut.isRelevant(TestFilterData(currentPushProviderName = "unknown"))).isFalse()
        assertThat(sut.isRelevant(TestFilterData(currentPushProviderName = FirebaseConfig.NAME))).isTrue()
    }
}
