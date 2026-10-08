/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import tech.orbitweb.orbitx.features.messages.impl.timeline.aTimelineItemReactions
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

open class ReactionSummaryStateProvider : PreviewParameterProvider<ReactionSummaryState> {
    override val values = sequenceOf(aReactionSummaryState())
}

fun aReactionSummaryState(): ReactionSummaryState {
    val reactions = aTimelineItemReactions(8, true).reactions
    return ReactionSummaryState(
        target = ReactionSummaryState.Summary(
            reactions = reactions,
            selectedKey = reactions[0].key,
            selectedEventId = EventId("$1234"),
        ),
        eventSink = {}
    )
}
