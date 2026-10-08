/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.libraries.matrix.api.timeline.item

import tech.orbitweb.orbitx.libraries.architecture.AsyncData
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.core.UserId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventContent
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventOrTransactionId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.ProfileDetails

sealed interface EventThreadInfo {
    data class ThreadRoot(val summary: ThreadSummary) : EventThreadInfo
    data class ThreadResponse(val threadRootId: ThreadId) : EventThreadInfo
}

data class ThreadSummary(
    val latestEvent: AsyncData<EmbeddedEventInfo>,
    val numberOfReplies: Long,
)

data class EmbeddedEventInfo(
    val eventOrTransactionId: EventOrTransactionId,
    val content: EventContent,
    val senderId: UserId,
    val senderProfile: ProfileDetails,
    val timestamp: Long,
)
