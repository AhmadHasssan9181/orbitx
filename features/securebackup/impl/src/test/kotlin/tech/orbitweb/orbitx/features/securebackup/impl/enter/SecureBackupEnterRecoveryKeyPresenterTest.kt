/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.securebackup.impl.enter

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.securebackup.impl.setup.views.RecoveryKeyUserStory
import tech.orbitweb.orbitx.features.securebackup.impl.setup.views.RecoveryKeyViewState
import tech.orbitweb.orbitx.features.securebackup.impl.tools.RecoveryKeyTools
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.encryption.EncryptionService
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EXCEPTION
import tech.orbitweb.orbitx.libraries.matrix.test.encryption.FakeEncryptionService
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule

class SecureBackupEnterRecoveryKeyPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state`() = runTest {
        val presenter = createPresenter()
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            assertThat(initialState.isSubmitEnabled).isFalse()
            assertThat(initialState.submitAction).isEqualTo(AsyncAction.Uninitialized)
            assertThat(initialState.recoveryKeyViewState).isEqualTo(
                RecoveryKeyViewState(
                    recoveryKeyUserStory = RecoveryKeyUserStory.Enter,
                    formattedRecoveryKey = "",
                    displayTextFieldContents = false,
                    inProgress = false,
                )
            )
        }
    }

    @Test
    fun `present - enter recovery key`() = runTest {
        val encryptionService = FakeEncryptionService()
        val presenter = createPresenter(encryptionService)
        moleculeFlow(RecompositionMode.Immediate) {
            presenter.present()
        }.test {
            val initialState = awaitItem()
            initialState.eventSink(SecureBackupEnterRecoveryKeyEvents.OnRecoveryKeyChange("1234"))
            val withRecoveryKeyState = awaitItem()
            assertThat(withRecoveryKeyState.isSubmitEnabled).isTrue()
            assertThat(withRecoveryKeyState.recoveryKeyViewState).isEqualTo(
                RecoveryKeyViewState(
                    recoveryKeyUserStory = RecoveryKeyUserStory.Enter,
                    formattedRecoveryKey = "1234",
                    displayTextFieldContents = false,
                    inProgress = false,
                )
            )
            encryptionService.givenRecoverFailure(AN_EXCEPTION)
            withRecoveryKeyState.eventSink(SecureBackupEnterRecoveryKeyEvents.Submit)
            val loadingState = awaitItem()
            assertThat(loadingState.submitAction).isEqualTo(AsyncAction.Loading)
            assertThat(loadingState.isSubmitEnabled).isFalse()
            val errorState = awaitItem()
            assertThat(errorState.submitAction).isEqualTo(AsyncAction.Failure(AN_EXCEPTION))
            assertThat(errorState.isSubmitEnabled).isFalse()
            errorState.eventSink(SecureBackupEnterRecoveryKeyEvents.ClearDialog)
            val clearedState = awaitItem()
            assertThat(clearedState.submitAction).isEqualTo(AsyncAction.Uninitialized)
            assertThat(clearedState.isSubmitEnabled).isTrue()
            encryptionService.givenRecoverFailure(null)
            clearedState.eventSink(SecureBackupEnterRecoveryKeyEvents.Submit)
            val loadingState2 = awaitItem()
            assertThat(loadingState2.submitAction).isEqualTo(AsyncAction.Loading)
            assertThat(loadingState2.isSubmitEnabled).isFalse()
            val finalState = awaitItem()
            assertThat(finalState.submitAction).isEqualTo(AsyncAction.Success(Unit))
            assertThat(finalState.isSubmitEnabled).isFalse()
        }
    }

    private fun createPresenter(
        encryptionService: EncryptionService = FakeEncryptionService(),
    ) = SecureBackupEnterRecoveryKeyPresenter(
        encryptionService = encryptionService,
        recoveryKeyTools = RecoveryKeyTools(),
    )
}
