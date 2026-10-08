/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.factories.event

import dev.zacsweers.metro.Inject
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemPollContent
import tech.orbitweb.orbitx.features.poll.api.pollcontent.PollContentStateFactory
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent

@Inject
class TimelineItemContentPollFactory(
    private val pollContentStateFactory: PollContentStateFactory,
) {
    suspend fun create(
        eventId: EventId?,
        isEditable: Boolean,
        isOwn: Boolean,
        content: PollContent,
    ): TimelineItemEventContent {
        val pollContentState = pollContentStateFactory.create(eventId, isEditable, isOwn, content)
        return TimelineItemPollContent(
            isMine = pollContentState.isMine,
            isEditable = pollContentState.isPollEditable,
            eventId = eventId,
            question = pollContentState.question,
            answerItems = pollContentState.answerItems,
            pollKind = pollContentState.pollKind,
            isEnded = pollContentState.isPollEnded,
            isEdited = content.isEdited
        )
    }
}
