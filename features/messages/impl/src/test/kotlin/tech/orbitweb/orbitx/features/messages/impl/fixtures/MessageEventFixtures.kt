/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.fixtures

import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemDebugInfo
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemReactions
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.ReadReceiptData
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemReadReceipts
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItemThreadInfo
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemTextContent
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarData
import tech.orbitweb.orbitx.libraries.designsystem.components.avatar.AvatarSize
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.TransactionId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.LocalEventSendState
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.MessageShieldProvider
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.SendHandleProvider
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.TimelineItemDebugInfoProvider
import tech.orbitweb.orbitx.libraries.matrix.test.AN_EVENT_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_MESSAGE
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_ID
import tech.orbitweb.orbitx.libraries.matrix.test.A_USER_NAME
import tech.orbitweb.orbitx.libraries.matrix.test.core.FakeSendHandle
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply.InReplyToDetails
import tech.orbitweb.orbitx.libraries.matrix.ui.messages.reply.aProfileDetailsReady
import kotlinx.collections.immutable.toImmutableList

internal fun aMessageEvent(
    eventId: EventId? = AN_EVENT_ID,
    transactionId: TransactionId? = null,
    isMine: Boolean = true,
    isEditable: Boolean = true,
    canBeRepliedTo: Boolean = true,
    content: TimelineItemEventContent = TimelineItemTextContent(body = A_MESSAGE, htmlDocument = null, formattedBody = A_MESSAGE, isEdited = false),
    inReplyTo: InReplyToDetails? = null,
    threadInfo: TimelineItemThreadInfo? = null,
    sendState: LocalEventSendState = LocalEventSendState.Sent(AN_EVENT_ID),
    debugInfoProvider: TimelineItemDebugInfoProvider = TimelineItemDebugInfoProvider { aTimelineItemDebugInfo() },
    messageShieldProvider: MessageShieldProvider = MessageShieldProvider { null },
    sendHandleProvider: SendHandleProvider = SendHandleProvider { FakeSendHandle() }
) = TimelineItem.Event(
    id = UniqueId(eventId?.value.orEmpty()),
    eventId = eventId,
    transactionId = transactionId,
    senderId = A_USER_ID,
    senderProfile = aProfileDetailsReady(displayName = A_USER_NAME),
    senderAvatar = AvatarData(A_USER_ID.value, A_USER_NAME, size = AvatarSize.TimelineSender),
    content = content,
    sentTime = "",
    isMine = isMine,
    isEditable = isEditable,
    canBeRepliedTo = canBeRepliedTo,
    reactionsState = aTimelineItemReactions(count = 0),
    readReceiptState = TimelineItemReadReceipts(emptyList<ReadReceiptData>().toImmutableList()),
    localSendState = sendState,
    inReplyTo = inReplyTo,
    threadInfo = threadInfo,
    origin = null,
    timelineItemDebugInfoProvider = debugInfoProvider,
    messageShieldProvider = messageShieldProvider,
    sendHandleProvider = sendHandleProvider,
    forwarder = null,
    forwarderProfile = null,
)
