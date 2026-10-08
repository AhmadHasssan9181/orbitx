/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.pinned.list

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.core.ThreadId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.TimelineItemDebugInfo

class FakePinnedMessagesListNavigator : PinnedMessagesListNavigator {
    var onViewInTimelineClickLambda: ((EventId) -> Unit)? = null
    override fun viewInTimeline(eventId: EventId) {
        onViewInTimelineClickLambda?.invoke(eventId)
    }

    var onShowEventDebugInfoClickLambda: ((EventId?, TimelineItemDebugInfo) -> Unit)? = null
    override fun navigateToEventDebugInfo(eventId: EventId?, debugInfo: TimelineItemDebugInfo) {
        onShowEventDebugInfoClickLambda?.invoke(eventId, debugInfo)
    }

    var onForwardEventClickLambda: ((EventId) -> Unit)? = null
    override fun forwardEvent(eventId: EventId) {
        onForwardEventClickLambda?.invoke(eventId)
    }

    var onOpenThreadLambda: ((ThreadId) -> Unit)? = null
    override fun navigateToThread(threadRootId: ThreadId) {
        onOpenThreadLambda?.invoke(threadRootId)
    }
}
