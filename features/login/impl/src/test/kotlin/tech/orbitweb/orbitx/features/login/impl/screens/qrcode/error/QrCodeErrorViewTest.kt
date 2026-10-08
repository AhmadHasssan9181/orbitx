/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.login.impl.screens.qrcode.error

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.features.login.impl.qrcode.QrCodeErrorScreenType
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBackKey
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class QrCodeErrorViewTest : RobolectricTest() {
    @Test
    fun `on back pressed - calls the onCancel callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setQrCodeErrorView(
                onCancel = callback,
            )
            pressBackKey()
        }
    }

    @Test
    fun `on try again button clicked - calls the expected callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setQrCodeErrorView(
                onRetry = callback,
            )
            clickOn(CommonStrings.action_try_again)
        }
    }

    @Test
    fun `on cancel button clicked - calls the expected callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setQrCodeErrorView(
                onCancel = callback,
            )
            clickOn(CommonStrings.action_cancel)
        }
    }

    private fun AndroidComposeUiTest<ComponentActivity>.setQrCodeErrorView(
        onRetry: () -> Unit = EnsureNeverCalled(),
        onCancel: () -> Unit = EnsureNeverCalled(),
        errorScreenType: QrCodeErrorScreenType = QrCodeErrorScreenType.UnknownError,
        appName: String = "ORBIT Chat",
    ) {
        setContent {
            QrCodeErrorView(
                errorScreenType = errorScreenType,
                appName = appName,
                onRetry = onRetry,
                onCancel = onCancel,
            )
        }
    }
}
