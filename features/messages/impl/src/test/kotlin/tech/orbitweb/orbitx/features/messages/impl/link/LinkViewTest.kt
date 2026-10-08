/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

@file:OptIn(ExperimentalTestApi::class)

package tech.orbitweb.orbitx.features.messages.impl.link

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runAndroidComposeUiTest
import io.element.android.wysiwyg.link.Link
import org.junit.Test
import tech.orbitweb.orbitx.libraries.architecture.AsyncAction
import tech.orbitweb.orbitx.libraries.ui.strings.CommonStrings
import tech.orbitweb.orbitx.tests.testutils.EnsureNeverCalledWithParam
import tech.orbitweb.orbitx.tests.testutils.EventsRecorder
import tech.orbitweb.orbitx.tests.testutils.clickOn
import tech.orbitweb.orbitx.tests.testutils.ensureCalledOnceWithParam
import tech.orbitweb.orbitx.tests.testutils.robolectric.RobolectricTest

class LinkViewTest : RobolectricTest() {
    @Test
    fun `clicking on cancel emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<LinkEvent>()
        setLinkView(
            aLinkState(
                linkClick = ConfirmingLinkClick(aLink),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_cancel)
        eventsRecorder.assertSingle(
            LinkEvent.Cancel
        )
    }

    @Test
    fun `clicking on continue emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<LinkEvent>()
        setLinkView(
            aLinkState(
                linkClick = ConfirmingLinkClick(aLink),
                eventSink = eventsRecorder,
            ),
        )
        clickOn(CommonStrings.action_continue)
        eventsRecorder.assertSingle(
            LinkEvent.Confirm
        )
    }

    @Test
    fun `success state invokes the callback and emits the expected event`() = runAndroidComposeUiTest {
        val eventsRecorder = EventsRecorder<LinkEvent>()
        ensureCalledOnceWithParam(aLink) { callback ->
            setLinkView(
                aLinkState(
                    linkClick = AsyncAction.Success(aLink),
                    eventSink = eventsRecorder,
                ),
                onLinkValid = callback,
            )
        }
        eventsRecorder.assertSingle(
            LinkEvent.Cancel
        )
    }
}

private fun AndroidComposeUiTest<ComponentActivity>.setLinkView(
    state: LinkState,
    onLinkValid: (Link) -> Unit = EnsureNeverCalledWithParam(),
) {
    setContent {
        LinkView(
            state = state,
            onLinkValid = onLinkValid,
        )
    }
}
