/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.receipt.bottomsheet

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemEvent
import tech.orbitweb.orbitx.tests.testutils.WarmUpRule
import tech.orbitweb.orbitx.tests.testutils.test

class ReadReceiptBottomSheetPresenterTest {
    @get:Rule
    val warmUpRule = WarmUpRule()

    @Test
    fun `present - handle event selected`() = runTest {
        val presenter = createPresenter()
        presenter.test {
            val initialState = awaitItem()
            val selectedEvent = aTimelineItemEvent()
            initialState.eventSink(ReadReceiptBottomSheetEvent.EventSelected(selectedEvent))
            assertThat(awaitItem().selectedEvent).isSameInstanceAs(selectedEvent)
        }
    }

    @Test
    fun `present - handle dismiss`() = runTest {
        val presenter = createPresenter()
        presenter.test {
            val initialState = awaitItem()
            val selectedEvent = aTimelineItemEvent()
            initialState.eventSink(ReadReceiptBottomSheetEvent.EventSelected(selectedEvent))
            skipItems(1)
            initialState.eventSink(ReadReceiptBottomSheetEvent.Dismiss)
            assertThat(awaitItem().selectedEvent).isNull()
        }
    }

    private fun createPresenter() = ReadReceiptBottomSheetPresenter()
}
