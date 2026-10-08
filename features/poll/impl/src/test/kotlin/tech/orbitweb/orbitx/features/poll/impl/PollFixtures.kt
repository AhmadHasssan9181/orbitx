/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UniqueId
import tech.orbitweb.orbitx.libraries.matrix.api.poll.PollAnswer
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.MatrixTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.aPollContent
import tech.orbitweb.orbitx.libraries.matrix.test.timeline.anEventTimelineItem

fun aPollTimelineItems(
    polls: Map<EventId, PollContent> = emptyMap(),
): Flow<List<MatrixTimelineItem>> {
    return flowOf(
        polls.map { entry ->
            MatrixTimelineItem.Event(
                uniqueId = UniqueId(entry.key.value),
                event = anEventTimelineItem(
                    eventId = entry.key,
                    content = entry.value,
                )
            )
        }
    )
}

fun anOngoingPollContent() = aPollContent(
    question = "Do you like polls?",
    answers = persistentListOf(
        PollAnswer("1", "Yes"),
        PollAnswer("2", "No"),
        PollAnswer("2", "Maybe"),
    ),
)

fun anEndedPollContent() = anOngoingPollContent().copy(
    endTime = 1702400215U
)
