/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.poll.api.pollcontent

import tech.orbitweb.orbitx.libraries.matrix.api.core.EventId
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.EventTimelineItem
import tech.orbitweb.orbitx.libraries.matrix.api.timeline.item.event.PollContent

interface PollContentStateFactory {
    suspend fun create(eventTimelineItem: EventTimelineItem, content: PollContent): PollContentState {
        return create(
            eventId = eventTimelineItem.eventId,
            isEditable = eventTimelineItem.isEditable,
            isOwn = eventTimelineItem.isOwn,
            content = content,
        )
    }
    suspend fun create(eventId: EventId?, isEditable: Boolean, isOwn: Boolean, content: PollContent): PollContentState
}
