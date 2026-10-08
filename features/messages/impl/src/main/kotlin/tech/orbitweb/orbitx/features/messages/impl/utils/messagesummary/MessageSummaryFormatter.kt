/*
 * Copyright (c) 2026 Orbit Team.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Orbit-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package tech.orbitweb.orbitx.features.messages.impl.utils.messagesummary

import tech.orbitweb.orbitx.features.messages.impl.timeline.model.TimelineItem
import tech.orbitweb.orbitx.features.messages.impl.timeline.model.event.TimelineItemEventContent

interface MessageSummaryFormatter {
    fun format(event: TimelineItem.Event): String {
        return format(event.content)
    }
    fun format(content: TimelineItemEventContent): String
}
