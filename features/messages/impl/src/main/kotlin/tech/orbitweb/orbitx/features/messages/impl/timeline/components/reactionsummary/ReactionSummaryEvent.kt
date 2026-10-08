/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.components.reactionsummary

import tech.orbitweb.orbitx.features.messages.impl.timeline.model.AggregatedReaction
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

sealed interface ReactionSummaryEvent {
    data object Clear : ReactionSummaryEvent
    data class ShowReactionSummary(val eventId: EventId, val reactions: List<AggregatedReaction>, val selectedKey: String) : ReactionSummaryEvent
}
