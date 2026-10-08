/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.history

import tech.orbitweb.orbitx.features.poll.impl.history.model.PollHistoryFilter
import tech.orbitweb.orbitx.features.poll.impl.history.model.PollHistoryItem
import tech.orbitweb.orbitx.features.poll.impl.history.model.PollHistoryItems
import kotlinx.collections.immutable.ImmutableList

data class PollHistoryState(
    val isLoading: Boolean,
    val hasMoreToLoad: Boolean,
    val activeFilter: PollHistoryFilter,
    val pollHistoryItems: PollHistoryItems,
    val eventSink: (PollHistoryEvents) -> Unit,
) {
    fun pollHistoryForFilter(filter: PollHistoryFilter): ImmutableList<PollHistoryItem> {
        return when (filter) {
            PollHistoryFilter.ONGOING -> pollHistoryItems.ongoing
            PollHistoryFilter.PAST -> pollHistoryItems.past
        }
    }
}
