/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.mediaviewer.impl.datasource

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tech.orbitweb.orbitx.libraries.matrix.api.room.CreateTimelineParams
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.FakeTimeline
import tech.orbitweb.orbitx.libraries.mediaviewer.impl.model.GroupedMediaItems
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaRecorder
import tech.orbitweb.orbitx.tests.testutils.lambda.value

class LiveMediaTimelineTest {
    @Test
    fun `LiveMediaTimeline cache is always null`() = runTest {
        val sut = createLiveMediaTimeline()
        assertThat<GroupedMediaItems?>(sut.cache).isNull()
    }

    @Test
    fun `getTimeline returns the timeline provided by the room, then from cache`() = runTest {
        val createTimelineResult = lambdaRecorder<CreateTimelineParams, Result<Timeline>> {
            Result.success(FakeTimeline())
        }
        val room = FakeJoinedRoom(
            createTimelineResult = createTimelineResult,
        )
        val sut = createLiveMediaTimeline(
            room = room,
        )
        val timeline = sut.getTimeline()
        assertThat(timeline.isSuccess).isTrue()
        createTimelineResult.assertions().isCalledOnce().with(value(CreateTimelineParams.MediaOnly))
        val timeline2 = sut.getTimeline()
        assertThat(timeline2.isSuccess).isTrue()
        // No called another time
        createTimelineResult.assertions().isCalledOnce()
    }

    private fun createLiveMediaTimeline(
        room: JoinedRoom = FakeJoinedRoom(),
    ) = LiveMediaTimeline(
        room = room,
    )
}
