/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.timeline.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class TimelineItemReactions(
    val reactions: ImmutableList<AggregatedReaction>
) {
    val highlightedKeys: ImmutableList<String>
        get() = reactions
            .filter { it.isHighlighted }
            .map { it.key }
            .toImmutableList()
}
