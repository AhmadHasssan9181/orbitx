/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.rageshake.impl.preferences

import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.rageshake.api.preferences.RageshakePreferencesEvent
import tech.orbitweb.orbitx.features.rageshake.impl.rageshake.A_SENSITIVITY
import tech.orbitweb.orbitx.features.rageshake.impl.rageshake.FakeRageShake
import tech.orbitweb.orbitx.features.rageshake.impl.rageshake.FakeRageshakeDataStore
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class RageshakePreferencesPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - initial state available`() = runTest {
        val presenter = DefaultRageshakePreferencesPresenter(
            FakeRageShake(isAvailableValue = true),
            FakeRageshakeDataStore(isEnabled = true),
            rageshakeFeatureAvailability = { flowOf(true) },
        )
        presenter.test {
            skipItems(1)
            val initialState = awaitItem()
            assertThat(initialState.isSupported).isTrue()
            assertThat(initialState.isEnabled).isTrue()
        }
    }

    @Test
    fun `present - initial state not available`() = runTest {
        val presenter = DefaultRageshakePreferencesPresenter(
            FakeRageShake(isAvailableValue = false),
            FakeRageshakeDataStore(isEnabled = true),
            rageshakeFeatureAvailability = { flowOf(true) },
        )
        presenter.test {
            skipItems(1)
            val initialState = awaitItem()
            assertThat(initialState.isSupported).isFalse()
            assertThat(initialState.isEnabled).isTrue()
        }
    }

    @Test
    fun `present - enable and disable`() = runTest {
        val presenter = DefaultRageshakePreferencesPresenter(
            FakeRageShake(isAvailableValue = true),
            FakeRageshakeDataStore(isEnabled = true),
            rageshakeFeatureAvailability = { flowOf(true) },
        )
        presenter.test {
            skipItems(1)
            val initialState = awaitItem()
            assertThat(initialState.isEnabled).isTrue()
            initialState.eventSink.invoke(RageshakePreferencesEvent.SetIsEnabled(false))
            assertThat(awaitItem().isEnabled).isFalse()
            initialState.eventSink.invoke(RageshakePreferencesEvent.SetIsEnabled(true))
            assertThat(awaitItem().isEnabled).isTrue()
        }
    }

    @Test
    fun `present - set sensitivity`() = runTest {
        val presenter = DefaultRageshakePreferencesPresenter(
            FakeRageShake(isAvailableValue = true),
            FakeRageshakeDataStore(isEnabled = true),
            rageshakeFeatureAvailability = { flowOf(true) },
        )
        presenter.test {
            skipItems(1)
            val initialState = awaitItem()
            assertThat(initialState.sensitivity).isEqualTo(A_SENSITIVITY)
            initialState.eventSink.invoke(RageshakePreferencesEvent.SetSensitivity(A_SENSITIVITY + 1f))
            assertThat(awaitItem().sensitivity).isEqualTo(A_SENSITIVITY + 1f)
        }
    }
}
