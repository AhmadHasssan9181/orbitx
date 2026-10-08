/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl

import tech.orbitweb.orbitx.features.messages.impl.attachments.Attachment
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.RoomId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.TimelineItemDebugInfo
import kotlinx.collections.immutable.ImmutableList

interface MessagesNavigator {
    fun navigateToEventDebugInfo(eventId: EventId?, debugInfo: TimelineItemDebugInfo)
    fun forwardEvent(eventId: EventId)
    fun navigateToReportMessage(eventId: EventId, senderId: UserId)
    fun navigateToEditPoll(eventId: EventId)
    fun navigateToPreviewAttachments(attachments: ImmutableList<Attachment>, inReplyToEventId: EventId?)
    fun navigateToRoom(roomId: RoomId, eventId: EventId?, serverNames: List<String>)
    fun navigateToMember(userId: UserId)
    fun navigateToThread(threadRootId: ThreadId, focusedEventId: EventId?)
    fun navigateToDeveloperSettings()
    fun navigateToCurrentLiveLocation()
    fun close()
}
