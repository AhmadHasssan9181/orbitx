/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.impl.timeline.postprocessor

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.MatrixTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.virtual.VirtualTimelineItem
import tech.orbitweb.orbitx.services.toolbox.test.systemclock.FakeSystemClock

class LoadingIndicatorsPostProcessorTest {
    @Test
    fun `LoadingIndicatorsPostProcessor adds Loading indicator at the top of the list if hasMoreToLoadBackward is true`() {
        val clock = FakeSystemClock()
        val sut = LoadingIndicatorsPostProcessor(clock)
        val result = sut.process(
            items = listOf(messageEvent, messageEvent2),
            hasMoreToLoadBackward = true,
            hasMoreToLoadForward = false,
        )
        assertThat(result).containsExactly(
            MatrixTimelineItem.Virtual(
                uniqueId = UniqueId("BackwardLoadingIndicator"),
                virtual = VirtualTimelineItem.LoadingIndicator(
                    direction = Timeline.PaginationDirection.BACKWARDS,
                    timestamp = clock.epochMillis()
                )
            ),
            messageEvent,
            messageEvent2,
        )
    }

    @Test
    fun `LoadingIndicatorsPostProcessor adds Loading indicator at the bottom of the list if hasMoreToLoadForward is true`() {
        val clock = FakeSystemClock()
        val sut = LoadingIndicatorsPostProcessor(clock)
        val result = sut.process(
            items = listOf(messageEvent, messageEvent2),
            hasMoreToLoadBackward = false,
            hasMoreToLoadForward = true,
        )
        assertThat(result).containsExactly(
            messageEvent,
            messageEvent2,
            MatrixTimelineItem.Virtual(
                uniqueId = UniqueId("ForwardLoadingIndicator"),
                virtual = VirtualTimelineItem.LoadingIndicator(
                    direction = Timeline.PaginationDirection.FORWARDS,
                    timestamp = clock.epochMillis()
                )
            ),
        )
    }

    @Test
    fun `LoadingIndicatorsPostProcessor adds Loading indicator at the bottom and at the top of the list`() {
        val clock = FakeSystemClock()
        val sut = LoadingIndicatorsPostProcessor(clock)
        val result = sut.process(
            items = listOf(messageEvent, messageEvent2),
            hasMoreToLoadBackward = true,
            hasMoreToLoadForward = true,
        )
        assertThat(result).containsExactly(
            MatrixTimelineItem.Virtual(
                uniqueId = UniqueId("BackwardLoadingIndicator"),
                virtual = VirtualTimelineItem.LoadingIndicator(
                    direction = Timeline.PaginationDirection.BACKWARDS,
                    timestamp = clock.epochMillis()
                )
            ),
            messageEvent,
            messageEvent2,
            MatrixTimelineItem.Virtual(
                uniqueId = UniqueId("ForwardLoadingIndicator"),
                virtual = VirtualTimelineItem.LoadingIndicator(
                    direction = Timeline.PaginationDirection.FORWARDS,
                    timestamp = clock.epochMillis()
                )
            ),
        )
    }

    @Test
    fun `LoadingIndicatorsPostProcessor only adds 1 Loading indicator if there is no items in the list`() {
        val clock = FakeSystemClock()
        val sut = LoadingIndicatorsPostProcessor(clock)
        val result = sut.process(
            items = listOf(),
            hasMoreToLoadBackward = true,
            hasMoreToLoadForward = true,
        )
        assertThat(result).containsExactly(
            MatrixTimelineItem.Virtual(
                uniqueId = UniqueId("BackwardLoadingIndicator"),
                virtual = VirtualTimelineItem.LoadingIndicator(
                    direction = Timeline.PaginationDirection.BACKWARDS,
                    timestamp = clock.epochMillis()
                )
            ),
        )
    }
}
