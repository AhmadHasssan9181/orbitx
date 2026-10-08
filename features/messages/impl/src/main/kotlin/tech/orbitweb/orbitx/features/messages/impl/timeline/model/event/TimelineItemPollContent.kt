/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model.event

import tech.orbitweb.orbitx.features.poll.api.pollcontent.PollAnswerItem
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.poll.PollKind

data class TimelineItemPollContent(
    val isMine: Boolean,
    val isEditable: Boolean,
    val eventId: EventId?,
    val question: String,
    val answerItems: List<PollAnswerItem>,
    val pollKind: PollKind,
    val isEnded: Boolean,
    override val isEdited: Boolean,
) : TimelineItemEventContent,
    TimelineItemEventMutableContent {
    override val type: String = "TimelineItemPollContent"
}
