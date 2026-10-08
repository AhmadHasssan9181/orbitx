/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest
import tech.orbitweb.orbitx.tests.testutils.setSafeContent

class MediaDeleteConfirmationBottomSheetTest : RobolectricTest() {
    @Test
    fun `clicking on Cancel invokes expected callback`() = runAndroidComposeUiTest {
        val state = aMediaBottomSheetStateDeleteConfirmation()
        ensureCalledOnce { callback ->
            setMediaDeleteConfirmationBottomSheet(
                state = state,
                onDismiss = callback,
            )
            clickOn(CommonStrings.action_cancel)
        }
    }

    @Test
    fun `clicking on Remove invokes expected callback`() = runAndroidComposeUiTest {
        val state = aMediaBottomSheetStateDeleteConfirmation()
        ensureCalledOnceWithParam(state.eventId) { callback ->
            setMediaDeleteConfirmationBottomSheet(
                state = state,
                onDelete = callback,
            )
            onNodeWithText(activity!!.getString(CommonStrings.action_remove)).assertExists()
            clickOn(CommonStrings.action_remove)
        }
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setMediaDeleteConfirmationBottomSheet(
    state: MediaBottomSheetState.DeleteConfirmation,
    onDelete: (EventId) -> Unit = EnsureNeverCalledWithParam(),
    onDismiss: () -> Unit = EnsureNeverCalled(),
) {
    setSafeContent {
        MediaDeleteConfirmationBottomSheet(
            state = state,
            onDelete = onDelete,
            onDismiss = onDismiss,
        )
    }
}
