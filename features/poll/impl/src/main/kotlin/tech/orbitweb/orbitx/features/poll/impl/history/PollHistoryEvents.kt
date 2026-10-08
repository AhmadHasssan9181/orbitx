/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.impl.history

import tech.orbitweb.orbitx.features.poll.impl.history.model.PollHistoryFilter
import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId

sealed interface PollHistoryEvents {
    data object LoadMore : PollHistoryEvents
    data class SelectPollAnswer(val pollStartId: EventId, val answerId: String) : PollHistoryEvents
    data class EndPoll(val pollStartId: EventId) : PollHistoryEvents
    data class SelectFilter(val filter: PollHistoryFilter) : PollHistoryEvents
}
