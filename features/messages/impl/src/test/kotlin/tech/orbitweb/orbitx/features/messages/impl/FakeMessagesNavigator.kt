/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.messages.impl.attachments.Attachment
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.TimelineItemDebugInfo
import tech.orbitweb.orbitx.tests.testutils.lambda.lambdaError

class FakeMessagesNavigator(
    private val onShowEventDebugInfoClickLambda: (eventId: EventId?, debugInfo: TimelineItemDebugInfo) -> Unit = { _, _ -> lambdaError() },
    private val onForwardEventClickLambda: (eventId: EventId) -> Unit = { _ -> lambdaError() },
    private val onReportContentClickLambda: (eventId: EventId, senderId: UserId) -> Unit = { _, _ -> lambdaError() },
    private val onEditPollClickLambda: (eventId: EventId) -> Unit = { _ -> lambdaError() },
    private val onPreviewAttachmentLambda: (attachments: ImmutableList<Attachment>, inReplyToEventId: EventId?) -> Unit = { _, _ -> lambdaError() },
    private val onNavigateToRoomLambda: (roomId: RoomId, threadId: EventId?, serverNames: List<String>) -> Unit = { _, _, _ -> lambdaError() },
    private val navigateToMemberLambda: (userId: UserId) -> Unit = { lambdaError() },
    private val navigateToDeveloperSettingsLambda: () -> Unit = { lambdaError() },
    private val onOpenThreadLambda: (threadRootId: ThreadId, focusedEventId: EventId?) -> Unit = { _, _ -> lambdaError() },
    private val closeLambda: () -> Unit = { lambdaError() },
    private val navigateToCurrentLiveLocationLambda: () -> Unit = { lambdaError() },
) : MessagesNavigator {
    override fun navigateToEventDebugInfo(eventId: EventId?, debugInfo: TimelineItemDebugInfo) {
        onShowEventDebugInfoClickLambda(eventId, debugInfo)
    }

    override fun forwardEvent(eventId: EventId) {
        onForwardEventClickLambda(eventId)
    }

    override fun navigateToReportMessage(eventId: EventId, senderId: UserId) {
        onReportContentClickLambda(eventId, senderId)
    }

    override fun navigateToEditPoll(eventId: EventId) {
        onEditPollClickLambda(eventId)
    }

    override fun navigateToPreviewAttachments(attachments: ImmutableList<Attachment>, inReplyToEventId: EventId?) {
        onPreviewAttachmentLambda(attachments, inReplyToEventId)
    }

    override fun navigateToRoom(roomId: RoomId, eventId: EventId?, serverNames: List<String>) {
        onNavigateToRoomLambda(roomId, eventId, serverNames)
    }

    override fun navigateToMember(userId: UserId) {
        navigateToMemberLambda(userId)
    }

    override fun navigateToThread(threadRootId: ThreadId, focusedEventId: EventId?) {
        onOpenThreadLambda(threadRootId, focusedEventId)
    }

    override fun navigateToDeveloperSettings() {
        navigateToDeveloperSettingsLambda()
    }

    override fun navigateToCurrentLiveLocation() {
        navigateToCurrentLiveLocationLambda()
    }

    override fun close() {
        closeLambda()
    }
}
