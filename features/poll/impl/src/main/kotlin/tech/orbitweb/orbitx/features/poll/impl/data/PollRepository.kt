/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.data

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import tech.orbitweb.orbitx.libraries.core.extensions.flatMap
import tech.orbitweb.orbitx.libraries.core.extensions.runCatchingExceptions
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.poll.PollKind
import tech.orbitweb.orbitx.libraries.matrix.api.room.CreateTimelineParams
import tech.orbitweb.orbitx.libraries.matrix.api.room.JoinedRoom
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.MatrixTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.Timeline
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.TimelineProvider
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.getActiveTimeline
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.toEventOrTransactionId

@AssistedInject
class PollRepository(
    private val room: JoinedRoom,
    private val defaultTimelineProvider: TimelineProvider,
    @Assisted private val timelineMode: Timeline.Mode,
) {
    @AssistedFactory
    fun interface Factory {
        fun create(
            timelineMode: Timeline.Mode,
        ): PollRepository
    }

    suspend fun getPoll(eventId: EventId): Result<PollContent> = runCatchingExceptions {
        getTimelineProvider()
            .getOrThrow()
            .getActiveTimeline()
            .timelineItems
            .first()
            .asSequence()
            .filterIsInstance<MatrixTimelineItem.Event>()
            .first { it.eventId == eventId }
            .event
            .content as PollContent
    }

    suspend fun savePoll(
        existingPollId: EventId?,
        question: String,
        answers: List<String>,
        pollKind: PollKind,
        maxSelections: Int,
    ): Result<Unit> = when (existingPollId) {
        null -> getTimelineProvider().flatMap { timelineProvider ->
            timelineProvider
                .getActiveTimeline()
                .createPoll(
                    question = question,
                    answers = answers,
                    maxSelections = maxSelections,
                    pollKind = pollKind,
                )
        }
        else -> getTimelineProvider().flatMap { timelineProvider ->
            timelineProvider.getActiveTimeline()
                .editPoll(
                    pollStartId = existingPollId,
                    question = question,
                    answers = answers,
                    maxSelections = maxSelections,
                    pollKind = pollKind,
                )
        }
    }

    suspend fun deletePoll(
        pollStartId: EventId,
    ): Result<Unit> =
        getTimelineProvider().flatMap { timelineProvider ->
            timelineProvider.getActiveTimeline()
                .redactEvent(
                    eventOrTransactionId = pollStartId.toEventOrTransactionId(),
                    reason = null,
                )
        }

    private suspend fun getTimelineProvider(): Result<TimelineProvider> {
        return when (timelineMode) {
            is Timeline.Mode.Thread -> {
                val threadedTimelineResult = room.createTimeline(CreateTimelineParams.Threaded(timelineMode.threadRootId))
                threadedTimelineResult.map { threadedTimeline ->
                    object : TimelineProvider {
                        private val flow = MutableStateFlow<Timeline?>(threadedTimeline)
                        override fun activeTimelineFlow(): StateFlow<Timeline?> = flow
                    }
                }
            }
            else -> Result.success(defaultTimelineProvider)
        }
    }
}
