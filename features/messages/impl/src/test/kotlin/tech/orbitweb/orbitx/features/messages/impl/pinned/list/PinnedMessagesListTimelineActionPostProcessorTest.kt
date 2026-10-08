/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.pinned.list

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.features.messages.impl.actionlist.model.TimelineItemAction

class PinnedMessagesListTimelineActionPostProcessorTest {
    @Test
    fun `ensure that ViewInTimeline is added`() {
        val sut = PinnedMessagesListTimelineActionPostProcessor()
        val result = sut.process(
            listOf()
        )
        assertThat(result).isEqualTo(
            listOf(TimelineItemAction.ViewInTimeline)
        )
    }

    @Test
    fun `ensure that some actions are kept and some other are filtered out`() {
        val sut = PinnedMessagesListTimelineActionPostProcessor()
        val result = sut.process(
            TimelineItemAction.entries.toList()
        )
        assertThat(result).isEqualTo(
            listOf(
                TimelineItemAction.ViewInTimeline,
                TimelineItemAction.Unpin,
                TimelineItemAction.Forward,
                TimelineItemAction.ViewSource,
            )
        )
    }
}
