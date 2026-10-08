/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.push.impl.troubleshoot

import com.google.common.truth.Truth.assertThat
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID_2
import tech.orbitweb.orbitx.libraries.matrix.test.FakeMatrixClient
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootNavigator
import tech.orbitweb.orbitx.libraries.troubleshoot.api.test.NotificationTroubleshootTestState
import tech.orbitweb.orbitx.libraries.troubleshoot.test.runAndTestState
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder

class IgnoredUsersTestTest {
    @Test
    fun `test IgnoredUsersTest order`() = runTest {
        val sut = IgnoredUsersTest(
            matrixClient = FakeMatrixClient(),
            stringProvider = FakeStringProvider(),
        )
        assertThat(sut.order).isEqualTo(80)
    }

    @Test
    fun `test IgnoredUsersTest quick fix`() = runTest {
        val sut = IgnoredUsersTest(
            matrixClient = FakeMatrixClient(),
            stringProvider = FakeStringProvider(),
        )
        val openIgnoredUsersResult = lambdaRecorder<Unit> {}
        val navigator = object : NotificationTroubleshootNavigator {
            override fun navigateToBlockedUsers() = openIgnoredUsersResult()
        }
        sut.quickFix(
            coroutineScope = backgroundScope,
            navigator = navigator,
        )
        openIgnoredUsersResult.assertions().isCalledOnce()
    }

    @Test
    fun `test IgnoredUsersTest with no blocked users`() = runTest {
        val sut = IgnoredUsersTest(
            matrixClient = FakeMatrixClient(
                ignoredUsersFlow = MutableStateFlow(persistentListOf())
            ),
            stringProvider = FakeStringProvider(),
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            assertThat(lastItem.status).isEqualTo(NotificationTroubleshootTestState.Status.Success)
        }
    }

    @Test
    fun `test IgnoredUsersTest with blocked users`() = runTest {
        val sut = IgnoredUsersTest(
            matrixClient = FakeMatrixClient(
                ignoredUsersFlow = MutableStateFlow(persistentListOf(A_USER_ID, A_USER_ID_2))
            ),
            stringProvider = FakeStringProvider(),
        )
        sut.runAndTestState {
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.Idle(true))
            assertThat(awaitItem().status).isEqualTo(NotificationTroubleshootTestState.Status.InProgress)
            val lastItem = awaitItem()
            val lastStatus = lastItem.status as NotificationTroubleshootTestState.Status.Failure
            assertThat(lastStatus.hasQuickFix).isTrue()
            assertThat(lastStatus.isCritical).isFalse()
            assertThat(lastStatus.quickFixButtonString).isNotNull()
            assertThat(lastItem.description).contains("2")
        }
    }
}
