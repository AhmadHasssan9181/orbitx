/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.scan

import androidx.activity.ComponentActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.matrix.api.auth.qrlogin.MatrixQrCodeLoginData
import tech.orbitweb.orbitx.libraries.matrix.test.auth.qrlogin.FakeMatrixQrCodeLoginData
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.pressBackKey
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class QrCodeScanViewTest : RobolectricTest() {
    private var provider: ProcessCameraProvider? = null

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        provider = ProcessCameraProvider.getInstance(context).get()
    }

    @After
    fun teardown() {
        provider?.unbindAll()
    }

    @Test
    fun `on back pressed - calls the expected callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setQrCodeScanView(
                state = aQrCodeScanState(),
                onBackClick = callback
            )
            pressBackKey()
        }
    }

    @Test
    fun `on QR code data ready - calls the expected callback`() = runAndroidComposeUiTest {
        val data = FakeMatrixQrCodeLoginData()
        ensureCalledOnceWithParam<MatrixQrCodeLoginData>(data) { callback ->
            setQrCodeScanView(
                state = aQrCodeScanState(authenticationAction = AsyncAction.Success(data)),
                onQrCodeDataReady = callback
            )
        }
    }

    private fun AndroidComposeUiTest<ComponentActivity>.setQrCodeScanView(
        state: QrCodeScanState,
        onBackClick: () -> Unit = EnsureNeverCalled(),
        onQrCodeDataReady: (MatrixQrCodeLoginData) -> Unit = EnsureNeverCalledWithParam(),
    ) {
        setContent {
            QrCodeScanView(
                state = state,
                onBackClick = onBackClick,
                onQrCodeDataReady = onQrCodeDataReady
            )
        }
    }
}
