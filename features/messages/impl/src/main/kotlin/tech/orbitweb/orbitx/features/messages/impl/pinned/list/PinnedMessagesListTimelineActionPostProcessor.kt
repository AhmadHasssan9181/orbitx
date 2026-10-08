/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.pinned.list

import tech.orbitweb.orbitx.features.messages.impl.actionlist.model.TimelineItemAction
import tech.orbitweb.orbitx.features.messages.impl.actionlist.model.TimelineItemActionPostProcessor

class PinnedMessagesListTimelineActionPostProcessor : TimelineItemActionPostProcessor {
    override fun process(actions: List<TimelineItemAction>): List<TimelineItemAction> {
        return buildList {
            add(TimelineItemAction.ViewInTimeline)
            actions.firstOrNull { it == TimelineItemAction.Unpin }?.let(::add)
            actions.firstOrNull { it == TimelineItemAction.Forward }?.let(::add)
            actions.firstOrNull { it == TimelineItemAction.ViewSource }?.let(::add)
        }
    }
}
