/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.Composable
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.testing.junit4.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import tech.orbitweb.orbitx.features.call.test.FakeElementCallEntryPoint
import tech.orbitweb.orbitx.features.forward.test.FakeForwardEntryPoint
import tech.orbitweb.orbitx.features.knockrequests.test.FakeKnockRequestsListEntryPoint
import tech.orbitweb.orbitx.features.location.test.FakeLocationService
import tech.orbitweb.orbitx.features.location.test.FakeShareLocationEntryPoint
import tech.orbitweb.orbitx.features.location.test.FakeShowLocationEntryPoint
import tech.orbitweb.orbitx.features.messages.api.MessagesEntryPoint
import tech.orbitweb.orbitx.features.messages.impl.pinned.banner.createPinnedEventsTimelineProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.createTimelineController
import tech.orbitweb.orbitx.features.poll.test.create.FakeCreatePollEntryPoint
import tech.orbitweb.orbitx.libraries.androidutils.system.DeviceHasVulkanSupport
import tech.orbitweb.orbitx.libraries.dateformatter.test.FakeDateFormatter
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.permalink.PermalinkData
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_SESSION_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.room.FakeBaseRoom
import tech.orbitweb.orbitx.libraries.matrix.test.roomlist.FakeRoomListService
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.RoomMemberProfilesCache
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.RoomNamesCache
import tech.orbitweb.orbitx.libraries.mediaviewer.test.FakeMediaViewerEntryPoint
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpanTheme
import tech.orbitweb.orbitx.libraries.textcomposer.mentions.MentionSpanUpdater
import tech.orbitweb.orbitx.services.analytics.test.FakeAnalyticsService
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError
import tech.orbitweb.orbitx.tests.testutils.node.TestParentNode
import tech.orbitweb.orbitx.tests.testutils.testCoroutineDispatchers
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class DefaultMessagesEntryPointTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `test node builder`() = runTest {
        val entryPoint = DefaultMessagesEntryPoint()
        val parentNode = TestParentNode.create { buildContext, plugins ->
            MessagesFlowNode(
                buildContext = buildContext,
                plugins = plugins,
                roomListService = FakeRoomListService(),
                sessionId = A_SESSION_ID,
                shareLocationEntryPoint = FakeShareLocationEntryPoint(),
                showLocationEntryPoint = FakeShowLocationEntryPoint(),
                createPollEntryPoint = FakeCreatePollEntryPoint(),
                elementCallEntryPoint = FakeElementCallEntryPoint(),
                mediaViewerEntryPoint = FakeMediaViewerEntryPoint(),
                forwardEntryPoint = FakeForwardEntryPoint(),
                analyticsService = FakeAnalyticsService(),
                locationService = FakeLocationService(),
                room = FakeBaseRoom(),
                roomMemberProfilesCache = RoomMemberProfilesCache(),
                roomNamesCache = RoomNamesCache(),
                mentionSpanUpdater = object : MentionSpanUpdater {
                    override fun updateMentionSpans(text: CharSequence) = text

                    @Composable
                    override fun rememberMentionSpans(text: CharSequence) = text
                },
                mentionSpanTheme = MentionSpanTheme(A_USER_ID),
                pinnedEventsTimelineProvider = createPinnedEventsTimelineProvider(),
                timelineController = createTimelineController(),
                knockRequestsListEntryPoint = FakeKnockRequestsListEntryPoint(),
                dateFormatter = FakeDateFormatter(),
                coroutineDispatchers = testCoroutineDispatchers(),
                hasVulkanSupport = DeviceHasVulkanSupport(mockk(relaxed = true))
            )
        }
        val callback = object : MessagesEntryPoint.Callback {
            override fun navigateToRoomDetails() = lambdaError()
            override fun navigateToRoomMemberDetails(userId: UserId) = lambdaError()
            override fun handlePermalinkClick(data: PermalinkData, pushToBackstack: Boolean) = lambdaError()
            override fun forwardEvent(eventId: EventId, fromPinnedEvents: Boolean) = lambdaError()
            override fun navigateToRoom(roomId: RoomId) = lambdaError()
            override fun navigateToDeveloperSettings() = lambdaError()
        }
        val initialTarget = MessagesEntryPoint.InitialTarget.Messages(focusedEventId = AN_EVENT_ID)
        val params = MessagesEntryPoint.Params(initialTarget)
        val result = entryPoint.createNode(
            parentNode = parentNode,
            buildContext = BuildContext.root(null),
            params = params,
            callback = callback,
        )
        assertThat(result).isInstanceOf(MessagesFlowNode::class.java)
        assertThat(result.plugins).contains(MessagesEntryPoint.Params(initialTarget))
        assertThat(result.plugins).contains(callback)
    }

    @Test
    fun `test initial target to nav target mapping`() {
        assertThat(MessagesEntryPoint.InitialTarget.Messages(focusedEventId = AN_EVENT_ID).toNavTarget())
            .isEqualTo(MessagesFlowNode.NavTarget.Messages(focusedEventId = AN_EVENT_ID))
        assertThat(MessagesEntryPoint.InitialTarget.PinnedMessages.toNavTarget())
            .isEqualTo(MessagesFlowNode.NavTarget.PinnedMessagesList)
    }
}
