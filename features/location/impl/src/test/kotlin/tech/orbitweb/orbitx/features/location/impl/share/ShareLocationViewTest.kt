/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.location.impl.share

import androidx.activity.ComponentActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import org.junit.Test
import tech.orbitweb.orbitx.features.location.impl.common.ui.LocationConstraintsDialogState
import tech.orbitweb.orbitx.libraries.testtags.TestTags
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalled
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnce
import tech.orbitweb.orbitx.tests.testutils.pressBack
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class ShareLocationViewTest : RobolectricTest() {
    @Test
    fun `test back action`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>(expectEvents = false)
        ensureCalledOnce { callback ->
            setShareLocationView(
                state = aShareLocationState(
                    eventSink = eventsRecorder
                ),
                navigateUp = callback,
            )
            pressBack()
        }
    }

    @Test
    fun `test fab click`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                eventSink = eventsRecorder
            ),
            navigateUp = EnsureNeverCalled(),
        )
        onNodeWithTag(TestTags.floatingActionButton.value).performClick()
        eventsRecorder.assertSingle(ShareLocationEvent.StartTrackingUserLocation)
    }

    @Test
    fun `when permission denied is displayed user can open the settings`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                dialogState = ShareLocationState.Dialog.Constraints(LocationConstraintsDialogState.PermissionDenied),
                eventSink = eventsRecorder
            ),
            navigateUp = EnsureNeverCalled(),
        )
        clickOn(CommonStrings.action_continue)
        eventsRecorder.assertSingle(ShareLocationEvent.OpenAppSettings)
    }

    @Test
    fun `when permission denied is displayed user can close the dialog`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                dialogState = ShareLocationState.Dialog.Constraints(LocationConstraintsDialogState.PermissionDenied),
                eventSink = eventsRecorder
            ),
            navigateUp = EnsureNeverCalled(),
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(ShareLocationEvent.DismissDialog)
    }

    @Test
    fun `when permission rationale is displayed user can request permissions`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                dialogState = ShareLocationState.Dialog.Constraints(LocationConstraintsDialogState.PermissionRationale),
                eventSink = eventsRecorder
            ),
            navigateUp = EnsureNeverCalled(),
        )
        clickOn(CommonStrings.action_continue)
        eventsRecorder.assertSingle(ShareLocationEvent.RequestPermissions)
    }

    @Test
    fun `when permission rationale is displayed user can close the dialog`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                dialogState = ShareLocationState.Dialog.Constraints(LocationConstraintsDialogState.PermissionRationale),
                eventSink = eventsRecorder
            ),
            navigateUp = EnsureNeverCalled(),
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(ShareLocationEvent.DismissDialog)
    }

    @Test
    fun `when location service disabled is displayed user can open location settings`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                dialogState = ShareLocationState.Dialog.Constraints(LocationConstraintsDialogState.LocationServiceDisabled),
                eventSink = eventsRecorder
            ),
            navigateUp = EnsureNeverCalled(),
        )
        clickOn(CommonStrings.action_continue)
        eventsRecorder.assertSingle(ShareLocationEvent.OpenLocationSettings)
    }

    @Test
    fun `when location service disabled is displayed user can close the dialog`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                dialogState = ShareLocationState.Dialog.Constraints(LocationConstraintsDialogState.LocationServiceDisabled),
                eventSink = eventsRecorder
            ),
            navigateUp = EnsureNeverCalled(),
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(ShareLocationEvent.DismissDialog)
    }

    @Test
    fun `when disclaimer is displayed user can accept`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                dialogState = ShareLocationState.Dialog.LiveLocationDisclaimer,
                eventSink = eventsRecorder,
                canShareLiveLocation = true,
            ),
            navigateUp = EnsureNeverCalled(),
        )

        clickOn(CommonStrings.action_accept)
        eventsRecorder.assertSingle(ShareLocationEvent.AcceptLiveLocationDisclaimer)
    }

    @Test
    fun `when disclaimer is displayed user can decline`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<ShareLocationEvent>()
        setShareLocationView(
            aShareLocationState(
                dialogState = ShareLocationState.Dialog.LiveLocationDisclaimer,
                eventSink = eventsRecorder,
                canShareLiveLocation = true,
            ),
            navigateUp = EnsureNeverCalled(),
        )

        clickOn(CommonStrings.action_decline)
        eventsRecorder.assertSingle(ShareLocationEvent.DismissDialog)
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setShareLocationView(
    state: ShareLocationState,
    navigateUp: () -> Unit = EnsureNeverCalled(),
) {
    setContent {
        // Simulate a LocalInspectionMode for MapLibreMap
        CompositionLocalProvider(LocalInspectionMode provides true) {
            ShareLocationView(
                state = state,
                navigateUp = navigateUp,
            )
        }
    }
}
