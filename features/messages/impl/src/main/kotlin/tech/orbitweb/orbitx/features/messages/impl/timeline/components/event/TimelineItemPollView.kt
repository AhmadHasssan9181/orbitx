/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.event

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import kotlinx.collections.immutable.toImmutableList
import tech.orbitweb.orbitx.features.messages.impl.timeline.TimelineEvent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemPollContent
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemPollContentProvider
import tech.orbitweb.orbitx.features.poll.api.pollcontent.PollContentView
import tech.orbitweb.orbitx.libraries.designsystem.preview.ElementPreview
import tech.orbitweb.orbitx.libraries.designsystem.preview.PreviewsDayNight
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

@Composable
fun TimelineItemPollView(
    content: TimelineItemPollContent,
    eventSink: (TimelineEvent.TimelineItemPollEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    fun onSelectAnswer(pollStartId: EventId, answerId: String) {
        eventSink(TimelineEvent.SelectPollAnswer(pollStartId, answerId))
    }

    fun onEndPoll(pollStartId: EventId) {
        eventSink(TimelineEvent.EndPoll(pollStartId))
    }

    fun onEditPoll(pollStartId: EventId) {
        eventSink(TimelineEvent.EditPoll(pollStartId))
    }

    PollContentView(
        eventId = content.eventId,
        question = content.question,
        answerItems = content.answerItems.toImmutableList(),
        pollKind = content.pollKind,
        isPollEnded = content.isEnded,
        isPollEditable = content.isEditable,
        isMine = content.isMine,
        onSelectAnswer = ::onSelectAnswer,
        onEditPoll = ::onEditPoll,
        onEndPoll = ::onEndPoll,
        modifier = modifier,
    )
}

@PreviewsDayNight
@Composable
internal fun TimelineItemPollViewPreview(@PreviewParameter(TimelineItemPollContentProvider::class) content: TimelineItemPollContent) =
    ElementPreview {
        TimelineItemPollView(
            content = content,
            eventSink = {},
        )
    }
