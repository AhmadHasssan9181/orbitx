/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.services.apperror.impl

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.services.apperror.api.AppErrorState
import tech.orbitweb.orbitx.services.toolbox.test.strings.FakeStringProvider

internal class DefaultAppErrorStateServiceTest {
    @Test
    fun `initial value is no error`() = runTest {
        val service = createDefaultAppErrorStateService()
        service.appErrorStateFlow.test {
            val state = awaitItem()
            assertThat(state).isInstanceOf(AppErrorState.NoError::class.java)
        }
    }

    @Test
    fun `showError - emits value`() = runTest {
        val service = createDefaultAppErrorStateService()
        service.appErrorStateFlow.test {
            skipItems(1)

            service.showError("Title", "Body")
            val state = awaitItem()
            assertThat(state).isInstanceOf(AppErrorState.Error::class.java)

            val errorState = state as AppErrorState.Error
            assertThat(errorState.title).isEqualTo("Title")
            assertThat(errorState.body).isEqualTo("Body")
        }
    }

    @Test
    fun `showError - emits value from ids`() = runTest {
        val service = createDefaultAppErrorStateService()
        service.appErrorStateFlow.test {
            skipItems(1)
            service.showError(1, 2)
            val state = awaitItem()
            assertThat(state).isInstanceOf(AppErrorState.Error::class.java)
            val errorState = state as AppErrorState.Error
            assertThat(errorState.title).isEqualTo("A string")
            assertThat(errorState.body).isEqualTo("A string")
        }
    }

    @Test
    fun `dismiss - clears value`() = runTest {
        val service = createDefaultAppErrorStateService()
        service.appErrorStateFlow.test {
            skipItems(1)

            service.showError("Title", "Body")
            val state = awaitItem()
            assertThat(state).isInstanceOf(AppErrorState.Error::class.java)

            val errorState = state as AppErrorState.Error
            errorState.dismiss()

            assertThat(awaitItem()).isInstanceOf(AppErrorState.NoError::class.java)
        }
    }

    private fun createDefaultAppErrorStateService() = DefaultAppErrorStateService(
        stringProvider = FakeStringProvider(),
    )
}
