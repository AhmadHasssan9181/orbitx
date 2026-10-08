/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.messages.impl.timeline.protection

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.libraries.designsystem.theme.components.Text
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class ProtectedViewTest : RobolectricTest() {
    @Test
    fun `when hideContent is false, the content is rendered`() = runAndroidComposeUiTest {
        setProtectedView(
            hideContent = false,
            content = {
                Text("Hello")
            }
        )
        onNodeWithText("Hello").assertExists()
    }

    @Test
    fun `when hideContent is true, the content is not rendered, and user can reveal it`() = runAndroidComposeUiTest {
        ensureCalledOnce {
            setProtectedView(
                hideContent = true,
                onShowClick = it,
                content = {
                    Text("Hello")
                }
            )
            onNodeWithText("Hello").assertDoesNotExist()
            clickOn(CommonStrings.action_show)
        }
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setProtectedView(
    hideContent: Boolean = false,
    onShowClick: () -> Unit = { lambdaError() },
    content: @Composable () -> Unit = {},
) {
    setContent {
        ProtectedView(
            hideContent = hideContent,
            onShowClick = onShowClick,
            content = content
        )
    }
}
