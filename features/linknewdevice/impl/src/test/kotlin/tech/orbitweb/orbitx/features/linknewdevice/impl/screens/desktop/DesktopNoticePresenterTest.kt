/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.linknewdevice.impl.screens.desktop

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.permissions.test.FakePermissionsPresenter
import tech.orbitweb.orbitx.libraries.permissions.test.FakePermissionsPresenterFactory
import tech.orbitweb.orbitx.tests.testutils.test

class DesktopNoticePresenterTest {
    @Test
    fun `present - initial state`() = runTest {
        val presenter = createPresenter()
        presenter.test {
            awaitItem().run {
                assertThat(cameraPermissionState.permission).isEqualTo("android.permission.POST_NOTIFICATIONS")
                assertThat(canContinue).isFalse()
            }
        }
    }

    @Test
    fun `present - Continue with camera permissions can continue`() = runTest {
        val permissionsPresenter = FakePermissionsPresenter().apply { setPermissionGranted() }
        val permissionsPresenterFactory = FakePermissionsPresenterFactory(permissionsPresenter)
        val presenter = createPresenter(permissionsPresenterFactory = permissionsPresenterFactory)
        presenter.test {
            awaitItem().eventSink(DesktopNoticeEvent.Continue)
            assertThat(awaitItem().canContinue).isTrue()
        }
    }

    @Test
    fun `present - Continue with unknown camera permissions opens permission dialog`() = runTest {
        val permissionsPresenter = FakePermissionsPresenter()
        val permissionsPresenterFactory = FakePermissionsPresenterFactory(permissionsPresenter)
        val presenter = createPresenter(permissionsPresenterFactory = permissionsPresenterFactory)
        presenter.test {
            awaitItem().eventSink(DesktopNoticeEvent.Continue)
            assertThat(awaitItem().cameraPermissionState.showDialog).isTrue()
        }
    }
}

private fun createPresenter(
    permissionsPresenterFactory: FakePermissionsPresenterFactory = FakePermissionsPresenterFactory(),
) = DesktopNoticePresenter(
    permissionsPresenterFactory = permissionsPresenterFactory,
)
