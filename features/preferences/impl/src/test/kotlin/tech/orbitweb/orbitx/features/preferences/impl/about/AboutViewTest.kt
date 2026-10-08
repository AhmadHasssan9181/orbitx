/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.preferences.impl.about

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import org.junit.Test

class AboutViewTest : RobolectricTest() {
    @Test
    fun `clicking on back invokes back callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setAboutView(
                anAboutState(),
                onBackClick = callback,
            )
            pressBack()
        }
    }

    @Test
    fun `clicking on an item invokes the expected callback`() = runAndroidComposeUiTest {
        val state = anAboutState()
        ensureCalledOnceWithParam(state.elementLegals.first()) { callback ->
            setAboutView(
                state,
                onElementLegalClick = callback,
            )
            clickOn(state.elementLegals.first().titleRes)
        }
    }

    @Test
    fun `clicking on the open source licenses invokes the expected callback`() = runAndroidComposeUiTest {
        ensureCalledOnce { callback ->
            setAboutView(
                anAboutState(),
                onOpenSourceLicensesClick = callback,
            )
            clickOn(CommonStrings.common_open_source_licenses)
        }
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setAboutView(
    state: AboutState,
    onElementLegalClick: (ElementLegal) -> Unit = EnsureNeverCalledWithParam(),
    onOpenSourceLicensesClick: () -> Unit = EnsureNeverCalled(),
    onBackClick: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        AboutView(
            state = state,
            onElementLegalClick = onElementLegalClick,
            onOpenSourceLicensesClick = onOpenSourceLicensesClick,
            onBackClick = onBackClick,
        )
    }
}
