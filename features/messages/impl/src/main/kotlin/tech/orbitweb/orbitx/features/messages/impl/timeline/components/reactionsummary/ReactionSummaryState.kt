/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary

import kotlinx.collections.immutable.ImmutableList
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.AggregatedReaction
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

data class ReactionSummaryState(
    val target: Summary?,
    val eventSink: (ReactionSummaryEvent) -> Unit
) {
    data class Summary(
        val reactions: ImmutableList<AggregatedReaction>,
        val selectedKey: String,
        val selectedEventId: EventId
    )
}
