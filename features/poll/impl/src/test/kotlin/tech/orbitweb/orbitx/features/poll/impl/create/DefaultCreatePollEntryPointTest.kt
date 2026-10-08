/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bumble.appyx.core.modality.BuildContext
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import tech.orbitweb.orbitx.features.messages.test.FakeMessageComposerContext
import tech.orbitweb.orbitx.features.poll.api.create.CreatePollEntryPoint
import tech.orbitweb.orbitx.features.poll.api.create.CreatePollMode
import tech.orbitweb.orbitx.features.poll.impl.data.PollRepository
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeJoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.LiveTimelineProvider
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode

class DefaultCreatePollEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test node builder`() {
        val entryPoint = DefaultCreatePollEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            CreatePollNode(
                buildContext = buildContext,
                plugins = plugins,
                presenterFactory = { timelineMode: Timeline.Mode, backNavigator: () -> Unit, mode: CreatePollMode ->
                    CreatePollPresenter(
                        repositoryFactory = {
                            val room = FakeJoinedRoom()
                            PollRepository(room, LiveTimelineProvider(room), timelineMode)
                        },
                        analyticsService = FakeAnalyticsService(),
                        messageComposerContext = FakeMessageComposerContext(),
                        navigateUp = backNavigator,
                        mode = mode,
                        timelineMode = timelineMode,
                    )
                },
                analyticsService = FakeAnalyticsService(),
            )
        }
        val params = CreatePollEntryPoint.Params(
            timelineMode = Timeline.Mode.Live,
            mode = CreatePollMode.NewPoll,
        )
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            params = params,
        )
        assertThat(result).isInstanceOf(CreatePollNode::class.java)
        assertThat(result.plugins).contains(CreatePollNode.Inputs(timelineMode = params.timelineMode, mode = params.mode))
    }
}
